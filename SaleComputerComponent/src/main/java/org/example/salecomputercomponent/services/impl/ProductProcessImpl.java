package org.example.salecomputercomponent.services.impl;

import org.example.salecomputercomponent.entities.Product;
import org.example.salecomputercomponent.repositories.ProductRepository;
import org.example.salecomputercomponent.services.ProductProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductProcessImpl implements ProductProcess {



    @Autowired
    private ProductRepository productRepository;
    @Override
    public boolean saveProduct(Product product) {
        return productRepository.save(product) != null;
    }

    @Override
    public boolean deleteProductById(int id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }

    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getProductByCategory(int id) {
        return productRepository.findByCategoryId(id);
    }

    @Override
    public boolean existProductByCategoryId(int id) {
        return productRepository.existsByCategoryId(id);
    }


}
