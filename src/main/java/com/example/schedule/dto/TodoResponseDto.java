package com.example.schedule.dto;

import com.example.schedule.entity.Todo;
import lombok.Getter;

@Getter
public class TodoResponseDto {

    private Long todoId;
    private String name;
    private String todo;
    private String password;
    private String createAt;
    private String updateAt;

    public TodoResponseDto(Todo todo) {
        this.todoId = todo.getTodoId();
        this.name = todo.getName();
        this.todo = todo.getTodo();
        this.password = todo.getPassword();
        this.createAt = todo.getCreateAt().toString();
        this.updateAt = todo.getUpdateAt().toString();
    }
}