package org.example.salecomputercomponent.repositories;

import org.example.salecomputercomponent.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}