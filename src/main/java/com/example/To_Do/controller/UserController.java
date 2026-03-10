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
    public String saveUser(@ModelAttribute Users user, HttpSession session)
    {
        log.info("registration done");
        String otp = userService.generateOtp();
        log.info("otp generated");
        session.setAttribute("otp",otp);
        session.setAttribute("user",user);
        log.info("otp is "+otp);
        userService.sendMail(user.getEmail(), otp);
        log.info("email is send to the user");
        return "verify-otp";
    }

    @PostMapping("/verify-otp")
    public String OtpVerify(@RequestParam String otpInput, HttpSession session)
    {
        String getOtp = (String) session.getAttribute("otp");
        Users user= (Users) session.getAttribute("user");
        System.out.println(getOtp);
        System.out.println(otpInput);

        if(getOtp.equals(otpInput))
        {
            userService.saveUser(user);
            session.removeAttribute("otp");
            session.removeAttribute("user");
            return "login";
        }
        return "wrong-otp";
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
