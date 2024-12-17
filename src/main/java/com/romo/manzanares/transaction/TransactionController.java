package com.romo.manzanares.transaction;


import com.romo.manzanares.client.Client;
import com.romo.manzanares.client.ClientRepository;
import com.romo.manzanares.client.dtos.ClientDto;
import com.romo.manzanares.employee.Employee;
import com.romo.manzanares.employee.EmployeeRepository;
import com.romo.manzanares.product.Product;
import com.romo.manzanares.product.ProductRepository;
import com.romo.manzanares.provider.Provider;
import com.romo.manzanares.provider.ProviderRepository;
import com.romo.manzanares.provider.dtos.ProviderDto;
import com.romo.manzanares.stock.StockRepository;
import com.romo.manzanares.transaction.dtos.*;
import com.romo.manzanares.transaction.dtos.query.*;
import com.romo.manzanares.warehouse.Warehouse;
import com.romo.manzanares.warehouse.WarehouseRepository;
import com.romo.manzanares.warehouse.dtos.WarehouseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final ProviderRepository providerRepository;
    private final StockRepository stockRepository;
    private final ModelMapper modelMapper;
    @Autowired
    private EmployeeRepository employeeRepository;

    public TransactionController(TransactionRepository transactionRepository, ModelMapper modelMapper, WarehouseRepository warehouseRepository, ProductRepository productRepository, ClientRepository clientRepository, ProviderRepository providerRepository, StockRepository stockRepository) {
        this.transactionRepository = transactionRepository;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.providerRepository = providerRepository;
        this.stockRepository = stockRepository;


        this.modelMapper = modelMapper;
    }

    @GetMapping("/warehouse")
    public List<WarehouseTransactionDto> getWarehouseTransactions() {
        List<Transaction> transactions = transactionRepository.findAll().stream().takeWhile(t -> t.getTransactionType() == TransactionType.STOCK).toList();
        return transactions
                .stream()
                .map(t -> modelMapper.map(t, WarehouseTransactionDto.class))
                .peek(t -> {
                    Warehouse warehouse = warehouseRepository.findById(t.getFlow_id()).orElse(null);
                    WarehouseDto warehouseDto = modelMapper.map(warehouse, WarehouseDto.class);
                    t.setDestinyWarehouseDto(warehouseDto);
                }).collect(Collectors.toList());
    }

    @GetMapping("/provider")
    public List<ProviderTransactionDto> getProviderTransactions() {
        List<Transaction> transactions = transactionRepository.findAll().stream().takeWhile(t -> t.getTransactionType() == TransactionType.PROVIDER).toList();
        return transactions
                .stream()
                .map(t -> modelMapper.map(t, ProviderTransactionDto.class))
                .peek(t -> {
                    Provider provider = providerRepository.findById(t.getFlow_id()).orElse(null);
                    ProviderDto providerDto = modelMapper.map(provider, ProviderDto.class);
                    t.setProviderDto(providerDto);
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/clients")
    public List<ClientTransactionDto> getClientTransactions() {
        List<Transaction> transactions = transactionRepository.findAll().stream().takeWhile(t -> t.getTransactionType() == TransactionType.CLIENT).toList();
        return transactions
                .stream()
                .map(t -> modelMapper.map(t, ClientTransactionDto.class))
                .peek(t -> {
                    Client client = clientRepository.findById(t.getFlow_id()).orElse(null);
                    ClientDto clientDto = modelMapper.map(client, ClientDto.class);
                    t.setClientDto(clientDto);
                })
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<CommonTransactionDto> getTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream().map(t -> modelMapper.map(t, CommonTransactionDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{transactionId}")
    public CommonTransactionDto getTransaction(@PathVariable int transactionId) {
        return modelMapper.map(transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found")), CommonTransactionDto.class);
    }

    @PostMapping
    public CommonTransactionDto addTransaction(@RequestBody CreateTransactionDto commonTransactionDto) {
        Transaction transaction = new Transaction();
        Warehouse warehouse = warehouseRepository.findById(commonTransactionDto.getWarehouse_id()).orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Product product = productRepository.findById(commonTransactionDto.getProduct_id()).orElseThrow(() -> new RuntimeException("Product not found"));
        Employee employee = employeeRepository.findById(commonTransactionDto.getEmployee_id()).orElseThrow(() -> new RuntimeException("Employee not found"));

        transaction.setWarehouse(warehouse);
        transaction.setProduct(product);
        transaction.setEmployee(employee);
        transaction.setDate(commonTransactionDto.getDate());
        transaction.setQuantity(commonTransactionDto.getQuantity());
        transaction.setTransactionType(commonTransactionDto.getTransactionType());
        transaction.setFlow_id(commonTransactionDto.getFlow_id());

        return modelMapper.map(transactionRepository.save(transaction), CommonTransactionDto.class);
    }

    @PutMapping
    public CommonTransactionDto updateTransaction(@RequestBody UpdateTransactionDto commonTransactionDto) {
        Transaction transaction = new Transaction();
        Warehouse warehouse = warehouseRepository.findById(commonTransactionDto.getWarehouse_id()).orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Product product = productRepository.findById(commonTransactionDto.getProduct_id()).orElseThrow(() -> new RuntimeException("Product not found"));
        Employee employee = employeeRepository.findById(commonTransactionDto.getEmployee_id()).orElseThrow(() -> new RuntimeException("Employee not found"));

        transaction.setId(commonTransactionDto.getId());
        transaction.setWarehouse(warehouse);
        transaction.setProduct(product);
        transaction.setEmployee(employee);
        transaction.setDate(commonTransactionDto.getDate());
        transaction.setQuantity(commonTransactionDto.getQuantity());
        transaction.setTransactionType(commonTransactionDto.getTransactionType());
        transaction.setFlow_id(commonTransactionDto.getFlow_id());

        return modelMapper.map(transactionRepository.save(transaction), CommonTransactionDto.class);
    }

    @DeleteMapping("/{transactionId}")
    public String deleteTransaction(@PathVariable int transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));
        transactionRepository.delete(transaction);
        return "Deleted transaction with id: " + transactionId;
    }


    @Operation(summary = "Por almacen de origen")
    @GetMapping("/query/warehouse/origin")
    public List<CommonTransactionDto> findByOriginWarehouseId(@RequestBody QueryWarehouseDto queryWarehouseDto) {

        return transactionRepository.findAll().stream()
                .takeWhile(t -> t.getWarehouse().getId() == queryWarehouseDto.getId()
                        && t.getDate().toLocalDateTime().isBefore(queryWarehouseDto.getEndDate().atStartOfDay())
                        && t.getDate().toLocalDateTime().isAfter(queryWarehouseDto.getStartDate().atStartOfDay())).map(t -> modelMapper.map(t, CommonTransactionDto.class)).collect(Collectors.toList());
    }

    @Operation(summary = "General (todos los almacenes)")
    @GetMapping("/query/warehouse")
    public List<CommonTransactionDto> findAllOriginWarehouse(@Validated @RequestBody QueryDto queryDto) {
        return transactionRepository
                .findAll()
                .stream()
                .takeWhile(t ->
                        t.getDate().toLocalDateTime().isBefore(queryDto.getEndDate().atStartOfDay()) &&
                        t.getDate().toLocalDateTime().isAfter(queryDto.getStartDate().atStartOfDay()))
                .map(t -> modelMapper.map(t, CommonTransactionDto.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Por producto")
    @GetMapping("/query/product")
    public List<CommonTransactionDto> findByProduct(@RequestBody QueryProductDto queryProductDto) {
        return transactionRepository.findAll().stream()
                .takeWhile(t -> t.getProduct().getUpc() == queryProductDto.getProductUpc()
                        && t.getDate().toLocalDateTime().isBefore(queryProductDto.getEndDate().atStartOfDay())
                        && t.getDate().toLocalDateTime().isAfter(queryProductDto.getStartDate().atStartOfDay())).map(t -> modelMapper.map(t, CommonTransactionDto.class)).collect(Collectors.toList());
    }

    // Empleado
    @Operation(summary = "Por empleado")
    @GetMapping("/query/employee")
    public List<CommonTransactionDto> findByEmployee(@RequestBody QueryEmployeeDto queryEmployeeDto) {
        return transactionRepository.findAll().stream()
                .takeWhile(t -> t.getEmployee().getId() == queryEmployeeDto.getId()
                        && t.getDate().toLocalDateTime().isBefore(queryEmployeeDto.getEndDate().atStartOfDay())
                        && t.getDate().toLocalDateTime().isAfter(queryEmployeeDto.getStartDate().atStartOfDay())).map(t -> modelMapper.map(t, CommonTransactionDto.class)).collect(Collectors.toList());
    }

    //Cliente
    @Operation(summary = "Por cliente")
    @GetMapping("/query/client")
    public List<ClientTransactionDto> findByClient(@RequestBody QueryClientDto queryClientDto) {
        return transactionRepository
                .findAll()
                .stream()
                .takeWhile(t -> t.getTransactionType() == TransactionType.CLIENT
                        && t.getFlow_id() == queryClientDto.getId()
                        && t.getDate().toLocalDateTime().isBefore(queryClientDto.getEndDate().atStartOfDay())
                        && t.getDate().toLocalDateTime().isAfter(queryClientDto.getStartDate().atStartOfDay()))
                .map(t -> modelMapper.map(t, ClientTransactionDto.class))
                .peek(t -> {
                    Client client = clientRepository.findById(t.getFlow_id()).orElse(null);
                    ClientDto clientDto = modelMapper.map(client, ClientDto.class);
                    t.setClientDto(clientDto);
                }).collect(Collectors.toList());
    }
    // Proveedor
    @Operation(summary = "Por proveedor")
    @GetMapping("/query/provider")
    public List<ProviderTransactionDto> findByProvider(@RequestBody QueryProviderDto queryProviderDto) {
        return transactionRepository
                .findAll()
                .stream()
                .takeWhile(t -> t.getTransactionType() == TransactionType.PROVIDER
                        && t.getFlow_id() == queryProviderDto.getId()
                        && t.getDate().toLocalDateTime().isBefore(queryProviderDto.getEndDate().atStartOfDay())
                        && t.getDate().toLocalDateTime().isAfter(queryProviderDto.getStartDate().atStartOfDay()))
                .map(t -> modelMapper.map(t, ProviderTransactionDto.class))
                .peek(t -> {
                    Provider provider = providerRepository.findById(t.getFlow_id()).orElse(null);
                    ProviderDto providerDto = modelMapper.map(provider, ProviderDto.class);
                    t.setProviderDto(providerDto);
                }).collect(Collectors.toList());
    }
    // Almancen de origen/destino
    @Operation(summary = "Por almacen de destino")
    @GetMapping("/query/destinyWarehouse")
    public List<WarehouseTransactionDto> findByWarehouse(@RequestBody QueryWarehouseDto queryWarehouseDto) {
        return transactionRepository
                .findAll()
                .stream()
                .takeWhile(t -> t.getTransactionType() == TransactionType.STOCK
                        && t.getFlow_id() == queryWarehouseDto.getId()
                        && t.getDate().toLocalDateTime().isBefore(queryWarehouseDto.getEndDate().atStartOfDay())
                        && t.getDate().toLocalDateTime().isAfter(queryWarehouseDto.getStartDate().atStartOfDay()))
                .map(t -> modelMapper.map(t, WarehouseTransactionDto.class))
                .peek(t -> {
                    Warehouse warehouse = warehouseRepository.findById(t.getFlow_id()).orElse(null);
                    WarehouseDto warehouseDto = modelMapper.map(warehouse, WarehouseDto.class);
                    t.setDestinyWarehouseDto(warehouseDto);
                }).collect(Collectors.toList());
    }
}
