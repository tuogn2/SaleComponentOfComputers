package org.example.salecomputercomponent.controller;


import jakarta.servlet.http.HttpSession;
import org.example.salecomputercomponent.entities.*;
import org.example.salecomputercomponent.services.CartProcess;
import org.example.salecomputercomponent.services.OrderProcess;
import org.example.salecomputercomponent.services.ProductProcess;
import org.example.salecomputercomponent.services.impl.*;
import org.example.salecomputercomponent.util.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CategoryProcessImpl categoryProcess;
    @Autowired
    private ProductProcessImpl productProcess;

    @Autowired
    private CartProcessImpl cartProcess;

    @Autowired
    private OrderProcessimpl OrderProcess;

    @Autowired
    private OrderDatailProcessimpl orderDetailProcess;

    @Autowired
    private UserProcessimpl UserProcess;

    @Autowired
    private SendMail sendMail;
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") int productId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        // Check if the product already exists in cart
        boolean productExists = false;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductid() == productId) {
                // Product already exists, increase the quantity
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                productExists = true;
                break;
            }
        }

        // If product does not exist, add new CartItem
        if (!productExists) {
            // Here you should fetch the product from the database
            Product product = productProcess.getProductById(productId);
            if (product != null) {
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItems.add(cartItem);
            }
        }

        session.setAttribute("cartItems", cartItems);
        return "redirect:/cart";
    }


    @GetMapping
    public String showCart(HttpSession session, Model model) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        double totalPrice = 0;
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                totalPrice += item.getProduct().getPrice() * item.getQuantity();
            }
        }
        List<Category> ca = categoryProcess.getAllCategories();
        model.addAttribute("cartItems", cartItems != null ? cartItems : new ArrayList<>());
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("categories", ca);
        return "cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("productId") int productId, HttpSession session) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        if (cartItems != null) {
            cartItems.removeIf(item -> item.getProduct().getProductid() == productId);
        }
        session.setAttribute("cartItems", cartItems);
        return "redirect:/cart";
    }


    @PostMapping("/updateQuantity")
    @ResponseBody
    public ResponseEntity<?> updateQuantity(@RequestBody Map<String, Object> requestData, HttpSession session) {
        try {
            int productId = Integer.parseInt(requestData.get("productId").toString());
            int newQuantity = Integer.parseInt(requestData.get("newQuantity").toString());
            // Lấy giỏ hàng từ session
            List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
            if (cartItems == null) {
                return ResponseEntity.badRequest().build(); // Trả về lỗi 400 nếu giỏ hàng trống
            }

            // Cập nhật số lượng sản phẩm trong giỏ hàng
            for (CartItem cartItem : cartItems) {
                if (cartItem.getProduct().getProductid() == productId){
                    cartItem.setQuantity(newQuantity);
                    break;
                }
            }

            // Tính toán lại tổng giá trị giỏ hàng
            BigDecimal totalPrice = cartProcess.calculateTotalPrice();

            // Lưu giỏ hàng đã cập nhật vào session
            session.setAttribute("cartItems", cartItems);

            // Trả về JSON chứa totalPrice mới
            Map<String, Object> response = new HashMap<>();
            response.put("totalPrice", totalPrice);
            return ResponseEntity.ok(response);
        } catch (NumberFormatException | NullPointerException e) {
            // Xử lý ngoại lệ nếu cần thiết
            return ResponseEntity.badRequest().build(); // Trả về lỗi 400 nếu dữ liệu không hợp lệ
        }
    }


    @PostMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");

        if (cartItems == null || cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        Users loggedInUser = (Users) session.getAttribute("loggedInUser");
        Users user = UserProcess.getUserByEmail(loggedInUser.getEmail());


        if (loggedInUser == null) {
            return "redirect:/user/login";
        }

        // Tạo đối tượng Orders
        Orders order = new Orders();
        order.setUser(user);
        order.setTotalPrice(cartProcess.calculateTotalPrice().floatValue());
        order.setStatus("Pending");
        order.setCreatedAt(new Date());

        // Lưu Orders vào cơ sở dữ liệu
        OrderProcess.add(order);

        // Tạo và lưu OrderDetail cho từng sản phẩm trong giỏ hàng
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getProduct().getPrice());

            orderDetailProcess.add(orderDetail);
        }

        // Gửi email xác nhận
        String recipientEmail = user.getEmail();
        String username = user.getName();
        BigDecimal totalPrice = cartProcess.calculateTotalPrice();
        sendMail.sendCartConfirmationEmail(recipientEmail, username, cartItems);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);

        // Xóa giỏ hàng sau khi đã lưu
        session.removeAttribute("cartItems");

        return "complete";
    }
}
