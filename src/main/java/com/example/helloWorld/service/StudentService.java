package com.example.helloWorld.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.helloWorld.entity.Student;
import com.example.helloWorld.entity.Subject;
import com.example.helloWorld.repository.StudentRepository;
import com.example.helloWorld.repository.SubjectRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class StudentService {
	
	private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

   

//    public Student saveStudent(Student student) {
//        
//    	 Set<Subject> subjects = student.getSubjects();
//         if (subjects != null && !subjects.isEmpty()) {
//             subjects.forEach(subject -> {
//                 subject.setStudent(student);
//                 subjectRepository.save(subject);
//             });
//         }
//         return studentRepository.save(student);
//    }
    @Transactional
    public Student saveStudent(Student student) {
        // Save the student first
        Student savedStudent = studentRepository.save(student);

        // Save the subjects
        Set<Subject> subjects = student.getSubjects();
        if (subjects != null && !subjects.isEmpty()) {
            subjects.forEach(subject -> {
                subject.setStudent(savedStudent);
                subjectRepository.save(subject);
            });
        }
        
        // Fetch the student again to ensure subjects are associated correctly
        return studentRepository.findById(savedStudent.getId()).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
}
