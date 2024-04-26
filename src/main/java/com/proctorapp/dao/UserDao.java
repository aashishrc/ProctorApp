package com.proctorapp.dao;

import com.proctorapp.model.Student;
import com.proctorapp.model.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    public Users addUser(Users user){
        System.out.println("Users DAO reached");
        Transaction transaction = null;
        try (Session session = DAO.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
//            session.persist(object);
            session.getTransaction().commit();
            System.out.println("User added successfully with ID: " + user.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
//            e.printStackTrace();
//            System.out.println("Failed to add user: " + e.getMessage());
            return null;
        }
        return user;
    }

    public Users getUserById(int id) {
        Session session = DAO.getSessionFactory().openSession();
        Users user = session.get(Users.class, id);
        session.close();
        return user;
    }

    public List<Users> getAllUsers() {
        Session session = DAO.getSessionFactory().openSession();
        Query<Users> query = session.createQuery("FROM Users", Users.class);
        List<Users> users = query.list();
        session.close();
        return users;
    }

    public void update(Users user) {
        Session session = DAO.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteUser(int id) {
        Session session = DAO.getSessionFactory().openSession();
        session.beginTransaction();
        Users user = session.get(Users.class, id);
        if (user != null) {
            session.delete(user);
        }
        session.getTransaction().commit();
        session.close();
    }

    public Users findByUsername(String username) {
        Users user = null;
        try {
            Session session = DAO.getSessionFactory().openSession();
            session.beginTransaction();
//            user = session.get(Users.class, username);
            Query query = session.createQuery("FROM Users WHERE username = :username");
            query.setParameter("username", username);
            user = (Users) query.uniqueResult();
            session.getTransaction().commit();
            session.close();
            System.out.println(user);
        } catch (HibernateException e) {
            if (user==null){
                System.out.println("Some Error ");
            }
//            e.printStackTrace();
            return null;
        }
        return user;
    }
}
