package com.proctorapp.controller;
import com.proctorapp.dao.StudentDao;
import org.springframework.ui.Model;
import com.proctorapp.dao.ProfessorDao;
import com.proctorapp.model.Professor;
import com.proctorapp.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorDao professorDao;

    @Autowired
    private StudentDao studentDao;

//    @PostMapping("/students")
//    public String showStudents(@RequestParam(name = "professorId", required = false) Long professorId, HttpServletRequest request){
//        System.out.println("I reached /students and :" + professorId);
//        if(professorId == null)
//            return "error";
//        Professor professor = professorDao.getProfessorById(professorId);
//        if (professor != null) {
//            // Add professor object to request scope
//            System.out.println("I'm inside: "+ professorId);
//
//            request.setAttribute("professor", professor);
//            // Return the view name "students"
//            return "students";
//        } else {
//            // Handle case where professor is not found (e.g., redirect to error page)
//            return "error";  // Replace with your appropriate error view name
//        }
//    }

    @PostMapping("/students")
    public String showStudents(@RequestParam(name = "professorId", required = false) Long professorId, Model model) {
        System.out.println("I reached /students and :" + professorId);
        List<Student> students;
        if (professorId == null) {
            return "error";
        }

        Professor professor = professorDao.getProfessorById(professorId);
        students = professorDao.getStudentsToProfessor(professorId);

        if (professor != null) {
            System.out.println("I'm inside: " + professorId);

            // Add professor object and students list to the model
            model.addAttribute("professor", professor);
            model.addAttribute(students);
            return "students";
        } else {
            // Handle case where professor is not found (e.g., redirect to error page)
            return "error";
        }
    }

}
