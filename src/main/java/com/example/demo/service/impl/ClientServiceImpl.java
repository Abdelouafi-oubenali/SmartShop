package com.example.demo.service.impl;

import com.example.demo.dto.ClientDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Client;
import com.example.demo.entity.User;
import com.example.demo.mapper.ClientMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserMapper userMapper ;
    private final UserRepository userRepository ;

    public ClientServiceImpl(
            ClientRepository clientRepository,
            ClientMapper clientMapper,
            UserMapper userMapper,
            UserRepository userRepository
    ) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ClientDTO create(ClientDTO clientDTO , UserDto userDto)
    {
        User user = userMapper.toEntity(userDto) ;
        user = userRepository.save(user) ;

        Client client = clientMapper.toEntity(clientDTO) ;
        client.setUser(user);
        client = clientRepository.save(client);

        ClientDTO result = clientMapper.toDTO(client);
        result.setUserId(user.getId());

        return result ;

    }

    @Override
    public List<ClientDTO> getAll()
    {
        return clientRepository.findAll()
                .stream()
                .map(clientMapper::toDTO)
                .toList() ;
    }

    @Override
    public ClientDTO update(Long id , ClientDTO clientDTO , UserDto userDto)
    {
       Client client = clientRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Client no fond")) ;

        User user = client.getUser();

        client.setName(clientDTO.getName());
        client.setEmail(clientDTO.getEmail());
        client.setLoyaltyLevel(clientDTO.getLoyaltyLevel());
        client.setTotalOrders(clientDTO.getTotalOrders());
        client.setTotalSpent(clientDTO.getTotalSpent());

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());

        client = clientRepository.save(client);

        ClientDTO result = clientMapper.toDTO(client);
        result.setUserId(user.getId());

        return result;


    }




}
