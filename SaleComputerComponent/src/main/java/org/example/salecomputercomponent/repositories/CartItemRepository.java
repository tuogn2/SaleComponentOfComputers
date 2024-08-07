package org.example.salecomputercomponent.repositories;

import org.example.salecomputercomponent.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}