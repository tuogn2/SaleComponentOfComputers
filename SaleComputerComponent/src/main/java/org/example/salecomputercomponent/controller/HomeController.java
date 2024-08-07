package org.example.salecomputercomponent.controller;


import jakarta.servlet.http.HttpSession;
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
public class HomeController {
    @Autowired
    private CategoryProcessImpl categoryProcess;

    @Autowired
    private ProductProcessImpl productProcess;

    @RequestMapping
    public String showMyPage(Model model,@RequestParam(defaultValue = "1") int page) {
        List<Category> ca = categoryProcess.getAllCategories();
        List<Product> pr = productProcess.getAllProduct();

        int pageSize = 12; // Số sản phẩm mỗi trang
        int totalProducts = pr.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        // Tính toán vị trí bắt đầu và kết thúc của trang hiện tại
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, totalProducts);
        List<Product> products = pr.subList(start, end);

        model.addAttribute("products", products);
        model.addAttribute("categories", ca);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "index";

    }





}
