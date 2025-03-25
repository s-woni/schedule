package com.example.schedule.service;

import com.example.schedule.dto.TodoRequestDto;
import com.example.schedule.dto.TodoResponseDto;

import java.util.List;

public interface TodoService {

    TodoResponseDto createTodo(TodoRequestDto dto);

    List<TodoResponseDto> getAllTodos();

    TodoResponseDto getTodoById(Long todoId);

    TodoResponseDto updateTodo(Long todoId, TodoRequestDto dto, String password);

    void deleteTodo(Long todoId, String password);
}
