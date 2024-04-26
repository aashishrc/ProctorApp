package com.proctorapp.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
public class Student extends Users{

    private String name;

    @ManyToOne
    private Professor professor;

//    @OneToOne
//    @JoinColumn(name = "id")
//    private Users user;

    public Student() {

    }

//    public int getStudent_id() {
//        return student_id;
//    }

    public Student(String name, Professor professor) {
        this.name = name;
        this.professor = professor;
//        this.user = user;
    }

//    public void setStudent_id(int id) {
//        this.student_id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

}