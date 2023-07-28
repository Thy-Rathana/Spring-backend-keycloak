package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

// implementation keycloak with spring boot backend api

// Path: build.gradle
// Compare this snippet from build.gradle:
// plugins {
//     id 'org.springframework.boot' version '2.5.5'
//     id 'io.spring.dependency-management' version '1.0.11.RELEASE'
//     id 'java'
// }
//
// group = 'com.example'
// version = '0.0.1-SNAPSHOT'
// sourceCompatibility = '11'
//
// repositories {
//     mavenCentral()
// }
//
// dependencies {
//     implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
//     implementation 'org.springframework.boot:spring-boot-starter-security'
//     implementation 'org.springframework.boot:spring-boot-starter-web'
//     runtimeOnly 'org.postgresql:postgresql'
//     testImplementation 'org.springframework.boot:spring-boot-starter-test'
//     implementation 'org.springframework.boot:spring-boot-starter-validation'
//     implementation 'org.keycloak:keycloak-spring-boot-starter'
// }
//
// test {
//     useJUnitPlatform()
// }
// Compare this snippet from src\main\resources\application.properties:
// spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
// spring.datasource.username=postgres
// spring.datasource.password=postgres
// spring.datasource.driver-class-name=org.postgresql.Driver
// spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
// spring.jpa.hibernate.ddl-auto=update
// spring.jpa.show-sql=true
// spring.jpa.properties.hibernate.format_sql=true
// spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
// spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults = false

// keycloak configuration
// keycloak.realm = demo
// keycloak.auth-server-url = http://localhost:8080/auth
// keycloak.ssl-required = external
// keycloak.resource = spring-boot-backend
// keycloak.public-client = true
// keycloak.securityConstraints[0].authRoles[0] = user
// keycloak.securityConstraints[0].securityCollections[0].patterns[0] = /api/v1/users
// keycloak.securityConstraints[0].securityCollections[0].patterns[1] = /api/v1/users/*
// keycloak.securityConstraints[0].securityCollections[0].patterns[2] = /api/v1/users/{id}
// keycloak.securityConstraints[0].securityCollections[0].patterns[3] = /api/v1/users/{id}/*
// keycloak.securityConstraints[0].securityCollections[0].patterns[4] = /api/v1/users/{id}/
// keycloak.securityConstraints[0].securityCollections[0].patterns[5] = /api/v1/users/{id}/update
// keycloak.securityConstraints[0].securityCollections[0].patterns[6] = /api/v1/users/{id}/delete
// keycloak.securityConstraints[0].securityCollections[0].patterns[7] = /api/v1/users/{id}/update/*
// keycloak.securityConstraints[0].securityCollections[0].patterns[8] = /api/v1/users/{id}/delete/*
// keycloak.securityConstraints[0].securityCollections[0].patterns[9] = /api/v1/users/{id}/update/
// keycloak.securityConstraints[0].securityCollections[0].patterns[10] = /api/v1/users/{id}/delete/
// keycloak.securityConstraints[0].securityCollections[0].patterns[11] = /api/v1/users/{id}/update/{id}

// Path: src\main\java\com\example\demo\config\SecurityConfig.java
// Compare this snippet from src\main\java\com\example\demo\config\SecurityConfig.java:
// package com.example.demo.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
// @EnableWebSecurity
// public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .authorizeRequests()
//                 .antMatchers("/api/v1/users").hasRole("user")
//                 .antMatchers("/api/v1/users/*").hasRole("user")
//                 .antMatchers("/api/v1/users/{id}").hasRole("user")
//                 .antMatchers("/api/v1/users/{id}/*").hasRole("user")
//                 .antMatchers("/api/v1/users/{id}/").hasRole("user")
//                 .antMatchers("/api/v1/users/{id}/update").hasRole("user")
//                 .antMatchers("/api/v1/users/{id}/delete").hasRole("user")
//                 .antMatchers("/api/v1/users/{id}/update/*").hasRole("user")
//                 .antMatchers("/api/v1/users/{id}/delete/*").hasRole("user")
//                 .antMatchers("/api/v1/users/{id}/update/").hasRole("user")
//                 .antMatchers("/api/v1/users/{id}/delete/").hasRole("user")
//                 .antMatchers("/api/v1/users/{id}/update/{id}").hasRole("user")
//                 .anyRequest().permitAll();
//         return http.formLogin().disable();
//     }
// }
// Compare this snippet from src\main\java\com\example\demo\controller\UserController.java:
// package com.example.demo.controller;
//
// import com.example.demo.bean.BeanValidator;
// import com.example.demo.bean.ResultDTO;
// import com.example.demo.model.User;
// import com.example.demo.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
//
// import java.util.ArrayList;
//
// @CrossOrigin(origins = "http://localhost:3000" , maxAge = 3600)
// @RestController
// @RequestMapping("/api/v1")
// public class UserController {
//     @Autowired
//     private UserService userService;
//     @Autowired
//     private BeanValidator beanValidator;
//     @GetMapping("/users")
//     public ResponseEntity<?> allUsers() {
//         System.err.println(":::  UserController.allUsers :::");
//         ResultDTO<?> responsePacket = null;
//         try {
//             responsePacket = new ResultDTO<>(userService.getAllUsers(), "Users fetched successfully !!", true);
//             return new ResponseEntity<>(responsePacket, HttpStatus.OK);
//         } catch (Exception e) {
//             responsePacket = new ResultDTO<>(e.getMessage(), false);
//             return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
//         }
//     }
//
// .....
//
// }
// Compare this snippet from src\main\java\com\example\demo\service\UserService.java:
// package com.example.demo.service;
//
// import com.example.demo.model.User;
// import com.example.demo.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
//
// @Service
// public class UserService {
//     @Autowired
//     private UserRepository repo;
//     public User saveUser(User user) {
//         return repo.save(user);
//     }
//
//     public List<User> getAllUsers() {
//         return repo.findAll();
//     }
//
//     public User isDataExist(User reqData) {
//         return repo.findByEmailAndMobNo(reqData.getEmail(), reqData.getMobNo());
//     }
//
//     .....
//
// }
// Compare this snippet from src\main\java\com\example\demo\repository\UserRepository.java:
// package com.example.demo.repository;
//
// import com.example.demo.model.User;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
//
// @Repository
// public interface UserRepository extends JpaRepository<User, String> {
//     User findByEmailAndMobNo(String email, String mobNo);
// }
// Compare this snippet from src\main\java\com\example\demo\model\User.java:
// package com.example.demo.model;
//
// import org.hibernate.annotations.GenericGenerator;
//
// import javax.persistence.*;
// import javax.validation.constraints.Email;
// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.NotNull;
// import javax.validation.constraints.Pattern;
// import java.io.Serializable;
//
// @Entity
// @Table(name = "users")
// public class User implements Serializable {
//     @Id
//     @GeneratedValue(generator = "uuid")
//     @GenericGenerator(name = "uuid", strategy = "uuid2")
//     @Column(name = "id", updatable = false, nullable = false)
//     private String id;
//     @NotBlank(message = "Name must not be empty")
//     @Column(name = "name")
//     private String name;
//     @NotBlank(message = "Email must not be empty")
//     @Email(message = "Email should be valid")
//     @NotBlank(message = "Password must not be empty")
//     @Column(name = "password")
//     private String password;
//     @NotBlank(message = "Email must not be empty")
//     @Email(message = "Email should be valid")
//     @Column(name = "email")
//     private String email;
//     @NotBlank(message = "Mobile number must not be empty")
//     @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be valid")
//     @Column(name = "mob_no")
//     private String mobNo;
//
//     .....
//
// }
// Compare this snippet from src\main\java\com\example\demo\bean\ResultDTO.java:
// package com.example.demo.bean;
//
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;
//
// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// public class ResultDTO<T> {
//     private T data;
//     private String message;
//     private boolean status;
//
//     public ResultDTO(T data, boolean status) {
//         this.data = data;
//         this.status = status;
//     }
//
//     public ResultDTO(String message, boolean status) {
//         this.message = message;
//         this.status = status;
//     }
// }
// Compare this snippet from src\main\java\com\example\demo\bean\BeanValidator.java:
// package com.example.demo.bean;
//
// import java.util.ArrayList;
// import java.util.Set;
//
// import com.example.demo.model.User;
// import jakarta.validation.ConstraintViolation;
// import jakarta.validation.Validation;
// import jakarta.validation.Validator;
// import org.springframework.stereotype.Component;
//
// @Component
// public class BeanValidator {
//
//     public Validator getValidator() {
//         return Validation.buildDefaultValidatorFactory().getValidator();
//     }
//
//     .....
//
// }
// Compare this snippet from src\main\java\com\example\demo\controller\UserController.java:
// package com.example.demo.controller;
//
// import com.example.demo.bean.BeanValidator;
// import com.example.demo.bean.ResultDTO;
// import com.example.demo.model.User;
// import com.example.demo.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
//
// import java.util.ArrayList;
//
// @CrossOrigin(origins = "http://localhost:3000" , maxAge = 3600)
// @RestController
// @RequestMapping("/api/v1")
// public class UserController {
//     @Autowired
//     private UserService userService;
//     @Autowired
//     private BeanValidator beanValidator;
//     @GetMapping("/users")
//     public ResponseEntity<?> allUsers() {
//         System.err.println(":::  UserController.allUsers :::");
//         ResultDTO<?> responsePacket = null;
//         try {
//             responsePacket = new ResultDTO<>(userService.getAllUsers(), "Users fetched successfully !!", true);
//             return new ResponseEntity<>(responsePacket, HttpStatus.OK);
//         } catch (Exception e) {
//             responsePacket = new ResultDTO<>(e.getMessage(), false);
//             return new ResponseEntity<>(responsePacket, HttpStatus.BAD_REQUEST);
//         }
//     }
//
// .....
//
// }








