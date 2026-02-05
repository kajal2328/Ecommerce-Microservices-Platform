package com.kajal.ecommerce.Product.Repository;

import com.kajal.ecommerce.Product.entity.Product;
import org.apache.catalina.mapper.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Collection<Product> findByActiveTrue();

    @Query("SELECT p FROM Product p WHERE p.active=true AND p.stockQuantity>0 AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword ,'%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);

    Optional<Product> findByIdAndActiveTrue(Long id);
}
