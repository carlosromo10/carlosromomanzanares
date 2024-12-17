package com.romo.manzanares.product;

import com.romo.manzanares.product.dtos.CreateProductDto;
import com.romo.manzanares.product.dtos.ProductDto;
import com.romo.manzanares.product.dtos.UpdateProductDto;
import com.romo.manzanares.provider.Provider;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductController(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{productUpc}")
    public ProductDto getProductByUpc(@PathVariable long productUpc) {
        return modelMapper.map(productRepository
                .findById(productUpc)
                .orElseThrow(() -> new RuntimeException("Product not found")), ProductDto.class);
    }

    @PostMapping
    public ProductDto addProduct(@Valid @RequestBody CreateProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    @PutMapping
    public ProductDto updateProduct(@Valid @RequestBody UpdateProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        return modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@Valid @PathVariable long productId ) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
        return "Deleted product with id: " + productId;
    }
}
