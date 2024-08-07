package org.example.salecomputercomponent.entities;


import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int productid;

    private String name;
    private String description;
    private float price;
    private float quantity;
    private String image;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

    public Product() {

    }

    public int getProductid() {
        return productid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }

    public Category getCategory() {
        return category;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productid=" + productid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                ", category=" + category +
                '}';
    }

    public Product(int productid, String name, String description, float price, float quantity, String image, Category category) {
        this.productid = productid;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.category = category;
    }
}
