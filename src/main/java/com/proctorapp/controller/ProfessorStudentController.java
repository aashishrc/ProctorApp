package com.proctorapp.controller;

import com.proctorapp.model.Professor;
import com.proctorapp.model.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfessorStudentController {

    @PersistenceContext
    private EntityManager entityManager;

    @PutMapping("/professors/{professorId}/students/{studentId}")
    @Transactional
    public String addStudentToProfessorGroup(
            @PathVariable Long professorId,
            @PathVariable Long studentId) {

        Professor professor = entityManager.find(Professor.class, professorId);
        Student student = entityManager.find(Student.class, studentId);

        if (professor == null || student == null) {
            return "Error: Professor or student not found.";
        }

//        if (student.getProfessor() != null) {
//            return "Error: Student is already associated with a professor.";
//        }
//
//        professor.getStudents().add(student);
//        student.setProfessor(professor);

        entityManager.merge(professor);
        entityManager.merge(student);

        return "Student added to professor's group successfully.";
    }
    @DeleteMapping("/professors/{professorId}/students/{studentId}")
    @Transactional
    public String removeStudentFromProfessorGroup(
            @PathVariable Long professorId,
            @PathVariable Long studentId) {

        Professor professor = entityManager.find(Professor.class, professorId);
        Student student = entityManager.find(Student.class, studentId);

        if (professor == null || student == null) {
            return "Error: Professor or student not found.";
        }

//        if (!student.getProfessor().equals(professor)) {
//            return "Error: Student is not associated with this professor.";
//        }
//
//        professor.getStudents().remove(student);
//        student.setProfessor(null);

        entityManager.merge(professor);
        entityManager.merge(student);

        return "Student removed from professor's group successfully.";
    }
}

