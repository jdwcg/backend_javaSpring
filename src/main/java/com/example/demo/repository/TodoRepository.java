package com.example.demo.repository;

import com.example.demo.domain.Todo; // 우리가 만든 Todo 엔티티 import
import org.springframework.data.jpa.repository.JpaRepository; // JPA 리포지토리 import
import org.springframework.stereotype.Repository; // 이 클래스가 리포지토리임을 알리는 어노테이션

@Repository // 1. 이 인터페이스가 스프링에게 데이터 접근 계층(Repository)임을 알려줘요
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // 2. JpaRepository<엔티티 타입, 엔티티의 ID 타입>을 상속받으면 끝!
    // 이렇게만 해도 스프링 Data JPA가 알아서 CRUD(저장, 조회, 수정, 삭제) 메서드들을 만들어줘요!

    // 예시: findByCompleted()처럼 커스텀 쿼리 메서드를 정의할 수도 있지만, 지금은 여기까지만!
    // List<Todo> findByCompleted(Boolean completed);
}