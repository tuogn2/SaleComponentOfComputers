package org.example.salecomputercomponent.repositories;

import org.example.salecomputercomponent.entities.OrderDetail;
import org.example.salecomputercomponent.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    boolean existsByUser_Id(int userId);
}