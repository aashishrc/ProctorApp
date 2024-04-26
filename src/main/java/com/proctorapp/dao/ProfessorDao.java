package com.proctorapp.dao;
import com.proctorapp.model.Professor;
import com.proctorapp.model.Student;
import com.proctorapp.model.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProfessorDao {

    public Professor saveProfessor(Professor professor){
        System.out.println("Professors DAO reached");
        Transaction transaction = null;
        try (Session session = DAO.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(professor);
            session.getTransaction().commit();
            System.out.println("Professor added successfully with ID: " + professor.getId());
        } catch (Exception e) {
            e.printStackTrace();
//            if (transaction != null) {
//                transaction.rollback();
//            }
            return null;
        }
    return professor;
    }

    public Professor getProfessorById(Long id){
        Session session = DAO.getSessionFactory().openSession();
        Professor professor = session.get(Professor.class, id);
        session.close();
        System.out.println(professor);
        return professor;
    }

    public List<Student> getStudentsToProfessor(Long professorId) {
        List<Student> students = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = DAO.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Create HQL query to fetch students assigned to the given professor
            String hql = "FROM Student s WHERE s.professor.id = :professorId";
            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("professorId", professorId);

            // Execute query and retrieve the list of students
            students = query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return students;
    }

}

