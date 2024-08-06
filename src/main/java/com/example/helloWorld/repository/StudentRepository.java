package com.example.helloWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.helloWorld.entity.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

}
