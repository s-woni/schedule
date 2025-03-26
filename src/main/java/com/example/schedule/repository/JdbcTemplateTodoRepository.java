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

    // 할 일 생성
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

        // 생성된 할 일의 Id 가져옴
        Long generatedTodoId = keyHolder.getKey().longValue();

        // 생성된 할 일을 다시 조회하여 반환
        Todo savedTodo = findTodoById(generatedTodoId)
                .orElseThrow(() -> new RuntimeException("Todo creation failed"));

        return new TodoResponseDto(savedTodo);
    }

    // 할 일 전체 조회
    @Override
    public List<TodoResponseDto> findAllTodos() {
        return jdbcTemplate.query("SELECT * FROM todo", new TodoRowMapper())
                .stream()
                .map(TodoResponseDto::new)
                .collect(Collectors.toList());
    }

    // 할 일을 ID로 조회
    @Override
    public Optional<Todo> findTodoById(Long todoId) {
        List<Todo> result = jdbcTemplate.query("SELECT * FROM todo WHERE todo_id = ?", new TodoRowMapper(), todoId);
        return result.stream().findFirst();
    }

    // 할 일을 ID와 비밀번호로 조회
    @Override
    public Optional<Todo> findTodoByIdAndPassword(Long todoId, String password) {
        List<Todo> result = jdbcTemplate.query("SELECT * FROM todo WHERE todo_id = ? AND password = ?",
                new TodoRowMapper(), todoId, password);
        return result.stream().findFirst();
    }

    // 할 일 수정
    @Override
    public int updateTodo(Long todoId, String name, String todo, String password) {

        // 비밀번호 검증 후 수정
        Todo invalidPassword = findTodoByIdAndPassword(todoId, password).orElseThrow(() -> new RuntimeException("Invalid password"));

        return jdbcTemplate.update("UPDATE todo SET name = ?, todo = ?, update_at = CURRENT_TIMESTAMP WHERE todo_id = ? AND password = ?",
                name, todo, todoId, password);
    }

    // 할 일 삭제
    @Override
    public int deleteTodo(Long todoId, String password) {

        // 비밀번호 검증 후 삭제
        Todo invalidPassword = findTodoByIdAndPassword(todoId, password).orElseThrow(() -> new RuntimeException("Invalid password"));

        return jdbcTemplate.update("DELETE FROM todo WHERE todo_id = ?", todoId);
    }

    // ResultSet을 Todo 객체로 변환하는 클래스
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
