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
//    public String generateOtp()
//    {
//        Integer otp= (int) (Math.random()*900000) +100000;
//        return String.valueOf(otp);
//    }

    public void saveUser(Users users)
    {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        users.setRole("ROLE_USER");
        usersRepo.save(users);
    }

//    public void sendMail(String toemail, String otp)
//    {
//        SimpleMailMessage message=new SimpleMailMessage();
//        message.setFrom("vaibhavnarode56@gmail.com");
//        message.setTo(toemail);
//        message.setSubject("Your OTP Verification Code");
//        message.setText("Your Otp is "+otp+"\n Do not share with anyone");
//        javaMailSender.send(message);
//    }

//    public void sendMail(String toEmail, String otp){
//        emailService.sendEmail(toEmail,"Register with OTP","Your OTP is "+otp);
//    }

    public Users getCurrentUser(String email)
    {
        Users user=usersRepo.findByEmail(email);
        return user;
    }
}
