package com.kajal.ecommerce.Product.Service;

import com.kajal.ecommerce.Product.DTO.ProductRequest;
import com.kajal.ecommerce.Product.DTO.ProductResponse;
import com.kajal.ecommerce.Product.Repository.ProductRepository;
import com.kajal.ecommerce.Product.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;


    public ProductResponse createProduct(ProductRequest productRequest)
    {
        Product product=new Product();

        updateProductFromRequest(product,productRequest);

        productRepository.save(product);

        return  mapToProductResponse(product);
    }

    public List<ProductResponse> fetchAllProducts()
    {
        return productRepository.findByActiveTrue().stream().map(this::mapToProductResponse).
                toList();
    }

    public Optional<ProductResponse> getProductById(Long id)
    {
        return productRepository.findByIdAndActiveTrue(id).map(this::mapToProductResponse);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest)
    {
        Optional<Product> existingProductOptional= productRepository.findById(id);

        if(existingProductOptional.isPresent())
        {
            Product existingProduct=existingProductOptional.get();

            updateProductFromRequest(existingProduct,productRequest);
            productRepository.save(existingProduct);

            return  mapToProductResponse(existingProduct);
        }
        else {
            return null;
        }

    }

    public boolean deleteProductById(Long id)
    {

        return productRepository.findById(id).
                map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    private ProductResponse mapToProductResponse(Product product)
    {
        ProductResponse response=new ProductResponse();

        response.setId(String.valueOf(product.getId()));
        response.setName(product.getName());
        response.setCategory(product.getCategory());
        response.setPrice(product.getPrice());
        response.setDescription(product.getDescription());
        response.setImageUrl(product.getImageUrl());
        response.setStockQuantity(product.getStockQuantity());
        response.setActive(product.isActive());

        return response;

    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest)
    {
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setActive(productRequest.getActive());
    }


    public List<ProductResponse> searchProduct(String keyword)
    {
        return productRepository.searchProducts(keyword).stream().map(this::mapToProductResponse).
                toList();
    }
}
