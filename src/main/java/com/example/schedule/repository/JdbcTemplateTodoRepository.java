package com.example.schedule.repository;

import com.example.schedule.dto.TodoResponseDto;
import com.example.schedule.entity.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JdbcTemplateTodoRepository implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TodoResponseDto createTodo(Todo todo) {
        String sql = "INSERT INTO todo (name, todo, password) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "todo_id" });
            ps.setString(1, todo.getName());
            ps.setString(2, todo.getTodo());
            ps.setString(3, todo.getPassword());
            return ps;
        }, keyHolder);

        Long generatedTodoId = keyHolder.getKey().longValue();

        Todo savedTodo = findTodoById(generatedTodoId)
                .orElseThrow(() -> new RuntimeException("Todo creation failed"));

        return new TodoResponseDto(savedTodo);
    }

    @Override
    public List<TodoResponseDto> findAllTodos() {
        return jdbcTemplate.query("SELECT * FROM todo", new TodoRowMapper())
                .stream()
                .map(TodoResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Todo> findTodoById(Long todoId) {
        List<Todo> todos = jdbcTemplate.query("SELECT * FROM todo WHERE todo_id = ?", new TodoRowMapper(), todoId);
        return todos.stream().findFirst();
    }

    @Override
    public Optional<Todo> findTodoByIdAndPassword(Long todoId, String password) {
        List<Todo> todos = jdbcTemplate.query("SELECT * FROM todo WHERE todo_id = ? AND password = ?",
                new TodoRowMapper(), todoId, password);
        return todos.stream().findFirst();
    }

    @Override
    public int updateTodo(Long todoId, String name, String todo, String password) {

        Todo invalidPassword = findTodoByIdAndPassword(todoId, password).orElseThrow(() -> new RuntimeException("Invalid password"));

        return jdbcTemplate.update("UPDATE todo SET name = ?, todo = ?, update_at = CURRENT_TIMESTAMP WHERE todo_id = ? AND password = ?",
                name, todo, todoId, password);
    }

    @Override
    public int deleteTodo(Long todoId, String password) {

        Todo invalidPassword = findTodoByIdAndPassword(todoId, password).orElseThrow(() -> new RuntimeException("Invalid password"));

        return jdbcTemplate.update("DELETE FROM todo WHERE todo_id = ?", todoId);
    }

    private static class TodoRowMapper implements RowMapper<Todo> {
        @Override
        public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Todo todo = new Todo(
                    rs.getString("name"),
                    rs.getString("todo"),
                    rs.getString("password")
            );
            todo.setTodoId(rs.getLong("todo_id"));
            todo.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());
            todo.setUpdateAt(rs.getTimestamp("update_at").toLocalDateTime());
            return todo;
        }
    }
}
