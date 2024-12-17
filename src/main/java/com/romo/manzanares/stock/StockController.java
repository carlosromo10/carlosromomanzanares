package com.romo.manzanares.stock;

import com.romo.manzanares.product.Product;
import com.romo.manzanares.product.ProductRepository;
import com.romo.manzanares.location.Location;
import com.romo.manzanares.location.LocationRepository;
import com.romo.manzanares.stock.dtos.CreateStockDto;
import com.romo.manzanares.stock.dtos.StockDto;
import com.romo.manzanares.stock.dtos.UpdateStockDto;
import com.romo.manzanares.warehouse.Warehouse;
import com.romo.manzanares.warehouse.WarehouseRepository;
import com.romo.manzanares.warehouse.dtos.WarehouseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stock")
@Validated
public class StockController {
    private final StockRepository stockRepository;
    private final LocationRepository locationRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public StockController(StockRepository stockRepository, LocationRepository locationRepository, WarehouseRepository warehouseRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.stockRepository = stockRepository;
        this.locationRepository = locationRepository;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<StockDto> getAllStocks() {
        return stockRepository
                .findAll()
                .stream()
                .map(stock -> modelMapper.map(stock, StockDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{stockId}")
    public StockDto getStockById(@PathVariable int stockId) {
        return modelMapper.map(stockRepository.findById(stockId).orElseThrow(() -> new RuntimeException("Stock not found")), StockDto.class);
    }

    @PostMapping
    public StockDto addStock(@Validated @RequestBody CreateStockDto stockDto) {
        Stock stock = new Stock();

        Warehouse warehouse = warehouseRepository.findById(stockDto.getWarehouse_id()).orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Product product = productRepository.findById(stockDto.getProduct_id()).orElseThrow(() -> new RuntimeException("Product not found"));
        Location location = locationRepository.findById(stockDto.getLocation_id()).orElseThrow(() -> new RuntimeException("Location not found"));

        stock.setWarehouse(warehouse);
        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(stockDto.getQuantity());
        return modelMapper.map(stockRepository.save(stock), StockDto.class);
    }

    @PutMapping
    public StockDto updateStock(@Validated @RequestBody UpdateStockDto stockDto) {
        Stock stock = new Stock();
        Warehouse warehouse = warehouseRepository.findById(stockDto.getWarehouse_id()).orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Product product = productRepository.findById(stockDto.getProduct_id()).orElseThrow(() -> new RuntimeException("Product not found"));
        Location location = locationRepository.findById(stockDto.getLocation_id()).orElseThrow(() -> new RuntimeException("Location not found"));

        stock.setId(stockDto.getId());
        stock.setWarehouse(warehouse);
        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(stockDto.getQuantity());
        return modelMapper.map(stockRepository.save(stock), StockDto.class);
    }

    @DeleteMapping("/{stockId}")
    public String deleteStock(@PathVariable int stockId) {
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new RuntimeException("Stock not found"));
        stockRepository.delete(stock);
        return "Deleted stock with id: " + stockId;
    }


    @Operation(summary = "General por almacen")
    @GetMapping("/warehouse/{warehouseId}")
    public List<StockDto> findStockByWarehouseId(@PathVariable int warehouseId) {
        return stockRepository
                .findAll()
                .stream()
                .takeWhile(t -> t.getWarehouse().getId() == warehouseId)
                .map(t -> modelMapper.map(t, StockDto.class))
                .collect(Collectors.toList());
    }

    @Operation(summary = "General todos los almacenes")
    @GetMapping("/warehouse")
    public List<StockDto> findAllStockByWarehouse() {
        return stockRepository
                .findAll()
                .stream()
                .map(t -> modelMapper.map(t, StockDto.class))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Stock por producto")
    @GetMapping("/product/{productUpc}")
    public List<StockDto> findAllStockByProductUpc(@PathVariable long productUpc) {
        return stockRepository
                .findAll()
                .stream()
                .takeWhile(t -> t.getProduct().getUpc() == productUpc)
                .map(t -> modelMapper.map(t, StockDto.class)).collect(Collectors.toList());
    }
}
