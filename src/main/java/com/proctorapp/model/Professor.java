package com.proctorapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;

@Entity
public class Professor extends Users {
//    @Id
//    @GeneratedValue
//    private Long professor_id;

    private String name;

    @OneToMany(mappedBy = "professor", fetch = FetchType.EAGER)
    private List<Student> students = new ArrayList<>();

    // Constructors, getters, setters

    public Professor(String name, List<Student> students) {
//        this.professor_id = professor_id;
        this.name = name;
        this.students = students;
//        this.user = user;
    }

//    @OneToOne
//    @JoinColumn(name = "id")
//    private Users user;

    public Professor() {

    }

//    public Long getProfessor_id() {
//        return professor_id;
//    }
//
//    public void setProfessor_id(Long id) {
//        this.professor_id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}