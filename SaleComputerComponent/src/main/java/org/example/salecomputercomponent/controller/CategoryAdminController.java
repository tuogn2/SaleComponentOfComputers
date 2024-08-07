package org.example.salecomputercomponent.controller;


import org.example.salecomputercomponent.entities.Category;
import org.example.salecomputercomponent.entities.Product;
import org.example.salecomputercomponent.services.impl.CategoryProcessImpl;
import org.example.salecomputercomponent.services.impl.ProductProcessImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryAdminController {

    @Autowired
    private CategoryProcessImpl categoryProcess;
    @Autowired
    private ProductProcessImpl productProcess;

    @GetMapping
    public String viewCategories(Model model) {
        List<Category> categories = categoryProcess.getAllCategories();
        model.addAttribute("categories", categories);
        return "/admin/categoryAdmin";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category-form";
    }


    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        categoryProcess.saveCategory(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable int id, Model model) {
        Category category = categoryProcess.getCategoryById(id);
        model.addAttribute("category", category);
        return "admin/category-form";
    }


    @PostMapping("/edit")
    public String editCategory(@ModelAttribute("category") Category category) {
        categoryProcess.saveCategory(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable int id, RedirectAttributes redirectAttributes) {
        // Kiểm tra nếu có sản phẩm liên quan đến danh mục
        if (productProcess.existProductByCategoryId(id)) {
            // Thêm thông báo lỗi vào RedirectAttributes
            redirectAttributes.addFlashAttribute("errorMessage", "Danh mục không thể xóa vì đã có sản phẩm liên quan.");

            return "redirect:/admin/category";
        }

        // Xóa danh mục nếu không có sản phẩm liên quan
        categoryProcess.deleteById(id);
        return "redirect:/admin/category";
    }

}
