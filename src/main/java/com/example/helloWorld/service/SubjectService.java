package com.example.helloWorld.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.helloWorld.entity.Subject;
import com.example.helloWorld.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class SubjectService {
	
	private final SubjectRepository subjectRepository;

    

    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
}
