package com.example.demo.config;

import org.springframework.context.annotation.Configuration; // 설정 클래스임을 알리는 어노테이션
import org.springframework.web.servlet.config.annotation.CorsRegistry; // CORS 설정을 위한 클래스
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // 웹 MVC 설정을 커스터마이징할 때 사용

@Configuration // 1. 스프링에게 이 클래스가 설정(Configuration) 클래스임을 알려줍니다.
public class WebConfig implements WebMvcConfigurer { // 2. 웹 MVC 설정을 확장하는 인터페이스를 구현합니다.

    @Override
    public void addCorsMappings(CorsRegistry registry) { // 3. CORS 설정을 추가하는 메서드를 오버라이드합니다.
        registry.addMapping("/**") // 4. 모든 경로(/**)에 대해 CORS를 허용합니다.
                .allowedOrigins("http://localhost:5173") // 5. 'http://localhost:5173' (프론트엔드 주소) 에서 오는 요청만 허용합니다.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 6. 허용할 HTTP 메서드를 지정합니다. (CRUD 모두 포함)
                .allowedHeaders("*") // 7. 모든 헤더를 허용합니다.
                .allowCredentials(true) // 8. 자격 증명(쿠키, 인증 헤더 등)을 허용합니다.
                .maxAge(3600); // 9. CORS pre-flight 요청의 결과를 3600초(1시간) 동안 캐싱합니다.
    }
}