package com.romo.manzanares.employee;

import com.romo.manzanares.employee.dtos.CreateEmployeeDto;
import com.romo.manzanares.employee.dtos.EmployeeDto;
import com.romo.manzanares.employee.dtos.UpdateEmployeeDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeController(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<EmployeeDto> findAllEmployees() {
        return employeeRepository.findAll().stream().map(employee -> modelMapper.map(employee, EmployeeDto.class)).collect(Collectors.toList());
    }

    @GetMapping("/{employeeId}")
    public EmployeeDto findEmployeeById(@PathVariable("employeeId") int employeeId) {
        return modelMapper.map(employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found")), EmployeeDto.class);
    }

    @PostMapping
    public EmployeeDto addEmployee(@RequestBody CreateEmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        return modelMapper.map(employeeRepository.save(employee), EmployeeDto.class);
    }

    @PutMapping
    public EmployeeDto updateEmployee(@RequestBody UpdateEmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        return modelMapper.map( employeeRepository.save(employee), EmployeeDto.class);
    }

    @DeleteMapping("/{providerId}")
    public String deleteEmployee(@PathVariable("employeeId") int employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepository.delete(employee);
        return "Deleted employee with id: " + employeeId;
    }
}
