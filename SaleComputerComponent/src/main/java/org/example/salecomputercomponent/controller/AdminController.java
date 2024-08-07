package org.example.salecomputercomponent.controller;

import jakarta.servlet.http.HttpSession;
import org.example.salecomputercomponent.entities.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final HttpSession httpSession;

    public AdminController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping
    public String adminPage() {
        Users loggedInUser = (Users) httpSession.getAttribute("loggedInUser");

        if (loggedInUser != null && "ADMIN".equals(loggedInUser.getRole())) {
            return "/admin/admin";
        } else {

            return "redirect:/"; // Chuyển hướng về trang chính hoặc trang đăng nhập
        }
    }

}
