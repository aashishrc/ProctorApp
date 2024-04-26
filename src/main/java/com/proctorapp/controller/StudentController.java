package com.proctorapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proctorapp.dao.StudentDao;
import com.proctorapp.model.Student;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    @GetMapping("/studentWithoutProfessor")
    @ResponseBody
    public String getStudentsWithoutProfessor(@RequestParam(name = "professorId", required = false) Long professorId, Model model) throws JsonProcessingException {
        List<Student> students;
        students = studentDao.getStudentsWithoutProfessor();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(students);
//        String studentsJson = objectMapper.writeValueAsString(students);
//        model.addAttribute(studentsJson);
//        return "addStudentForm";
    }

    @PostMapping("/associateStudents")
    @ResponseBody
    public String associateStudents(@RequestBody List<Long> selectedStudentIds, @RequestParam("professorId") Long professorId) {
        try {
            // Associate selected students with the professor
            studentDao.setProfessor(selectedStudentIds, professorId);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/unassociateStudent")
    public String unassociateStudent(@RequestParam("professorId") Long professorId,@RequestParam("studentId") Long studentId) {
        Student student = studentDao.getStudentById(studentId);
        if (student != null) {
            student.setProfessor(null); // Set professorId to null to remove association
            studentDao.updateStudent(student); // Update the student in the database
            return "/students?professorId=" + professorId;
        } else {
            return "Student not found with ID: " + studentId;
        }
    }

}
