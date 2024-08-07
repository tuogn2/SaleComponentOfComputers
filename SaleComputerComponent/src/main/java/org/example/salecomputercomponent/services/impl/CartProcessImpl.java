package org.example.salecomputercomponent.services.impl;

import jakarta.servlet.http.HttpSession;
import org.example.salecomputercomponent.entities.CartItem;
import org.example.salecomputercomponent.repositories.CartRepository;
import org.example.salecomputercomponent.services.CartProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartProcessImpl implements CartProcess {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private HttpSession httpSession;

    @Override
    public void updateQuantity(Long productId, Integer newQuantity) {


    }

    @Override
    public BigDecimal calculateTotalPrice() {
        List<CartItem> cartItems = (List<CartItem>) httpSession.getAttribute("cartItems");
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            float price = cartItem.getProduct().getPrice();
            int quantity = (int) cartItem.getQuantity();

            BigDecimal priceBD = BigDecimal.valueOf(price);
            BigDecimal itemTotal = priceBD.multiply(BigDecimal.valueOf(quantity));
            totalPrice = totalPrice.add(itemTotal);
        }
        return totalPrice;
    }

}
