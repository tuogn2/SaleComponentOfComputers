package org.example.salecomputercomponent.services;


import org.example.salecomputercomponent.entities.OrderDetail;

import java.util.List;

public interface OrderDetailProcess {
    boolean add(OrderDetail orderDetail);
    List<OrderDetail> getAllOrderDetail();
    List<OrderDetail> findByOrderId(int orderId);
    public boolean existsByProductId(int productId);
}
