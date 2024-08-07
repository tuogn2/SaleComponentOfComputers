package org.example.salecomputercomponent.controller;


import org.example.salecomputercomponent.entities.Users;
import org.example.salecomputercomponent.services.impl.OrderProcessimpl;
import org.example.salecomputercomponent.services.impl.ProductProcessImpl;
import org.example.salecomputercomponent.services.impl.UserProcessimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class UserAdminController {

    @Autowired
    private UserProcessimpl userProcess;
//    @Autowired
//    private ProductProcessImpl productProcess;

    @Autowired
    private OrderProcessimpl orderProcess;

    @GetMapping
    public String listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            Model model
    ) {
        Page<Users> userPage = userProcess.getUsers(PageRequest.of(page, size));
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        return "admin/userAdmin";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {

        if (orderProcess.existsByUserId(id)) {
            // Thêm thông báo lỗi vào RedirectAttributes
            redirectAttributes.addFlashAttribute("errorMessage", " không thể xóa vì nguời dung đã từng mua hàng");

            return "redirect:/admin/users";
        }

        // Xóa danh mục nếu không có sản phẩm liên quan
        userProcess.deleteById(id);
        return "redirect:/admin/users";

    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Users user = userProcess.getUserById(id);
        System.out.println(user);
        if (user == null) {
            return "error/404";
        }
        model.addAttribute("user", user);
        return "admin/userAdmin_form";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable int id, @ModelAttribute Users user) {
        Users existingUser = userProcess.getUserById(id);
        if (existingUser == null) {
            return "error/404";
        }
        existingUser.setName(user.getName());
        existingUser.setAddress(user.getAddress());
        existingUser.setRole(user.getRole());

        userProcess.addUser(existingUser);
        return "redirect:/admin/users";
    }
}
