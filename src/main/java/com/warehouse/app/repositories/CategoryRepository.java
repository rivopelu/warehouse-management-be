package com.warehouse.app.repositories;

import com.warehouse.app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("select c from Category as c where c.slug in :name order by c.name limit 1")
    Optional<Category> findAllBySlug(List<String> name);
}
