package com.example.To_Do.controller;

import com.example.To_Do.model.ToDo;
import com.example.To_Do.model.Users;
import com.example.To_Do.service.ToDoService;
import com.example.To_Do.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
public class ToDoController {
    @Autowired
    private ToDoService toDoService;
    @Autowired
    private UserService userService;

    @GetMapping("/todo")
    public String homepage(Model model, Principal principal)
    {
        Users users = userService.getCurrentUser(principal.getName());
        List<ToDo> list=toDoService.todaysTask(users);
        model.addAttribute("user",users);
        model.addAttribute("todo",new ToDo());
        model.addAttribute("todos",list);
        return "homepage";
    }
    @PostMapping("/save")
    public String saveTask(@ModelAttribute ToDo toDo, Principal principal)
    {
        Users user = userService.getCurrentUser(principal.getName());
        toDoService.saveTask(toDo,user);
        return "redirect:todo";
    }

    @GetMapping("/tasks/today")
    public String task(Principal principal, Model model)
    {
        Users users = userService.getCurrentUser(principal.getName());
        List<ToDo> list=toDoService.todaysTask(users);
        model.addAttribute("todos",list);
        boolean allCompleted = list.stream()
                .allMatch(l -> l.getStatus().equals("Completed"));

        if (allCompleted && !list.isEmpty()) {
            return "task-completion";
        }
        return "task-todays";
    }

    @GetMapping("/complete/{id}")
    public String complete(@PathVariable Long id)
    {
        ToDo toDo= toDoService.getToDo(id);
        toDoService.updateTodo(toDo);
        return "redirect:/tasks/today";
    }

    @GetMapping("/done/{id}")
    public String done(@PathVariable Long id)
    {
        ToDo toDo= toDoService.getToDo(id);
        toDoService.updateTodo(toDo);
        return "redirect:/todo";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id)
    {
        toDoService.deleteTodo(id);
        return "redirect:/tasks/today";
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable Long id)
    {
        toDoService.deleteTodo(id);
        return "redirect:/todo";
    }


    @GetMapping("/tasks/last7")
    public String displayLast7DaysRecord(Model model, Principal principal)
    {
        Users users = userService.getCurrentUser(principal.getName());
        List<ToDo> list = toDoService.seeLastSevenDaysData(users);
        System.out.println("Size of list is -----> "+ list.size());
        model.addAttribute("last7daystasks",list);
        return "last-7days-task";
    }

    @GetMapping("/allTasks")
    public String allTasks(@RequestParam(defaultValue = "0") int page, Model model, Principal principal)
    {
        Users users=userService.getCurrentUser(principal.getName());

        Pageable pageable = (Pageable) PageRequest.of(page,10);
        Page<ToDo> taskPage= toDoService.findAllTask(users, pageable);

        model.addAttribute("taskPage", taskPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", taskPage.getTotalPages());

        return "see-All-Tasks";
    }
}
