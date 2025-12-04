package com.example.To_Do.repository;

import com.example.To_Do.model.ToDo;
import com.example.To_Do.model.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;

public interface ToDoRepo extends JpaRepository<ToDo,Long> {
    List<ToDo> findAllByUserAndCreatedAt(Users user, LocalDate localDate);
    List<ToDo> findAllByUserAndCreatedAtBetween(Users user,LocalDate start,LocalDate end);
    Page<ToDo> findAllByUserOrderByCreatedAtDesc(Users user, Pageable pageable);
}
