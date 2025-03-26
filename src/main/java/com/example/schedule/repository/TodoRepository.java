package com.example.schedule.repository;

import com.example.schedule.entity.Todo;
import com.example.schedule.dto.TodoResponseDto;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {

    // 할 일 생성
    TodoResponseDto createTodo(Todo todo);

    // 할 일 전체 조회
    List<TodoResponseDto> findAllTodos();

    // 할 일을 ID로 조회
    Optional<Todo> findTodoById(Long todoId);

    // 할 일을 ID와 비밀번호로 조회
    Optional<Todo> findTodoByIdAndPassword(Long todoId, String password);

    // 할 일 수정
    int updateTodo(Long todoId, String name, String todo, String password);

    // 할 일 삭제
    int deleteTodo(Long todoId, String password);
}
