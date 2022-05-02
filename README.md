# 스프링부트로 블로그 만들기 version 1

-LIBRARY 
Spring Boot DevTools
Lombok
Spring Web
Mustache
Maria DB Driver
Spring Data Jpa

- 포트 설정, 컨텍스트 패스 설정, 문자 인코딩 설정
```
server:
  port: 8080
  servlet:
    context-path: /
    encoding:
      charset: utf-8
```

- DB연결
```
spring:
  datasource:
      url: jdbc:mariadb://localhost:3306/greendb
      driver-class-name: org.mariadb.jdbc.Driver
      username: green
      password: green1234
```

