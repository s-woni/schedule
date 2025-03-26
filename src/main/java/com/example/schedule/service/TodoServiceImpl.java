package com.example.schedule.service;

import com.example.schedule.dto.TodoRequestDto;
import com.example.schedule.dto.TodoResponseDto;
import com.example.schedule.entity.Todo;
import com.example.schedule.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // 할 일 생성
    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {
        Todo todo = new Todo(
                dto.getName(),
                dto.getTodo(),
                dto.getPassword()
        );

        return todoRepository.createTodo(todo);
    }

    // 할일 전체 조회
    @Override
    public List<TodoResponseDto> getAllTodos() {

        List<TodoResponseDto> todos = todoRepository.findAllTodos();
        return todos;
    }

    // 할 일 단일 조회
    @Override
    public TodoResponseDto getTodoById(Long id) {

        // 할 일을 찾을 수 없으면 예외 발생
        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id " + id));

        return new TodoResponseDto(todo);
    }

    // 할 일 수정
    @Override
    public TodoResponseDto updateTodo(Long id, TodoRequestDto dto, String password) {

        // 할 일을 찾을 수 없으면 예외 발생
        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id " + id));

        // 비밀번호와 일치하지 않을 시 예외 발생
        if (!todo.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        todo.update(dto.getTodo(), dto.getName());

        todoRepository.updateTodo(id, todo.getName(), todo.getTodo(), password);

        return new TodoResponseDto(todo);
    }

    // 할 일 삭제
    @Override
    public void deleteTodo(Long id, String password) {
        
        // 할 일을 찾을 수 없으면 예외 발생
        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id " + id));

        // 비밀번호와 일치하지 않을 시 예외 발생
        if (!todo.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        todoRepository.deleteTodo(id, password);
    }
}
