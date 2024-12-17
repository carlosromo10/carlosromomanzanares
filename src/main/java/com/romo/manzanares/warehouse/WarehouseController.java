package com.romo.manzanares.warehouse;

import com.romo.manzanares.warehouse.dtos.CreateWarehouseDto;
import com.romo.manzanares.warehouse.dtos.UpdateWarehouseDto;
import com.romo.manzanares.warehouse.dtos.WarehouseDto;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/warehouse")
@Validated
public class WarehouseController {
   private final WarehouseRepository warehouseRepository;
   private final ModelMapper modelMapper;

   public WarehouseController(WarehouseRepository warehouseRepository, ModelMapper modelMapper) {
       this.warehouseRepository = warehouseRepository;
       this.modelMapper = modelMapper;
   }

   @GetMapping
    public List<WarehouseDto> getAllWarehouses() {
       return warehouseRepository
               .findAll()
               .stream()
               .map(warehouse -> modelMapper.map(warehouse, WarehouseDto.class))
               .collect(Collectors.toList());
   }

    @GetMapping("/{warehouseId}")
    public WarehouseDto getWarehouseById(@PathVariable int warehouseId) {
        return modelMapper.map(warehouseRepository.findById(warehouseId).orElseThrow(() -> new RuntimeException("Warehouse not found")), WarehouseDto.class);
    }

   @PostMapping
    public WarehouseDto addWarehouse(@RequestBody @Validated CreateWarehouseDto warehouseDto) {
       Warehouse warehouse = modelMapper.map(warehouseDto, Warehouse.class);
       return modelMapper.map(warehouseRepository.save(warehouse), WarehouseDto.class);
   }

   @PutMapping
    public WarehouseDto updateWarehouse(@RequestBody @Validated UpdateWarehouseDto warehouseDto) {
       Warehouse warehouse = modelMapper.map(warehouseDto, Warehouse.class);
       return modelMapper.map(warehouseRepository.save(warehouse), WarehouseDto.class);
   }

   @DeleteMapping
    public String deleteWarehouse(@Validated int warehouseId) {
       Warehouse warehouse = warehouseRepository.findById(warehouseId).orElseThrow(() -> new RuntimeException("Warehouse not found"));
       warehouseRepository.delete(warehouse);
       return "Deleted warehouse with id: " + warehouseId;
   }
}
