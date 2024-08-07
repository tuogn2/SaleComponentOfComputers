package org.example.salecomputercomponent.controller;

import org.example.salecomputercomponent.entities.Category;
import org.example.salecomputercomponent.entities.Product;

import org.example.salecomputercomponent.services.impl.CategoryProcessImpl;
import org.example.salecomputercomponent.services.impl.ProductProcessImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private CategoryProcessImpl categoryProcess;

    @Autowired
    private ProductProcessImpl productProcess;


    @GetMapping
    public String getProducts(Model model, @RequestParam(defaultValue = "1") int page) {
        List<Category> categories = categoryProcess.getAllCategories();
        List<Product> products = productProcess.getAllProduct();

        int pageSize = 12; // Số sản phẩm mỗi trang
        int totalProducts = products.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        // Tính toán vị trí bắt đầu và kết thúc của trang hiện tại
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalProducts);
        List<Product> pagedProducts = products.subList(start, end);

        model.addAttribute("products", pagedProducts);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "index"; // Tên của file HTML
    }

    @GetMapping("/detail/{id}")
    public String getProductDetail(@PathVariable("id") int id, Model model) {
        List<Category> categories = categoryProcess.getAllCategories();
        Product product = productProcess.getProductById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("product", product);
        return "productDetail"; // Tên của file HTML
    }


}
