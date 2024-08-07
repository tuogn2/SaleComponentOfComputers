package org.example.salecomputercomponent.services.impl;

import org.example.salecomputercomponent.entities.OrderDetail;
import org.example.salecomputercomponent.entities.Orders;
import org.example.salecomputercomponent.repositories.OrderDetailRepository;
import org.example.salecomputercomponent.repositories.OrdersRepository;
import org.example.salecomputercomponent.services.OrderDetailProcess;
import org.example.salecomputercomponent.services.OrderProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDatailProcessimpl implements OrderDetailProcess {

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Override
    public boolean add(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail) != null;
    }

    @Override
    public List<OrderDetail> getAllOrderDetail() {
        return orderDetailRepository.findAll();
    }

    @Override
    public List<OrderDetail> findByOrderId(int orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public boolean existsByProductId(int productId) {
        return orderDetailRepository.existsByProduct_Productid(productId);
    }


}
