package org.example.salecomputercomponent.services;

import org.example.salecomputercomponent.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductProcess {

    public List<Product> getAllProduct();
    public Product getProductById(int id);
    public List<Product> getProductByCategory(int id);
    public boolean existProductByCategoryId(int id);
    public boolean saveProduct(Product product);
    public boolean deleteProductById(int id);
    public boolean updateProduct(Product product);
    public Page<Product> getAllProduct(Pageable pageable);
}
