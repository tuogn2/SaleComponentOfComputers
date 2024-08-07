package org.example.salecomputercomponent.repositories;

import org.example.salecomputercomponent.entities.OrderDetail;
import org.example.salecomputercomponent.entities.Orders;
import org.example.salecomputercomponent.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Orders> {

    List<OrderDetail> findByOrderId(int orderId);
    boolean existsByProduct_Productid(int productid);


}