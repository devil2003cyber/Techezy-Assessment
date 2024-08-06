package com.example.helloWorld.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.example.helloWorld.security.JwtAuthenticationFilter;
@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

		
	  	private final JwtAuthenticationFilter jwtAuthFilter;
	    private final AuthenticationProvider authenticationProvider;
	    @Autowired
	    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
	        this.jwtAuthFilter = jwtAuthFilter;
	        this.authenticationProvider = authenticationProvider;
	    }
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable()) // Disable CSRF protection
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/auth/register","/api/auth/authenticate","/api/students","/api/subjects").permitAll() 
	                .requestMatchers("/h2-console/**").permitAll() 
	                .requestMatchers(HttpMethod.GET, "/api/demo-student").hasAnyRole("STUDENT","ADMIN")
	                .requestMatchers(HttpMethod.GET, "/api/demo-admin").hasRole("ADMIN")
	                .anyRequest().authenticated()
	            )
	            .sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use  session management
	            )
	            .headers(headers->
	            		headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
	            .authenticationProvider(authenticationProvider) // Set the custom authentication provider
	            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add the custom JWT filter before the /password authentication filter

	        return http.build();
}
	    }
