package com.example.schedule.dto;

import com.example.schedule.entity.Todo;
import lombok.Getter;

@Getter
public class TodoResponseDto {

    private Long todoId; // ID
    private String name; // 이름
    private String todo; // 할일
    private String password; // 비밀번호
    private String createAt; // 생성 시간
    private String updateAt; // 수정 시간

    public TodoResponseDto(Todo todo) {
        this.todoId = todo.getTodoId();
        this.name = todo.getName();
        this.todo = todo.getTodo();
        this.password = todo.getPassword();
        this.createAt = todo.getCreateAt().toString();
        this.updateAt = todo.getUpdateAt().toString();
    }
}