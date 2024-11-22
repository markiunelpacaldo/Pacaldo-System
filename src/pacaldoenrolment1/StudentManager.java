package pacaldoenrolment1;

import java.sql.*;
import java.util.Scanner;

public class StudentManager {

   static void addStudent(Connection conn, Scanner scanner) {
    try {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student email: ");
        String email = scanner.nextLine();

        // Check if the email already exists in the database
        String checkEmailQuery = "SELECT id FROM Students WHERE email = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkEmailQuery)) {
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("Error: A student with this email already exists.");
            } else {
                // Proceed with adding the student
                String insertQuery = "INSERT INTO Students (name, email) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                    stmt.setString(1, name);
                    stmt.setString(2, email);
                    stmt.executeUpdate();
                    System.out.println("Student added successfully.");
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error adding student: " + e.getMessage());
    }
}


    // View all students
    public static void viewStudents(Connection conn) {
        String query = "SELECT * FROM Students";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nStudents List:");
            System.out.println("+-----+------------------------+------------------------+");
            System.out.println("| ID  | Name                   | Email                  |");
            System.out.println("+-----+------------------------+------------------------+");

            while (rs.next()) {
                System.out.printf("| %-3d | %-22s | %-22s |\n",
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"));
            }
            System.out.println("+-----+------------------------+------------------------+");
        } catch (SQLException e) {
            System.out.println("Error viewing students: " + e.getMessage());
        }
    }

    // Update student details
    public static void updateStudent(Connection conn, Scanner scanner) {
        try {
            System.out.print("Enter student ID to update: ");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new student name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new student email: ");
            String email = scanner.nextLine();

            String query = "UPDATE Students SET name = ?, email = ? WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setInt(3, studentId);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Student updated successfully.");
                } else {
                    System.out.println("No student found with the given ID.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    // Delete a student
    public static void deleteStudent(Connection conn, Scanner scanner) {
        System.out.print("Enter student ID to delete: ");
        int studentId = scanner.nextInt();

        String deleteQuery = "DELETE FROM Students WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, studentId);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("No student found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }
}
