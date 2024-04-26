<%@ page import="java.util.List" %>
<%@ page import="com.proctorapp.model.Student" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <!-- Add necessary styles and scripts for modal form -->
</head>
<body>

<!-- Modal form for adding a student -->
<div id="addStudentModal" class="modal">
    <div class="modal-content">
        <!-- Form to display unassigned students and add a student -->
        <form action="/addStudent" method="post">
            <h2>Add Student</h2>
            <select name="studentId">
                <!-- Iterate over unassigned students and display options -->
                <c:forEach var="student" items="${unassignedStudents}">
                    <option value="${student.id}">${student.name}</option>
                </c:forEach>
            </select>
            <button type="submit">Add</button>
            <button type="button" onclick="closeModal()">Cancel</button>
        </form>
    </div>
</div>

<!-- JavaScript to handle modal functionality -->
<script>
    // Function to display modal form
    function openModal() {
        document.getElementById('addStudentModal').style.display = 'block';
    }

    // Function to close modal form
    function closeModal() {
        document.getElementById('addStudentModal').style.display = 'none';
    }
</script>

<!-- Button to open modal form -->
<button onclick="openModal()">Add Student</button>

</body>
</html>
