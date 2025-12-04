package com.example.To_Do.repository;

import com.example.To_Do.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users,Long> {
    Users findByEmail(String username);
}
