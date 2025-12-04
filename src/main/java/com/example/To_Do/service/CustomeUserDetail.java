package com.example.To_Do.service;

import com.example.To_Do.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomeUserDetail implements UserDetails {
    private Users users;
    public CustomeUserDetail(Users users)
    {
        this.users=users;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(users.getRole());
        return List.of(authority);
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }
}
