package org.example.salecomputercomponent.services.impl;

import org.example.salecomputercomponent.entities.Users;
import org.example.salecomputercomponent.repositories.UsersRepository;
import org.example.salecomputercomponent.services.UserProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProcessimpl implements UserProcess  {
    @Autowired
    private UsersRepository usersRepository;


    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public boolean addUser(Users user) {
        return usersRepository.save(user) != null;
    }

    @Override
    public boolean isAuthenticated(String email, String password, String role) {
        Users user = usersRepository.findByEmail(email);

        // Kiểm tra xem người dùng tồn tại và mật khẩu khớp (plaintext)
        if (user != null && password.equals(user.getPassword())) {
            // Kiểm tra vai trò (role) của người dùng
            if (role.equals(user.getRole())) {
                return true; // Người dùng xác thực thành công
            }
        }
        return false; // Người dùng không xác thực thành công
    }

    @Override
    public boolean isEmailExisted(String email) {
        Users user = usersRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Page<Users> getUsers(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    @Override
    public Users getUserById(int id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteById(int id) {
        try {
            usersRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
