package org.example.salecomputercomponent.repositories;

import org.example.salecomputercomponent.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}