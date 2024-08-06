package com.example.helloWorld.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.helloWorld.entity.Subject;
import com.example.helloWorld.repository.StudentRepository;
import com.example.helloWorld.repository.SubjectRepository;
import com.example.helloWorld.service.SubjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subjects")
public class SubjectController {
	
	private final SubjectService subjectService;
	
	@PostMapping
	public ResponseEntity<Subject> addSubject(
			@RequestBody Subject request){
		return ResponseEntity.ok(subjectService.saveSubject(request));
	}
	
	@GetMapping
	public ResponseEntity<List<Subject>> getSubject(){
		return ResponseEntity.ok(subjectService.getAllSubjects());
	}
}
