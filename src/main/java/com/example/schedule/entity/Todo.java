package com.example.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Todo {

    private Long todoId;
    private String name;
    private String todo;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Todo(String name, String todo, String password) {
        this.name = name;
        this.todo = todo;
        this.password = password;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    public void update(String todo, String name) {
        this.todo = todo;
        this.name = name;
        this.updateAt = LocalDateTime.now();
    }
}