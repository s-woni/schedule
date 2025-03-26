package com.example.schedule.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Todo {

    private Long todoId; // ID
    private String name; // 작성자 이름
    private String todo; // 할일
    private String password; // 비밀번호
    private LocalDateTime createAt; // 생성 시간
    private LocalDateTime updateAt; // 수정 시간

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