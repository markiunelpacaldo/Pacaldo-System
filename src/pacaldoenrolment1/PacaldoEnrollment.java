package pacaldoenrolment1;

import java.sql.*;
import java.util.Scanner;

public class PacaldoEnrollment {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = Database.connect();

        // Create tables if not already created
        Database.createTables(conn);

        boolean running = true;
        while (running) {
            System.out.println("\nEnrollment System Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Add Course");
            System.out.println("6. View Courses");
            System.out.println("7. Update Course");
            System.out.println("8. Delete Course");
            System.out.println("9. Enroll Student");
            System.out.println("10. Unenroll Student");
            System.out.println("11. View Student Enrollments");  // New option added here
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 12.");
                scanner.next(); // Clear invalid input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    StudentManager.addStudent(conn, scanner);
                    break;
                case 2:
                    StudentManager.viewStudents(conn);
                    break;
                case 3:
                    StudentManager.updateStudent(conn, scanner);
                    break;
                case 4:
                    StudentManager.deleteStudent(conn, scanner);
                    break;
                case 5:
                    CourseManager.addCourse(conn, scanner);
                    break;
                case 6:
                    CourseManager.viewCourses(conn);
                    break;
                case 7:
                    CourseManager.updateCourse(conn, scanner);
                    break;
                case 8:
                    CourseManager.deleteCourse(conn, scanner);
                    break;
                case 9:
                    EnrollmentManager.enrollStudent(conn, scanner);
                    break;
                case 10:
                    EnrollmentManager.unenrollStudent(conn, scanner);
                    break;
                case 11:
            EnrollmentManager.viewAllStudentEnrollments(conn);
                       break;

                case 12:
                    System.out.print("Are you sure you want to exit? (Y/N): ");
                    String exitConfirmation = scanner.nextLine();
                    if ("Y".equalsIgnoreCase(exitConfirmation)) {
                        System.out.println("Exiting the system.");
                        return;
                    }
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing the database connection: " + e.getMessage());
        }
    }
}
