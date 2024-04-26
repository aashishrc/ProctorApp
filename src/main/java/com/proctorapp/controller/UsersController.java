package com.proctorapp.controller;

import com.proctorapp.dao.ProfessorDao;
import com.proctorapp.dao.StudentDao;
import com.proctorapp.dao.UserDao;
import com.proctorapp.model.Professor;
import com.proctorapp.model.Student;
import com.proctorapp.model.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.List;
import java.util.regex.Pattern;


@Controller
public class UsersController {

    @GetMapping("/register")
    public String registerUser(){
        return "register";
    }

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProfessorDao professorDao;

    @Autowired
    private StudentDao studentDao;

    // Add a new user
    @PostMapping("/add")
    public String registerUser(Users user, HttpSession session) {
        String username = user.getUsername();
        String password = user.getPassword();
        String userType = user.getUserType();
        String FullName = user.getFirstname() + " " + user.getLastname();

//        String USERNAME_PATTERN = "^[a-zA-Z0-9._%+-]+@northeastern\\.edu$";
//        String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        String USERNAME_PATTERN = ".+";
        String PASSWORD_PATTERN = ".+";
//        Student user1 = null;

        if (Pattern.compile(USERNAME_PATTERN).matcher(username).matches()){
            if(Pattern.compile(PASSWORD_PATTERN).matcher(password).matches()){
                user = userDao.addUser(user);
                if (userType.equals("Professor")){
                    Professor professor = new Professor(FullName, null);
//                    user = userDao.addUser(user,professor);
                    professor = professorDao.saveProfessor(professor);
                }
                else{
                    Student student = new Student(FullName, null);
//                    user = userDao.addUser(student);
                    student = studentDao.saveStudent(student);

                }

                String error;
                if(user!=null)
                    error = "";
                else{
                    error = "User Already Exists. Please try logging in";
                }
                session.setAttribute("error",error);
                System.out.println("error " + error);
                return "login";
            }
            else{
                String error = "Error with the Password. Your Password should have 1 Uppercase, 1 lowercase, 1 number " +
                        "and one special character";
                session.setAttribute("error",error);
                return "register-fail";
            }

        }
        else{String error = "Error with the Username. Your Email is not associated with Northeastern University";
            session.setAttribute("error",error);
            return "register-fail";
        }

    }

    // Get all users
    @GetMapping
    public String getAllUsers(Model model) {
        List<Users> users = userDao.getAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    // Get user by ID
    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id, Model model) {
        Users user = userDao.getUserById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "user-details";
        } else {
            return "user-not-found";
        }
    }

    // Delete a user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userDao.deleteUser(id);
        return "redirect:/users";
    }

}
