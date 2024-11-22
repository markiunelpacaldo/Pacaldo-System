package pacaldoenrolment1;

import java.sql.*;

public class Database {

    private static final String DATABASE_URL = "jdbc:sqlite:enrolment.db";

    // Establish a connection to the database
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return conn;
    }

    // Create tables if they don't exist
    public static void createTables(Connection conn) {
        String createStudentsTable = "CREATE TABLE IF NOT EXISTS Students (" +
                                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                     "name TEXT NOT NULL, " +
                                     "email TEXT NOT NULL UNIQUE)";
        String createCoursesTable = "CREATE TABLE IF NOT EXISTS Courses (" +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    "course_name TEXT NOT NULL)";
        String createEnrollmentsTable = "CREATE TABLE IF NOT EXISTS Enrollments (" +
                                        "student_id INTEGER, " +
                                        "course_id INTEGER, " +
                                        "PRIMARY KEY (student_id, course_id), " +
                                        "FOREIGN KEY (student_id) REFERENCES Students(id), " +
                                        "FOREIGN KEY (course_id) REFERENCES Courses(id))";

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createStudentsTable);
            stmt.executeUpdate(createCoursesTable);
            stmt.executeUpdate(createEnrollmentsTable);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
}
