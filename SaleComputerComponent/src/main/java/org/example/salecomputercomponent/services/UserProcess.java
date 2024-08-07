package org.example.salecomputercomponent.services;

import org.example.salecomputercomponent.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserProcess {
    List<Users> getAllUsers();
    boolean addUser(Users user);
    boolean isAuthenticated(String email, String password, String role);
    boolean isEmailExisted(String email);
    Users getUserByEmail(String email);
    Page<Users> getUsers(Pageable pageable);
    public  Users getUserById(int id);
    public boolean deleteById(int id);
}
