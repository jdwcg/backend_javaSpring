package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // 1. 이 클래스가 데이터베이스 테이블과 매핑되는 엔티티임을 명시
@Table(name = "todos") // 2. 이 엔티티가 'todos'라는 테이블에 매핑됨을 지정
@Getter // 3. Lombok: 모든 필드에 대한 getter 메서드 자동 생성
@Setter // 4. Lombok: 모든 필드에 대한 setter 메서드 자동 생성
@NoArgsConstructor // 5. Lombok: 인자 없는 기본 생성자 자동 생성 (JPA 필수)
public class Todo {

    @Id // 6. 기본 키(Primary Key)임을 명시
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 7. ID 값 자동 생성 전략 (DB에 맡김)
    private Long id; // 할 일의 고유 ID

    @Column(nullable = false) // 8. 데이터베이스 컬럼, null 값을 허용하지 않음
    private String content; // 할 일 내용

    @Column(nullable = false)
    private Boolean completed; // 완료 여부 (true: 완료, false: 미완료)

    // 9. 할 일 생성 시 사용할 생성자 (id는 자동 생성되므로 제외)
    public Todo(String content, Boolean completed) {
        this.content = content;
        this.completed = completed;
    }
}