package com.example.schedule.controller;

import com.example.schedule.dto.TodoRequestDto;
import com.example.schedule.dto.TodoResponseDto;
import com.example.schedule.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto todoRequestDto) {
        // TodoResponseDto response = todoService.createTodo(todoRequestDto);
        return new ResponseEntity<>(todoService.createTodo(todoRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        // List<TodoResponseDto> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todoService.getAllTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable Long id) {
        // TodoResponseDto response = todoService.getTodoById(id);
        return new ResponseEntity<>(todoService.getTodoById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable Long id,
            @RequestBody TodoRequestDto dto) {

        // TodoResponseDto response = todoService.updateTodo(id, dto, dto.getPassword());
        return new ResponseEntity<>(todoService.updateTodo(id, dto, dto.getPassword()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id, @RequestBody TodoRequestDto dto) {

        todoService.deleteTodo(id, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}