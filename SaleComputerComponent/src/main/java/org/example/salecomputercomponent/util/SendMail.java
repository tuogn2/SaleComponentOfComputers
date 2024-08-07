package org.example.salecomputercomponent.util;

import org.example.salecomputercomponent.entities.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SendMail {

    @Autowired
    private JavaMailSender emailSender;

    public void sendRegistrationEmail(String recipientEmail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Registration Confirmation");
        message.setText("Dear " + username + ",\n\nThank you for registering with us.");
        emailSender.send(message);
    }

    public void sendCartConfirmationEmail(String recipientEmail, String username, List<CartItem> cartItems) {
        if (recipientEmail == null) {
            // Log or handle the case where recipientEmail is null
            System.out.println("Recipient email is null. Cannot send email.");
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Xác nhận đơn hàng");
        message.setText("Xin chào " + username + ",\n\nCám ơn bạn đã đặt hàng với chúng tôi.\n\nThông tin đơn hàng:");

        // Add cart items details to email
        for (CartItem item : cartItems) {
            message.setText(message.getText() + "\n- " + item.getProduct().getName() + ": " + item.getQuantity() + " x " + item.getProduct().getPrice() + " VNĐ");
        }

        emailSender.send(message);
    }
}
