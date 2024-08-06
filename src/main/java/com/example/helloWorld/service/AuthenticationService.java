package com.example.helloWorld.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.example.helloWorld.dto.AuthenticationRequest;
import com.example.helloWorld.dto.AuthenticationResponse;
import com.example.helloWorld.dto.RegisterRequest;
import com.example.helloWorld.entity.User;
import com.example.helloWorld.repository.UserRepository;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		log.debug("Registering user with email: {}", request.getEmail());
		var user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole())
				.build();
		userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		var RefreshToken = jwtService.generateRefreshToken(user);
		return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(RefreshToken).build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		log.debug("Authenticating user with email: {}", request.getEmail());
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (Exception e) {
			log.error("Authentication failed for user: {}", request.getEmail(), e);
			throw e;
		}
		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found"));
		var jwtToken = jwtService.generateToken(user);
		var RefreshToken = jwtService.generateRefreshToken(user);
		return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(RefreshToken).build();
	}

	public void refreshToken(HttpServletRequest request, HttpServletResponse response)
			throws IOException, StreamWriteException, DatabindException, java.io.IOException
			 {
		try {
			final String authHeader = request.getHeader("AUTHORIZATION");
			final String refreshToken;
			final String userEmail;
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				return;
			}
			refreshToken = authHeader.substring(7);
			userEmail = jwtService.extarctUsername(refreshToken);
			if (userEmail != null) {
				var user = this.userRepository.findByEmail(userEmail).orElseThrow();
				if (jwtService.isTokenValid(refreshToken, user)) {
					var accessToken = jwtService.generateToken(user);

					var authResponse = AuthenticationResponse.builder().accessToken(accessToken)
							.refreshToken(refreshToken).build();
					new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
				}
			}
		} catch (Exception e) {
			response.setHeader( "Error : ", e.getMessage());
			response.setStatus(403);
			Map<String, String> error = new HashMap<>();
			error.put("Error messsage :", e.getMessage());
			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
			new ObjectMapper().writeValue(response.getOutputStream(), error);
			
		}

	}
}
