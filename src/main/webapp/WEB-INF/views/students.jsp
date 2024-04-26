<%@ page import="com.proctorapp.model.Professor" %>
<%@ page import="com.proctorapp.model.Student" %>
<%@ page import="com.proctorapp.model.Users" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Assuming you have a Professor object retrieved and stored in a variable named 'professor' --%>
<% Professor professor = (Professor) session.getAttribute("professor");
    Users user = (Users) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Professor Dashboard - Students</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            padding: 8px;
            border: 1px solid #ddd;
        }

        .add-student-btn {
            background-color: #4CAF50; /* Green */
            border: none;
            color: white;
            padding: 8px 16px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
        }

        .remove-student-btn {
            background-color: #f44336; /* Red */
            border: none;
            color: white;
            padding: 8px 16px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
        }

        #myModal {
            display: none;
        }
        .container {
            display: flex;
            flex-direction: column;
            height: 100vh;
        }
        .top-nav {
            background-color: #333;
            color: #fff;
            padding: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .logout-btn {
            background-color: #d9534f;
            color: #fff;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
        }
        .logo{
            color: #cccccc;
            text-decoration-color: white;
        }
        a{
            text-decoration: none;
            color: white;
        }

        /* Customize button styles as needed */
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function () {
            // Function to handle when "Add Student" button is clicked
            $(".add-student-btn").click(function () {
                $("#myModal").css("display", "block"); // Show modal
                fetchUnassociatedStudents(); // Fetch unassociated students via AJAX
            });

            // Function to fetch unassociated students via AJAX
            function fetchUnassociatedStudents() {
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/studentWithoutProfessor",
                    dataType: "json",
                    success: function (response) {
                        var students = response;
                        var studentListHtml = "";
                        for (var i = 0; i < students.length; i++) {
                            var student = students[i];
                            studentListHtml += "<div><input type='checkbox' name='selectedStudents' value='" + student.id + "'>";
                            studentListHtml += "<label>" + student.name + "</label></div>";
                        }
                        $("#studentList").html(studentListHtml);
                    },
                    error: function (xhr, status, error) {
                        console.error(error);
                    }
                });
            }

            // Function to handle modal close events
            $(".close, #cancelAddStudent").click(function () {
                $("#myModal").css("display", "none"); // Hide modal
            });
        });
    </script>
</head>
<body>
<%
    Long id = (Long) session.getAttribute("id");
    String userType = user.getUserType();
    String prefix;
    if(userType.equals("Professor")){
        prefix = "Prof.";
    }else{
        prefix = "";
    }

%>
<div class="container">
    <div class="top-nav">
        <div class="logo">
            <a href="">ProctorApp</a>
        </div>
        <div>
            <!-- Display logged-in user's name -->
            Logged in as: <%=prefix%>${user.firstname}
        </div>
        <div>
            <form action="/logout" method="get">
                <button class="logout-btn" type="submit">Logout</button>
            </form>
        </div>
    </div>

<h2>Students for Professor: <%= user.getFirstname() %>
</h2>


<c:if test ="${not empty professor.students}">
    Check if there are any students associated with the professor
    <table id="associatedStudents">
        <tr>
            <th>Name</th>
            <th>Action</th>
        </tr>
        <c:forEach var="student" items="${professor.students}">
            <tr>
                <td><c:out value="${student.name}"/></td>
                <td>
                    <form action="/unassociateStudent?professorId=${id}" method="post">
                        <input type="hidden" name="studentId" value="${student.id}"/>
                        <button class="remove-student-btn" type="submit">Remove</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${empty professor.students}">
    <p>No students currently assigned to this professor.</p>
</c:if>

<script>
    $(document).ready(function () {
        // Function to handle form submission
        $("#addStudentForm").submit(function (event) {
            event.preventDefault(); // Prevent default form submission
            var selectedStudents = []; // Array to store selected student IDs
            $("input[name='selectedStudents']:checked").each(function () {
                selectedStudents.push($(this).val()); // Add selected student ID to the array
            });

            // AJAX POST request to send selected students to the controller
            $.ajax({
                type: "POST",
                url: $(this).attr("action"),
                data: JSON.stringify(selectedStudents),
                contentType: "application/json",
                success: function (response) {
                    // Handle success response
                    console.log("Students added successfully.");
                    $("#associatedStudents").load(location.href + " #associatedStudents");
                    // You can perform any additional actions here, such as closing the modal or refreshing the page
                },
                error: function (xhr, status, error) {
                    // Handle error response
                    console.error("Error adding students:", error);
                }
            });
        });

        // Other JavaScript code...
    });
</script>

<c:set var = "id" value="${sessionScope.user.id}"/>
<!-- Modal -->
<div id="myModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <h3>Add Students</h3>
        <form id="addStudentForm" action="${pageContext.request.contextPath}/associateStudents?professorId=${id}" method="post">
            <div id="studentList"></div>
            <button type="submit" class="add-student-btn">Add Selected Students</button>
            <button type="button" id="cancelAddStudent">Cancel</button>
        </form>
    </div>
</div>

<button type="button" class="add-student-btn">Add Student</button>

</body>
</html>