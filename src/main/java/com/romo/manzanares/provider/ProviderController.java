package com.romo.manzanares.provider;

import com.romo.manzanares.provider.dtos.CreateProviderDto;
import com.romo.manzanares.provider.dtos.ProviderDto;
import com.romo.manzanares.provider.dtos.UpdateProviderDto;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/provider")
@Validated
public class ProviderController {
    private final ProviderRepository providerRepository;
    private final ModelMapper modelMapper;

    public ProviderController(ProviderRepository providerRepository, ModelMapper modelMapper) {
        this.providerRepository = providerRepository;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all providers")
    @GetMapping
    public List<ProviderDto> findAllProviders() {
        return providerRepository.findAll().stream().map(providerDto -> modelMapper.map(providerDto, ProviderDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{providerId}")
    public ProviderDto findProviderById(@PathVariable("providerId") int providerId) {
        return modelMapper.map(providerRepository.findById(providerId).orElseThrow(() -> new RuntimeException("Provider not found")), ProviderDto.class);
    }

    @PostMapping
    public ProviderDto addProvider(@RequestBody CreateProviderDto providerDto) {
        Provider provider = modelMapper.map(providerDto, Provider.class);
        return modelMapper.map(providerRepository.save(provider), ProviderDto.class);
    }

    @PutMapping
    public ProviderDto updateProvider(@RequestBody UpdateProviderDto providerDto) {
        Provider provider = modelMapper.map(providerDto, Provider.class);
        return modelMapper.map(providerRepository.save(provider), ProviderDto.class);
    }

    @DeleteMapping("/{providerId}")
    public String deleteProvider(@PathVariable("providerId") int providerId) {
        Provider provider = providerRepository.findById(providerId).orElseThrow(() -> new RuntimeException("Provider not found"));
        providerRepository.delete(provider);
        return "Deleted provider with id: " + providerId;
    }
}
