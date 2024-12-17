package com.romo.manzanares.location;

import com.romo.manzanares.location.dtos.CreateLocationDto;
import com.romo.manzanares.location.dtos.LocationDto;
import com.romo.manzanares.location.dtos.UpdateLocationDto;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/location")
@Validated
public class LocationController {
    private final LocationRepository locationRepository;
    private final ModelMapper modelMapper;

    public LocationController(LocationRepository locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<LocationDto> getAllLocations() {
        return locationRepository
                .findAll()
                .stream()
                .map(location -> modelMapper.map(location, LocationDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{locationId}")
    public LocationDto getLocationById(@PathVariable int locationId) {
        return modelMapper.map(locationRepository.findById(locationId).orElseThrow(() -> new RuntimeException("Location not found")), LocationDto.class);
    }

    @PostMapping
    public Location createLocation(@Validated @RequestBody CreateLocationDto locationDto) {
        Location location = modelMapper.map(locationDto, Location.class);
        return modelMapper.map(locationRepository.save(location), Location.class);
    }

    @PutMapping
    public Location updateLocation(@Validated @RequestBody UpdateLocationDto locationDto) {
        Location location = modelMapper.map(locationDto, Location.class);
        return modelMapper.map(locationRepository.save(location), Location.class);
    }

    @DeleteMapping
    public String deleteLocation(@RequestParam int locationId) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new RuntimeException("Location not found"));
        locationRepository.delete(location);
        return "Deleted location with id: " + locationId;
    }
}
