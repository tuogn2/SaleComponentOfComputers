package org.example.salecomputercomponent.services.impl;

import org.example.salecomputercomponent.entities.Orders;
import org.example.salecomputercomponent.repositories.OrdersRepository;
import org.example.salecomputercomponent.services.OrderProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessimpl implements OrderProcess {

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public boolean add(Orders orders) {
        return ordersRepository.save(orders) != null;
    }

    @Override
    public Page<Orders> getAllOrder(Pageable pageable){
        return ordersRepository.findAll(pageable);
    }
    @Override
    public Orders getOrder(int id){
        return ordersRepository.findById(id).get();
    }

    @Override
    public boolean existsByUserId(int id) {
        return ordersRepository.existsByUser_Id(id);
    }


}
