package com.proctorapp.dao;
import com.proctorapp.model.Professor;
import com.proctorapp.model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;


@Repository
public class StudentDao {

    @Autowired
    ProfessorDao professorDao;
    public Student saveStudent(Student student) {
        System.out.println("Students DAO reached");
        Transaction transaction = null;
        try (Session session = DAO.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(student);
            session.getTransaction().commit();
            System.out.println("Student added successfully with ID: " + student.getId());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("I'm outside rollback!!");
//          if (transaction != null) {
//              System.out.println("I'm in rollback!!");
//              transaction.rollback();
//                }
            return null;
        }
        return student;
    }


    public List<Student> getStudentsWithoutProfessor() {
        List<Student> students = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = DAO.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Create HQL query to fetch students without professor
            String hql = "FROM Student s WHERE s.professor IS NULL";
            Query<Student> query = session.createQuery(hql, Student.class);

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

    public void setProfessor(List<Long> StudentIds, Long professorId){
        Professor professor = professorDao.getProfessorById(professorId);
        List<Student> students;
        students = this.getStudentsWithId(StudentIds);
        Transaction transaction = null;
        try (Session session = DAO.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (Student student : students) {
                student.setProfessor(professor); // Assuming Student class has a professorId field
                session.saveOrUpdate(student); // Update the student in the database
            }
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Student> getStudentsWithId(List<Long> StudentsId){
        List<Student> students = null;
        try (Session session = DAO.getSessionFactory().openSession()) {
            students = new ArrayList<>();

            for (Long studentId : StudentsId) {
                Student student = session.get(Student.class, studentId);
                if (student != null) {
                    students.add(student);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return students;
    }

    public void updateStudent(Student student){
        Transaction transaction = null;
        try (Session session = DAO.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(student);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Student getStudentById(Long id){
        Session session = DAO.getSessionFactory().openSession();
        Student student = session.get(Student.class, id);
        session.close();
        System.out.println(student);
        return student;
    }
}
