package com.example.To_Do.service;

import com.example.To_Do.model.Users;
import com.example.To_Do.repository.UsersRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailService emailService;

    public void saveUser(Users users)
    {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        users.setRole("ROLE_USER");
        usersRepo.save(users);
    }

    public Users getCurrentUser(String email)
    {
        Users user=usersRepo.findByEmail(email);
        return user;
    }
}
