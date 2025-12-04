package com.example.To_Do.service;

import com.example.To_Do.model.Users;
import com.example.To_Do.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=usersRepo.findByEmail(username);
        if(users==null)
        {
            throw new UsernameNotFoundException("User Not Found");
        }
        return new CustomeUserDetail(users);
    }
}
