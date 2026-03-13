package com.example.To_Do.controller;

import com.example.To_Do.model.Users;
import com.example.To_Do.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String TodoWelcome()
    {
        return "welcome";
    }
    @GetMapping("/register")
    public String register(Model model)
    {
        log.info("/register end point is hit");
        model.addAttribute("user",new Users());
        return "registration";
    }


    @PostMapping("/saveUser")
    public String registerUser(@ModelAttribute Users user){
        userService.saveUser(user);
        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/resend-otp")
    public String resendOtp(HttpSession session)
    {
        return "redirect:saveUser";
    }

    @GetMapping("/logout")
    public String logout()
    {
        return "logout";
    }

}
