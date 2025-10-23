# Admin Tool - Spring Boot 운영툴 템플릿

Spring Boot 3.2 기반의 운영툴 템플릿 프로젝트입니다. JWT 인증, 사용자 관리, REST API 등 기본적인 운영툴 기능을 포함하고 있습니다.

## 주요 기능

- **사용자 인증/인가**: JWT 기반 인증 시스템
- **사용자 관리**: CRUD API (생성, 조회, 수정, 삭제)
- **역할 기반 접근 제어**: ADMIN, MANAGER, USER 역할
- **API 문서화**: Swagger/OpenAPI UI
- **예외 처리**: 전역 예외 처리 및 표준 응답 포맷
- **데이터베이스**: JPA/Hibernate를 통한 데이터 접근
- **로깅**: Logback을 통한 로그 관리
- **Health Check**: Spring Boot Actuator

## 기술 스택

- **Java**: 17
- **Spring Boot**: 3.2.0
- **Spring Security**: JWT 기반 인증
- **Spring Data JPA**: 데이터 접근
- **H2 Database**: 개발용 인메모리 데이터베이스
- **Gradle**: 빌드 도구
- **Swagger/OpenAPI**: API 문서화
- **Lombok**: 보일러플레이트 코드 감소

## 프로젝트 구조

```
src/
├── main/
│   ├── java/com/admin/tool/
│   │   ├── config/          # 설정 클래스
│   │   │   ├── DataInitializer.java
│   │   │   ├── OpenApiConfig.java
│   │   │   └── SecurityConfig.java
│   │   ├── controller/      # REST 컨트롤러
│   │   │   ├── AuthController.java
│   │   │   └── UserController.java
│   │   ├── dto/            # 데이터 전송 객체
│   │   │   ├── ApiResponse.java
│   │   │   ├── PageResponse.java
│   │   │   ├── LoginRequest.java
│   │   │   ├── JwtResponse.java
│   │   │   ├── UserRequest.java
│   │   │   └── UserResponse.java
│   │   ├── entity/         # JPA 엔티티
│   │   │   └── User.java
│   │   ├── exception/      # 예외 처리
│   │   │   ├── CustomException.java
│   │   │   ├── ResourceNotFoundException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── repository/     # JPA 리포지토리
│   │   │   └── UserRepository.java
│   │   ├── security/       # 보안 관련
│   │   │   ├── JwtTokenProvider.java
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   └── CustomUserDetailsService.java
│   │   ├── service/        # 비즈니스 로직
│   │   │   ├── AuthService.java
│   │   │   └── UserService.java
│   │   └── AdminToolApplication.java
│   └── resources/
│       ├── application.yml
│       └── logback-spring.xml
└── test/
    └── java/com/admin/tool/
```

## 시작하기

### 사전 요구사항

- JDK 17 이상
- Gradle 8.x 이상

### 빌드 및 실행

#### 1. 프로젝트 클론 후 빌드

```bash
./gradlew clean build
```

#### 2. 애플리케이션 실행

```bash
./gradlew bootRun
```

또는 JAR 파일로 실행:

```bash
java -jar build/libs/admin-tool-1.0.0.jar
```

#### 3. 애플리케이션 접속

- **애플리케이션**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **H2 Console**: http://localhost:8080/api/h2-console
  - JDBC URL: `jdbc:h2:mem:admindb`
  - Username: `sa`
  - Password: (비워두기)

## 기본 계정

애플리케이션 시작 시 자동으로 생성되는 기본 계정:

| Username | Password    | Role    |
|----------|-------------|---------|
| admin    | admin123    | ADMIN   |
| manager  | manager123  | MANAGER |
| user     | user123     | USER    |

## API 사용 예제

### 1. 로그인

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

응답:
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "username": "admin",
    "role": "ROLE_ADMIN"
  },
  "timestamp": "2024-10-23T14:30:00"
}
```

### 2. 사용자 목록 조회 (인증 필요)

```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer {accessToken}"
```

### 3. 사용자 생성 (ADMIN 권한 필요)

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Authorization: Bearer {accessToken}" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "password": "password123",
    "email": "newuser@example.com",
    "name": "New User",
    "role": "USER",
    "enabled": true
  }'
```

### 4. 사용자 수정 (ADMIN 권한 필요)

```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer {accessToken}" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "updateduser",
    "password": "newpassword123",
    "email": "updated@example.com",
    "name": "Updated User",
    "role": "MANAGER",
    "enabled": true
  }'
```

### 5. 사용자 삭제 (ADMIN 권한 필요)

```bash
curl -X DELETE http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer {accessToken}"
```

## 설정

### application.yml 주요 설정

```yaml
# 서버 포트
server.port: 8080

# 데이터베이스 (운영 환경에서는 MySQL/PostgreSQL 사용)
spring.datasource.url: jdbc:h2:mem:admindb

# JWT 설정
jwt:
  secret: your-secret-key-here  # 운영 환경에서는 반드시 변경!
  expiration: 86400000  # 24시간
```

### 운영 환경 설정

운영 환경에서는 다음 설정을 변경해야 합니다:

1. **JWT Secret Key**: 강력한 시크릿 키로 변경
2. **데이터베이스**: MySQL 또는 PostgreSQL 사용
3. **로그 레벨**: INFO 또는 WARN으로 설정
4. **H2 Console**: 비활성화

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/admindb
    username: your-username
    password: your-password
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: validate  # 운영에서는 validate 또는 none 사용

  h2:
    console:
      enabled: false  # 운영에서는 비활성화
```

## 테스트

```bash
./gradlew test
```

## 라이선스

MIT License

## 기여

프로젝트 개선을 위한 기여를 환영합니다!

## 문의

문의사항이 있으시면 이슈를 등록해주세요.
