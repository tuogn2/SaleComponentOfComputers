package org.example.salecomputercomponent.services;

import org.example.salecomputercomponent.entities.CartItem;
import org.example.salecomputercomponent.entities.Category;

import java.math.BigDecimal;
import java.util.List;

public interface CartProcess {

    public void updateQuantity(Long productId, Integer newQuantity);
    public BigDecimal calculateTotalPrice();


}
