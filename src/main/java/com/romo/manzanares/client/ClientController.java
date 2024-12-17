package com.romo.manzanares.client;

import com.romo.manzanares.client.dtos.ClientDto;
import com.romo.manzanares.client.dtos.CreateClientDto;
import com.romo.manzanares.client.dtos.UpdateClientDto;
import org.hibernate.sql.Update;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    public ClientController(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<ClientDto> findAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(client -> modelMapper.map(client, ClientDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{clientId}")
    public ClientDto findClientById(@PathVariable("clientId") int clientId) {
        return modelMapper.map( clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found")), ClientDto.class);
    }

    @PostMapping
    public ClientDto addClient(@RequestBody CreateClientDto clientDto) {
        Client client = modelMapper.map(clientDto, Client.class);
        return modelMapper.map(clientRepository.save(client), ClientDto.class);
    }

    @PutMapping
    public ClientDto updateClient(@RequestBody UpdateClientDto clientDto) {
        Client client = modelMapper.map(clientDto, Client.class);
        return modelMapper.map(clientRepository.save(client), ClientDto.class);
    }

    @DeleteMapping("/{clientId}")
    public String deleteClient(@PathVariable("clientId") int clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        clientRepository.delete(client);
        return "Deleted client with id: " + clientId;
    }
}
