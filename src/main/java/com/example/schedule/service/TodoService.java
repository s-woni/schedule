package com.example.schedule.service;

import com.example.schedule.dto.TodoRequestDto;
import com.example.schedule.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {

    // 할 일 생성
    TodoResponseDto createTodo(TodoRequestDto dto);

    // 할 일 전체 조회
    List<TodoResponseDto> getAllTodos();

    // 할 일 단일 조회
    TodoResponseDto getTodoById(Long todoId);

    // 할 일 수정
    TodoResponseDto updateTodo(Long todoId, TodoRequestDto dto, String password);

    // 할 일 삭제
    void deleteTodo(Long todoId, String password);
}
