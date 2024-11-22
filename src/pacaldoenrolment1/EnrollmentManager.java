package pacaldoenrolment1;

import java.sql.*;
import java.util.Scanner;

public class EnrollmentManager {

    // Enroll a student in a course
    public static void enrollStudent(Connection conn, Scanner scanner) {
    try {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter course ID: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check if the student is already enrolled in the course
        String checkEnrollmentQuery = "SELECT COUNT(*) FROM Enrollments WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkEnrollmentQuery)) {
            checkStmt.setInt(1, studentId);
            checkStmt.setInt(2, courseId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                System.out.println("Error: Student is already enrolled in this course.");
            } else {
                // Enroll the student if not already enrolled
                String enrollQuery = "INSERT INTO Enrollments (student_id, course_id) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(enrollQuery)) {
                    stmt.setInt(1, studentId);
                    stmt.setInt(2, courseId);
                    stmt.executeUpdate();
                    System.out.println("Student enrolled successfully.");
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error enrolling student: " + e.getMessage());
    }
}


    // Unenroll a student from a course
    public static void unenrollStudent(Connection conn, Scanner scanner) {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        System.out.print("Enter course ID: ");
        int courseId = scanner.nextInt();

        String query = "DELETE FROM Enrollments WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student unenrolled successfully.");
            } else {
                System.out.println("No enrollment found for this student in the course.");
            }
        } catch (SQLException e) {
            System.out.println("Error unenrolling student: " + e.getMessage());
        }
    }

    // View all enrollments for a student
   // View all enrollments for all students
public static void viewAllStudentEnrollments(Connection conn) {
    try {
        String query = "SELECT s.name AS student_name, c.course_name AS course_name " +
                       "FROM Students s " +
                       "JOIN Enrollments e ON s.id = e.student_id " +
                       "JOIN Courses c ON e.course_id = c.id";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("\nStudent Enrollments:");
            System.out.println("+--------------------+----------------------------+");
            System.out.println("| Student Name       | Course Name                |");
            System.out.println("+--------------------+----------------------------+");

            while (rs.next()) {
                String studentName = rs.getString("student_name");
                String courseName = rs.getString("course_name");
                System.out.printf("| %-18s | %-26s |\n", studentName, courseName);
            }
            System.out.println("+--------------------+----------------------------+");
        }
    } catch (SQLException e) {
        System.out.println("Error viewing all student enrollments: " + e.getMessage());
    }
}


}
