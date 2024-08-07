package org.example.salecomputercomponent.controller;


import org.example.salecomputercomponent.entities.OrderDetail;
import org.example.salecomputercomponent.entities.Orders;
import org.example.salecomputercomponent.entities.Users;
import org.example.salecomputercomponent.services.impl.OrderDatailProcessimpl;
import org.example.salecomputercomponent.services.impl.OrderProcessimpl;
import org.example.salecomputercomponent.services.impl.ProductProcessImpl;
import org.example.salecomputercomponent.services.impl.UserProcessimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderAdminController {

    @Autowired
    private OrderProcessimpl orderProcess;

    @Autowired
    private OrderDatailProcessimpl orderDetailProcess;;

    @GetMapping
    public String listOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Orders> orderPage = orderProcess.getAllOrder(pageable);

        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());

        return "admin/orderAdmin";
    }

    @GetMapping("/detail/{id}")
    public String viewOrderDetail(@PathVariable("id") int id, Model model) {
        Orders order = orderProcess.getOrder(id);
        List<OrderDetail> orderDetail= orderDetailProcess.findByOrderId(id);
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetail);
        return "admin/orderDetail"; // Tên của template Thymeleaf để hiển thị chi tiết đơn hàng
    }




}
