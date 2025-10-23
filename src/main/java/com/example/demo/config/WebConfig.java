package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 🚨 여기에 Vercel 배포 URL을 추가합니다!
                // 로컬 개발용(http://localhost:5173)과 Vercel용(https://javatest-tawny.vercel.app)을 모두 넣어줍니다.
                .allowedOrigins(
                        "http://localhost:5173", // 로컬 프론트엔드 개발 시 필요
                        "https://javatest-tawny.vercel.app" // Vercel에 배포된 프론트엔드 URL
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}