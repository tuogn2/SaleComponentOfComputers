package org.example.salecomputercomponent.controller;


import org.example.salecomputercomponent.entities.Category;
import org.example.salecomputercomponent.entities.Product;
import org.example.salecomputercomponent.services.impl.CategoryProcessImpl;
import org.example.salecomputercomponent.services.impl.ProductProcessImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryProcessImpl categoryProcess;
    @Autowired
    private ProductProcessImpl productProcess;

    @GetMapping
    public String showProductByCategory(Model model, @RequestParam(name = "categoryid",defaultValue = "1") int category) {
        List<Category> ca = categoryProcess.getAllCategories();
        List<Product> pr = productProcess.getProductByCategory(category);
        Category selectedCategory = categoryProcess.getCategoryById(category);
        String categoryName = (selectedCategory != null) ? selectedCategory.getName() : "Unknown Category";

        model.addAttribute("categoryName", categoryName);
        model.addAttribute("products", pr);
        model.addAttribute("categories", ca);
        return "productByCategory";

    }
}
