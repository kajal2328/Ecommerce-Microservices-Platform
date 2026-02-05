package com.kajal.ecommerce.Product.Controller;

import com.kajal.ecommerce.Product.DTO.ProductRequest;
import com.kajal.ecommerce.Product.DTO.ProductResponse;
import com.kajal.ecommerce.Product.Service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController
{
    private ProductService productService;

    @PostMapping("/add")
    private ResponseEntity<ProductResponse> addProducts(@RequestBody ProductRequest productRequest)
    {
        return new ResponseEntity<ProductResponse>(productService.createProduct(productRequest), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    private ResponseEntity<List<ProductResponse>> getAllProducts()
    {
        return new ResponseEntity<>( productService.fetchAllProducts(),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    private ResponseEntity<Optional<ProductResponse>> getProductById(@PathVariable Long id)
    {
        return new ResponseEntity<>(productService.getProductById(id),HttpStatus.FOUND);
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<ProductResponse> updateProductById(@PathVariable Long id, @RequestBody ProductRequest productRequest)
    {
        return new ResponseEntity<ProductResponse>(productService.updateProduct(id,productRequest),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<Void> deleteProductById(@PathVariable Long id)
    {
        boolean deleted=productService.deleteProductById(id);


        return deleted ? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();

    }

    @GetMapping("/search")
    private ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword)
    {
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }
}
