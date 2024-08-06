package com.example.helloWorld.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class StudentRegisterRequest {
	private Long id;
	private String firstName;
	private String lastName;
    private String address;
    private String role;
    private Set<String> subjectNames = new HashSet<>();
}
