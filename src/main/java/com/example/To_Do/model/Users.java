package com.example.To_Do.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="todo_users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToDo> todos = new ArrayList<>();

}
