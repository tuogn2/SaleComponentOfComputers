package org.example.salecomputercomponent.repositories;

import org.example.salecomputercomponent.entities.Product;
import org.example.salecomputercomponent.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByEmail(String email);

}