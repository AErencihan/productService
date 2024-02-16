package com.example.productservice.repository;

import com.example.productservice.model.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @NotNull
    Optional<Product> findByProductName(@NotNull String productName);

}
