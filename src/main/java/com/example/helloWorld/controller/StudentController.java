package com.example.helloWorld.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.helloWorld.dto.StudentRegisterRequest;
import com.example.helloWorld.entity.Student;
import com.example.helloWorld.entity.Subject;
import com.example.helloWorld.repository.StudentRepository;
import com.example.helloWorld.service.StudentService;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {
	
	
	private final StudentService studentService;
	
	@PostMapping
//	public ResponseEntity<Student> register(
//			@RequestBody StudentRegisterRequest request
//			){
//	
//		log.debug("Authenticating user with email: {}", request.getFirstName());
//		Student student = new Student();
//        student.setFirstName(request.getFirstName());
//        student.setLastName(request.getLastName());
//        student.setAddress(request.getAddress());
//        student.setRole(request.getRole());
//        Set<Subject> subjects = new HashSet<>();
//        for (String subjectName : request.getSubjectNames()) {
//            Subject subject = new Subject();
//            subject.setSubjectName(subjectName);
//            subject.setStudent(student);
//            subjects.add(subject);
//        }
//        student.setSubjects(subjects);
//
//        return ResponseEntity.ok(studentService.saveStudent(student));
//	}
	public ResponseEntity<Student> register(@RequestBody StudentRegisterRequest request) {
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setAddress(request.getAddress());
        student.setRole(request.getRole());

        Set<Subject> subjects = new HashSet<>();
        for (String subjectName : request.getSubjectNames()) {
            Subject subject = new Subject();
            subject.setSubjectName(subjectName);
            subject.setStudent(student); // Set the student reference
            subjects.add(subject);
        }
        student.setSubjects(subjects);

        return ResponseEntity.ok(studentService.saveStudent(student));
    }
	
	@GetMapping
	public ResponseEntity<List<Student>> getAllStudent()
	{
		return ResponseEntity.ok(studentService.getAllStudents());
	}
}
