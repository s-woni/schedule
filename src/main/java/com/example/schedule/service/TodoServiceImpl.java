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

    @Override
    public TodoResponseDto createTodo(TodoRequestDto dto) {
        Todo todo = new Todo(
                dto.getName(),
                dto.getTodo(),
                dto.getPassword()
        );

        return todoRepository.createTodo(todo);
    }

    @Override
    public List<TodoResponseDto> getAllTodos() {

        List<TodoResponseDto> todos = todoRepository.findAllTodos();
        return todos; // 반환형을 List<TodoResponseDto>로 그대로 두기
    }

    @Override
    public TodoResponseDto getTodoById(Long id) {

        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id " + id));

        return new TodoResponseDto(todo);
    }

    @Override
    public TodoResponseDto updateTodo(Long id, TodoRequestDto dto) {

        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id " + id));

        todo.update(dto.getTodo(), dto.getName());

        return todoRepository.createTodo(todo);
    }

    @Override
    public void deleteTodo(Long id) {

        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id " + id));

        todoRepository.deleteTodo(id);
    }
}
