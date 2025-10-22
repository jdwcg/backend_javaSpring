package com.example.demo.service;

import com.example.demo.domain.Todo; // Todo 엔티티 import
import com.example.demo.repository.TodoRepository; // TodoRepository import
import org.springframework.beans.factory.annotation.Autowired; // 의존성 주입을 위한 어노테이션
import org.springframework.stereotype.Service; // 이 클래스가 서비스임을 알리는 어노테이션
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 처리를 위한 어노테이션

import java.util.List;
import java.util.Optional; // Optional 클래스 import

@Service // 1. 이 클래스가 스프링에게 비즈니스 로직을 담당하는 서비스임을 알려줘요
@Transactional(readOnly = true) // 2. 모든 메서드에 읽기 전용 트랜잭션을 적용 (변경하는 메서드는 따로 설정)
public class TodoService {

    private final TodoRepository todoRepository; // 3. TodoRepository를 사용하기 위한 선언

    @Autowired // 4. 스프링이 TodoRepository 객체를 자동으로 주입해줘요! (의존성 주입)
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // CREATE (새로운 할 일 생성)
    @Transactional // 5. 이 메서드는 데이터 변경이 일어나므로 트랜잭션을 읽기/쓰기 모드로 적용
    public Todo createTodo(Todo todo) {
        // 단! 여기에 '내용이 너무 길면 안 돼!' 같은 비즈니스 로직을 추가할 수 있어요.
        if (todo.getContent() == null || todo.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("할 일 내용은 비워둘 수 없습니다! 😥");
        }
        return todoRepository.save(todo); // Repository를 이용해 DB에 저장
    }

    // READ (모든 할 일 조회)
    public List<Todo> findAllTodos() {
        return todoRepository.findAll(); // Repository를 이용해 모든 할 일 조회
    }

    // READ (특정 ID의 할 일 조회)
    public Optional<Todo> findTodoById(Long id) {
        return todoRepository.findById(id); // Repository를 이용해 ID로 할 일 조회
    }

    // UPDATE (할 일 업데이트)
    @Transactional // 6. 이 메서드는 데이터 변경이 일어나므로 트랜잭션을 읽기/쓰기 모드로 적용
    public Todo updateTodo(Long id, Todo updatedTodo) {
        // 먼저 기존 할 일이 있는지 찾아봐요
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 할 일을 찾을 수 없습니다: " + id + " 🥺"));

        // 할 일 내용을 업데이트 (null이 아니면)
        if (updatedTodo.getContent() != null && !updatedTodo.getContent().trim().isEmpty()) {
            todo.setContent(updatedTodo.getContent());
        }
        // 완료 여부를 업데이트
        if (updatedTodo.getCompleted() != null) { // completed 필드가 명시적으로 주어졌을 때만 업데이트
            todo.setCompleted(updatedTodo.getCompleted());
        }

        return todoRepository.save(todo); // 변경된 할 일 저장 (ID가 있으면 update, 없으면 insert)
    }

    // DELETE (할 일 삭제)
    @Transactional // 7. 이 메서드는 데이터 변경이 일어나므로 트랜잭션을 읽기/쓰기 모드로 적용
    public void deleteTodo(Long id) {
        // 삭제하려는 할 일이 존재하는지 먼저 확인
        if (!todoRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제하려는 할 일을 찾을 수 없습니다: " + id + " 😥");
        }
        todoRepository.deleteById(id); // Repository를 이용해 ID로 할 일 삭제
    }
}

