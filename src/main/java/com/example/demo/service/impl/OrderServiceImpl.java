package com.example.demo.service.impl;

import ch.qos.logback.core.status.Status;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.PaiementDTO;
import com.example.demo.entity.*;
import com.example.demo.enums.LoyaltyLevel;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.ApiResponse;
import com.example.demo.mapper.OrderItemMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.PaiementMapper;
import com.example.demo.repository.*;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.demo.enums.OrderStatus.CONFIRMED;
import static com.example.demo.enums.OrderStatus.PENDING;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductRepository productRepository ;
    private final OrderItemRepository orderItemRepository ;
    private final PaiementRepository paiementRepository ;
    private final PaiementMapper paiementMapper ;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        Order order = new Order();

        if (orderDTO.getClientId() != null) {
            Client client = clientRepository.findById(orderDTO.getClientId())
                    .orElseThrow(() -> new IllegalArgumentException("Client not found"));
            order.setClient(client);
        }

        if (orderDTO.getPromoCodeId() != null) {
            PromoCode promo = promoCodeRepository.findById(orderDTO.getPromoCodeId())
                    .orElseThrow(() -> new IllegalArgumentException("Promo code not found"));
            order.setPromoCode(promo);
        }

        double sousTotalHT = 0;

        for (OrderItemDTO dto : orderDTO.getItems()) {

            if (dto.getProduitQuantite() <= 0) {
                throw new IllegalArgumentException("Quantité non valide pour le produit: " + dto.getProductId());
            }

            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));

            if (product.getAvailableStock() < dto.getProduitQuantite()) {
                throw new IllegalArgumentException("Stock insuffisant pour: " + product.getName());
            }

            sousTotalHT += product.getUnitPrice() * dto.getProduitQuantite();
        }

        double remiseFidelite = calculateLoyaltyDiscount(orderDTO.getClientId(), sousTotalHT);
        double remisePromo = calculatePromoDiscount(orderDTO.getPromoCodeId(), sousTotalHT);

        double remiseManuelle = 0;
        if (orderDTO.getDiscount() != null) {
            remiseManuelle = sousTotalHT * (orderDTO.getDiscount() / 100.0);
        }

        double remiseTotale = remiseFidelite + remisePromo + remiseManuelle;

        double montantHTApresRemise = sousTotalHT - remiseTotale;
        if (montantHTApresRemise < 0) {
            montantHTApresRemise = 0;
        }

        double tvaRate = orderDTO.getTax() / 100.0;
        double tva = montantHTApresRemise * tvaRate;
        double totalTTC = montantHTApresRemise + tva;

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO dto : orderDTO.getItems()) {

            Product product = productRepository.findById(dto.getProductId()).get();

            product.setAvailableStock(product.getAvailableStock() - dto.getProduitQuantite());
            productRepository.save(product);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setProduitQuantite(dto.getProduitQuantite());
            item.setTotalLigne(product.getUnitPrice() * dto.getProduitQuantite());

            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotal(totalTTC);
        order.setDiscount(remiseTotale);
        order.setTax(orderDTO.getTax());
        order.setOrderDate(orderDTO.getOrderDate());

        List<Paiement> payments = new ArrayList<>();

        if (orderDTO.getPayments() != null) {
            for (PaiementDTO dto : orderDTO.getPayments()) {

                Paiement paiement = new Paiement();
                paiement.setOrder(order);
                paiement.setAmount(dto.getAmount());
                paiement.setPaymentNumber(dto.getPaymentNumber());
                paiement.setPaymentType(dto.getPaymentType());
                paiement.setPaymentDate(dto.getPaymentDate());
                paiement.setDepositDate(dto.getDepositDate());
                paiement.setStatus(dto.getStatus());
                paiement.setReference(dto.getReference());
                paiement.setBank(dto.getBank());

                payments.add(paiement);
            }
        }

        order.setPayments(payments);

        double totalPayments = payments.stream()
                .filter(p -> p.getAmount() != null)
                .mapToDouble(Paiement::getAmount)
                .sum();

        double remaining = totalTTC - totalPayments;
        order.setRemainingAmount(remaining);

        if (remaining > 0) {
            order.setStatus(PENDING);
        } else {
            order.setStatus(CONFIRMED);
        }



        Order savedOrder = orderRepository.save(order);

        if (order.getClient() != null) {

            LoyaltyLevel newLevel = checkLoyaltyLevel(
                    order.getClient().getId(),
                    CONFIRMED
            );

            Client client = order.getClient();
            client.setLoyaltyLevel(newLevel);

            clientRepository.save(client);
        }
        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO updateStatus(Long id, OrderStatus orderStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setStatus(orderStatus);
        orderRepository.save(order) ;

        List<OrderItem> list_items = orderItemRepository.findByOrderId(order.getId()) ;

        for (OrderItem item : list_items) {
            Long productId = item.getProduct().getId();
            Integer quantity = item.getProduitQuantite();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));

            product.setAvailableStock(product.getAvailableStock() + quantity);
            productRepository.save(product);

            System.out.println("Product ID: " + productId);
            System.out.println("Quantity: " + quantity);
        }


        return orderMapper.toDTO(order) ;
    }

    @Override
    public OrderDTO getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return orderMapper.toDTO(order);
    }

    @Override
    public ApiResponse delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        orderRepository.delete(order);
        return new ApiResponse(true, "Order deleted successfully");
    }

    @Override
    public List<OrderDTO> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> dtos = new ArrayList<>();
        for (Order o : orders) {
            OrderDTO dto = orderMapper.toDTO(o);
            dto.setClientId(o.getClient() != null ? o.getClient().getId() : null);
            dto.setPromoCodeId(o.getPromoCode() != null ? o.getPromoCode().getId() : null);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public ApiResponse completePayment(Long orderId, PaiementDTO paiementDTO) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Aucune commande trouvée avec cet identifiant."));

        Double rest = order.getRemainingAmount() - paiementDTO.getAmount();

        System.out.println();
        if (rest > 0) {

            order.setRemainingAmount(rest);
            orderRepository.save(order);

            return new ApiResponse(true,"Un paiement partiel a été enregistré. Le montant restant à payer est : " + rest
            );
        }

        else if (rest < 0) {

            return new ApiResponse(false,"Montant incorrect : le montant payé dépasse le montant restant. Veuillez saisir le montant exact."
            );
        }

        else {

            order.setStatus(CONFIRMED);
            order.setRemainingAmount(0.0);
            orderRepository.save(order);

            return new ApiResponse(true,"Le paiement complet a été effectué. La commande est maintenant confirmée."
            );
        }
    }


    private double calculateLoyaltyDiscount(Long clientId, double sousTotalHT) {
        if (clientId == null) return 0;

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        double discount = 0;

        if (client.getLoyaltyLevel() == LoyaltyLevel.SILVER && sousTotalHT >= 500) {
            discount = sousTotalHT * 0.05;
        } else if (client.getLoyaltyLevel() == LoyaltyLevel.GOLD && sousTotalHT >= 800) {
            discount = sousTotalHT * 0.10;
        } else if (client.getLoyaltyLevel() == LoyaltyLevel.PLATINUM && sousTotalHT >= 1200) {
            discount = sousTotalHT * 0.15;
        }

        return discount;
    }



    private double calculatePromoDiscount(Long promoId, double sousTotalHT) {
        if (promoId == null) return 0;

        PromoCode promo = promoCodeRepository.findById(promoId)
                .orElseThrow(() -> new IllegalArgumentException("Promo non trouvé"));

        if (promo.getCode().startsWith("PROMO-") && promo.getActive()) {
            return sousTotalHT * 0.05;
        }
        return 0;
    }

    @Override
    public Map<String, Object> getConfirmedOrdersStatsForLoggedUser(Long userId) {

        System.out.println("---------------  user id et " + userId) ;

        Client client = clientRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Client introuvable pour cet utilisateur"));

        Optional<Client> clientOpt = clientRepository.findByUser_Id(1L);
        System.out.println("Client found: " + clientOpt.isPresent());

        Long clientId = client.getId();

        Long totalOrders = orderRepository.countByClientIdAndStatus(clientId, OrderStatus.CONFIRMED);

        Double totalAmount = orderRepository.sumTotalByClientAndStatus(clientId, OrderStatus.CONFIRMED);
        if (totalAmount == null) totalAmount = 0.0;

        Map<String, Object> stats = new HashMap<>();
        stats.put("clientId", clientId);
        stats.put("totalOrders", totalOrders);
        stats.put("totalAmount", totalAmount);

        return stats;
    }

    private LoyaltyLevel checkLoyaltyLevel(Long clientId, OrderStatus orderStatus) {

        Long count = orderRepository.countByClientIdAndStatus(clientId, orderStatus);
        Double totalCumule = orderRepository.sumTotalByClientAndStatus(clientId, orderStatus);

        if (totalCumule == null) totalCumule = 0.0;

        if (count >= 20 || totalCumule >= 15000) {
            return LoyaltyLevel.PLATINUM;
        }

        if (count >= 10 || totalCumule >= 5000) {
            return LoyaltyLevel.GOLD;
        }

        if (count >= 3 || totalCumule >= 1000) {
            return LoyaltyLevel.SILVER;
        }

        return LoyaltyLevel.BASIC;
    }



}
