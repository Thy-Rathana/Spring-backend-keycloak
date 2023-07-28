package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .requestMatchers(request -> request.getRequestURI().startsWith("/api/v1/free")).permitAll()
//                .anyRequest().authenticated();
//        http.oauth2ResourceServer().jwt();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//
//        return http.build();
//    }

//        http.cors(cors -> cors.configurationSource(request -> {
//            CorsConfiguration corsConfiguration = new CorsConfiguration();
//            corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
//            corsConfiguration.setAllowedMethods(List.of("GET", "POST"));
//            corsConfiguration.setAllowedHeaders(List.of("*"));
//            return corsConfiguration;
//        }));




//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
//                .logout(logout -> logout.permitAll());

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers("/api/v1/free").hasRole("ADMIN")
                        .requestMatchers("/api/v1/free").permitAll()
                        .anyRequest().authenticated()
                );


        http.oauth2ResourceServer().jwt();
http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }




}
