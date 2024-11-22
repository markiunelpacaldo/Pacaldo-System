package pacaldoenrolment1;

import java.sql.*;
import java.util.Scanner;

public class CourseManager {

    // Add a new course
    public static void addCourse(Connection conn, Scanner scanner) {
        try {
            System.out.print("Enter course name: ");
            String courseName = scanner.nextLine();

            String query = "INSERT INTO Courses (course_name) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, courseName);
                stmt.executeUpdate();
                System.out.println("Course added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }

    // View all courses
    public static void viewCourses(Connection conn) {
        String query = "SELECT * FROM Courses";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nCourses List:");
            System.out.println("+-----+------------------------+");
            System.out.println("| ID  | Course Name            |");
            System.out.println("+-----+------------------------+");

            while (rs.next()) {
                System.out.printf("| %-3d | %-22s |\n",
                        rs.getInt("id"), rs.getString("course_name"));
            }
            System.out.println("+-----+------------------------+");
        } catch (SQLException e) {
            System.out.println("Error viewing courses: " + e.getMessage());
        }
    }

    // Update course details
    public static void updateCourse(Connection conn, Scanner scanner) {
        try {
            System.out.print("Enter course ID to update: ");
            int courseId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new course name: ");
            String courseName = scanner.nextLine();

            String query = "UPDATE Courses SET course_name = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, courseName);
                stmt.setInt(2, courseId);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Course updated successfully.");
                } else {
                    System.out.println("No course found with the given ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating course: " + e.getMessage());
        }
    }

    // Delete a course
    public static void deleteCourse(Connection conn, Scanner scanner) {
        System.out.print("Enter course ID to delete: ");
        int courseId = scanner.nextInt();

        String deleteQuery = "DELETE FROM Courses WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, courseId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Course deleted successfully.");
            } else {
                System.out.println("No course found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting course: " + e.getMessage());
        }
    }
}
