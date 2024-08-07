package org.example.salecomputercomponent.services.impl;

import org.example.salecomputercomponent.entities.Category;
import org.example.salecomputercomponent.repositories.CategoryRepository;
import org.example.salecomputercomponent.services.CategoryProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryProcessImpl implements CategoryProcess {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);

    }

    @Override
    public boolean saveCategory(Category category) {
        return categoryRepository.save(category) != null;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            categoryRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
