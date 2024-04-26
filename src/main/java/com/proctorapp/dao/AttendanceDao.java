//package com.proctorapp.dao;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//import jakarta.transaction.Transactional;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class AttendanceDao {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Transactional
//    public void saveAttendance(Attendance attendance) {
//        entityManager.persist(attendance);
//    }
//
//    public List<Attendance> getAttendancesByStudentId(Long studentId) {
//        TypedQuery<Attendance> query = entityManager.createQuery("SELECT a FROM Attendance a WHERE a.student.id = :studentId", Attendance.class);
//        query.setParameter("studentId", studentId);
//        return query.getResultList();
//    }
//
//    // Other methods as needed
//}
//
