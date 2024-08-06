package com.example.helloWorld.entity;

import java.util.Set;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.NotFound;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(
			name = "subject_name",
			nullable = false,
			columnDefinition = "TEXT"
			)
	private String subjectName;
	@ManyToOne
	@JoinColumn(name="student_id")
	@JsonBackReference
	private Student student;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id != null && id.equals(subject.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
