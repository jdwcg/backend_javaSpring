# Stage 1: 애플리케이션 빌드
FROM eclipse-temurin:21-jdk-jammy AS builder

# 작업 디렉토리를 /app으로 설정
WORKDIR /app

# Maven wrapper 파일 복사 (Docker 레이어 캐싱 활용을 위해 먼저 복사)
COPY mvnw .
COPY .mvn .mvn

# pom.xml 복사 및 의존성 다운로드
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B # <--- 여기를 수정했습니다!

# 나머지 소스 코드 복사
COPY src ./src

# Spring Boot 애플리케이션 빌드
# 테스트 스킵(-DskipTests)하여 빌드 시간 단축
RUN ./mvnw clean package -DskipTests # <--- 여기도 수정했습니다!

# Stage 2: 최종 실행 이미지 생성
FROM eclipse-temurin:21-jre-jammy

# 작업 디렉토리를 /app으로 설정
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일 복사
COPY --from=builder /app/target/*.jar app.jar

# Spring Boot 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]

# 애플리케이션이 외부로 노출할 포트 (기본 Spring Boot 포트 8080)
EXPOSE 8080