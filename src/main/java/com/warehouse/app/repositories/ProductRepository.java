package com.warehouse.app.repositories;

import com.warehouse.app.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, String> {
}
