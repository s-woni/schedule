package com.example.schedule.repository;

import com.example.schedule.entity.Todo;
import com.example.schedule.dto.TodoResponseDto;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    TodoResponseDto createTodo(Todo todo);

    List<TodoResponseDto> findAllTodos();

    Optional<Todo> findTodoById(Long todoId);

    Optional<Todo> findTodoByIdAndPassword(Long todoId, String password);

    int updateTodo(Long todoId, String name, String todo);

    int deleteTodo(Long todoId);
}
