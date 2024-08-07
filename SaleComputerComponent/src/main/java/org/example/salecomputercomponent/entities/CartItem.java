package org.example.salecomputercomponent.entities;

import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartitemid;

    
    @OneToOne
    @JoinColumn(name = "productid")
    private Product product;
    
    private float quantity;


    @ManyToOne
    @JoinColumn(name = "cartid")
    private Cart cart;

    public int getCartitemid() {
        return cartitemid;
    }

    public void setCartitemid(int cartitemid) {
        this.cartitemid = cartitemid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartitemid=" + cartitemid +
                ", product=" + product +
                ", quantity=" + quantity +
                ", cart=" + cart +
                '}';
    }

    public CartItem() {

    }

    public CartItem(int cartitemid, Product product, float quantity, Cart cart) {
        this.cartitemid = cartitemid;
        this.product = product;
        this.quantity = quantity;
        this.cart = cart;
    }
}
