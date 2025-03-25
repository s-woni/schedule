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
        return todos;
    }

    @Override
    public TodoResponseDto getTodoById(Long id) {

        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id " + id));

        return new TodoResponseDto(todo);
    }

    @Override
    public TodoResponseDto updateTodo(Long id, TodoRequestDto dto, String password) {

        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id " + id));

        if (!todo.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        todo.update(dto.getTodo(), dto.getName());

        todoRepository.updateTodo(id, todo.getName(), todo.getTodo(), password);

        return new TodoResponseDto(todo);
    }

    @Override
    public void deleteTodo(Long id, String password) {

        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id " + id));

        if (!todo.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        todoRepository.deleteTodo(id, password);
    }
}
