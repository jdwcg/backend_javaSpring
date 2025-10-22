package com.example.demo.controller;

import com.example.demo.domain.Todo; // 우리가 만든 Todo 엔티티 import
import com.example.demo.service.TodoService; // TodoService import
import org.springframework.beans.factory.annotation.Autowired; // 의존성 주입
import org.springframework.http.HttpStatus; // HTTP 상태 코드
import org.springframework.http.ResponseEntity; // HTTP 응답 객체
import org.springframework.web.bind.annotation.*; // RestController, RequestMapping 등 어노테이션

import java.util.List;

@RestController // 1. 이 클래스가 RESTful 웹 서비스의 컨트롤러임을 알려줘요
@RequestMapping("/api/todos") // 2. 이 컨트롤러의 모든 API는 "/api/todos"로 시작해요
public class TodoController {

    private final TodoService todoService; // 3. TodoService를 사용하기 위한 선언

    @Autowired // 4. 스프링이 TodoService 객체를 자동으로 주입해줘요!
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // [C] CREATE: 새로운 할 일 생성 (POST /api/todos)
    @PostMapping // 5. POST 요청이 "/api/todos"로 오면 이 메서드 실행
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        // @RequestBody: HTTP 요청 본문(JSON 형태)을 Todo 객체로 자동 변환
        Todo createdTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED); // 201 Created 상태 코드 반환
    }

    // [R] READ: 모든 할 일 조회 (GET /api/todos)
    @GetMapping // 6. GET 요청이 "/api/todos"로 오면 이 메서드 실행
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.findAllTodos();
        return new ResponseEntity<>(todos, HttpStatus.OK); // 200 OK 상태 코드와 함께 모든 할 일 반환
    }

    // [R] READ: 특정 ID의 할 일 조회 (GET /api/todos/{id})
    @GetMapping("/{id}") // 7. GET 요청이 "/api/todos/1" 등으로 오면 이 메서드 실행
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        // @PathVariable: URL 경로의 {id} 값을 추출해서 Long id 변수에 할당
        return todoService.findTodoById(id)
                .map(todo -> new ResponseEntity<>(todo, HttpStatus.OK)) // 찾으면 200 OK 반환
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 없으면 404 Not Found 반환
    }

    // [U] UPDATE: 할 일 업데이트 (PUT /api/todos/{id})
    @PutMapping("/{id}") // 8. PUT 요청이 "/api/todos/1" 등으로 오면 이 메서드 실행
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        try {
            Todo updatedTodo = todoService.updateTodo(id, todo);
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK); // 업데이트 성공 시 200 OK 반환
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 찾을 수 없으면 404 Not Found 반환
        }
    }

    // [D] DELETE: 할 일 삭제 (DELETE /api/todos/{id})
    @DeleteMapping("/{id}") // 9. DELETE 요청이 "/api/todos/1" 등으로 오면 이 메서드 실행
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 삭제 성공 시 204 No Content 반환
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 삭제할 할 일을 찾을 수 없으면 404 Not Found 반환
        }
    }
}