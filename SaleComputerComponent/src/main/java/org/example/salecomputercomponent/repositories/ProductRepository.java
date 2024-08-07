package org.example.salecomputercomponent.repositories;

import org.example.salecomputercomponent.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(int categoryId);
    boolean existsByCategoryId(int categoryId);

}