package org.example.salecomputercomponent.services;



import org.example.salecomputercomponent.entities.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderProcess {
    boolean add(Orders orders);
    public Page<Orders> getAllOrder(Pageable pageable);
    public Orders getOrder(int id);
    public boolean existsByUserId(int id);
}
