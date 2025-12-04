package com.example.To_Do.service;

import com.example.To_Do.model.ToDo;
import com.example.To_Do.model.Users;
import com.example.To_Do.repository.ToDoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepo toDoRepo;

    public List<ToDo> todaysTask(Users user)
    {
        LocalDate localDate=LocalDate.now();
        List<ToDo> list=toDoRepo.findAllByUserAndCreatedAt(user,localDate);
        return list;
    }

    public void saveTask(ToDo todo,Users user)
    {
        todo.setCreatedAt(LocalDate.now());
        todo.setUser(user);
        todo.setStatus("Pending");
        toDoRepo.save(todo);
    }

    public ToDo getToDo(Long id)
    {
        ToDo todo=toDoRepo.findById(id).orElseThrow(()->new IllegalArgumentException("RRecord not found"));
        return todo;
    }

    public void updateTodo(ToDo toDo)
    {
        toDo.setStatus("Completed");
        toDoRepo.save(toDo);
    }
    public void deleteTodo(Long id)
    {
        toDoRepo.deleteById(id);
    }

    public List<ToDo> seeLastSevenDaysData(Users user)
    {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusDays(7);
        List<ToDo> list=toDoRepo.findAllByUserAndCreatedAtBetween(user,start,end);

        return list;
    }

    public Page<ToDo> findAllTask(Users users, Pageable pageable)
    {
        Page<ToDo> list = toDoRepo.findAllByUserOrderByCreatedAtDesc(users,pageable);
        return list;
    }
}
