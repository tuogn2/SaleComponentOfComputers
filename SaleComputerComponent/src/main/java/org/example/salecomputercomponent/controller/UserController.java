package org.example.salecomputercomponent.controller;

import jakarta.servlet.http.HttpSession;
import org.example.salecomputercomponent.entities.Users;
import org.example.salecomputercomponent.services.UserProcess;
import org.example.salecomputercomponent.util.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserProcess userService;

    @Autowired
    private SendMail sendMail;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") Users user, Model model, HttpSession session) {
        try {
            // Kiểm tra xem email đã tồn tại hay chưa
            if (userService.isEmailExisted(user.getEmail())) {
                model.addAttribute("error", "Email đã tồn tại, vui lòng chọn email khác.");
                return "register";  // Trả về lại form đăng ký với thông báo lỗi
            }

            // Nếu email chưa tồn tại, tiến hành đăng ký
            user.setRole("USER");  // Set default role to USER
            userService.addUser(user);
            // Gửi email xác nhận đăng ký
            sendMail.sendRegistrationEmail(user.getEmail(), user.getName());
            // Lưu thông tin người dùng vào session
            session.setAttribute("loggedInUser", user);

            return "redirect:/";  // Chuyển hướng về trang chủ khi đăng ký thành công
        } catch (Exception e) {
            model.addAttribute("error", "Đăng ký thất bại, vui lòng thử lại.");  // Thêm thông báo lỗi
            return "register";  // Trả về lại form đăng ký với thông báo lỗi
        }
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }



    @PostMapping("/login")
    public String loginSubmit(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("role") String role,
                              HttpSession session,
                              Model model) {

        // Ví dụ: Logic xác thực tài khoản
        boolean isAuthenticated = userService.isAuthenticated(username, password, role);

        if (isAuthenticated) {
            // Tạo đối tượng Users (thay thế bằng logic lấy thông tin từ cơ sở dữ liệu)
            Users loggedInUser = new Users();
            loggedInUser.setEmail(username);
            loggedInUser.setRole(role);

            // Lưu thông tin người dùng vào session
            session.setAttribute("loggedInUser", loggedInUser);

            if(loggedInUser.getRole().equals("ADMIN")){
                return "redirect:/admin";
            }
            return "redirect:/";
        } else {
            model.addAttribute("error", "Thông tin đăng nhập không chính xác.");
            return "login"; // Trả về trang đăng nhập với thông báo lỗi
        }
    }

    @GetMapping("/logout")
    public String logoutPage(HttpSession session) {
        // Xóa thông tin người dùng khỏi session
        session.removeAttribute("loggedInUser");
        session.invalidate(); // Xóa toàn bộ session nếu cần thiết

        return "redirect:/user/login"; // Chuyển hướng về trang đăng nhập sau khi đăng xuất
    }


}
