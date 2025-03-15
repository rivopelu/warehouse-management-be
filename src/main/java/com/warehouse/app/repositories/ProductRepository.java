package com.warehouse.app.repositories;

import com.warehouse.app.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsBySlugAndActiveIsTrue(String slug);

    @Query("SELECT p FROM Product p WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND (:categoryId IS NULL OR p.category.id = :categoryId)")
    Page<Product> findListAndFilter(Pageable pageable, @Param("keyword") String keyword, @Param("categoryId") String categoryId);
}
