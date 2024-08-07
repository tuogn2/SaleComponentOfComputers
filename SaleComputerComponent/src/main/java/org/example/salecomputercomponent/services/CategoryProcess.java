package org.example.salecomputercomponent.services;

import org.example.salecomputercomponent.entities.Category;

import java.util.List;

public interface CategoryProcess {

    public List<Category> getAllCategories();
    public Category getCategoryById(int id);
    public boolean saveCategory(Category category);
    public boolean deleteById(int id);
}
