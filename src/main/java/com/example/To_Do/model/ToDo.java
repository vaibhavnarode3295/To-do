package com.example.To_Do.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="todo")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 200)
    private String title;
    private String status;
    @Column(length = 2000)
    private String description;

    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

}
