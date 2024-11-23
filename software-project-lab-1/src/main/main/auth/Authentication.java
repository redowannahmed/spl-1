package auth;
import java.io.*;

import exceptions.DuplicateIDException;
import exceptions.DuplicateUsernameException;
import user.Admin;
import user.Student;
import user.User;
import UI.UI;

public class Authentication {
    private static final String STUDENTS_FILE = "students.txt";
    private Admin admin = new Admin("admin", "admin123");

    public User login(String username, String password) {
        if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
            UI.printMessage("Logged in as Admin", "success");
            return admin;
        } else {
            return checkStudentCredentials(username, password);
        }
    }

    private Student checkStudentCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromString(line);
                if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                    UI.printMessage("Logged in as: " + student.getName(), "success");
                    return student;
                }
            }
        } catch (IOException e) {
            UI.printMessage("error reading from file", "error");
        }
        return null;
    }

    public void registerStudent(String name, String username, int id, String password) 
            throws DuplicateUsernameException, DuplicateIDException {
        
        if (!isStudentUsernameUnique(username)) {
            throw new DuplicateUsernameException("Username '" + username + "' is already taken.");
        }

        if (!isStudentIDUnique(id)) {
            throw new DuplicateIDException("ID '" + id + "' is already taken.");
        }

        Student student = new Student(name, username, id, password);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE, true))) {
            writer.write(student.toString());
            writer.newLine();
        } catch (IOException e) {
            UI.printMessage("Error writing to students file: " + e.getMessage(), "error");
        }
    }

    public boolean isStudentUsernameUnique(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) { 
                    continue;
                }
                try {
                    Student student = Student.fromString(line);
                    if (student.getUsername().equals(username)) {
                        return false;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking username uniqueness: " + e.getMessage());
        }
        return true;
    }
    
    private boolean isStudentIDUnique(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    Student student = Student.fromString(line);
                    if (student.getId() == id) {
                        return false;
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking ID uniqueness: " + e.getMessage());
        }
        return true;
    }
    
    public Student findStudentByUsername(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromString(line);
                if (student.getUsername().equals(username)) {
                    return student;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading students file: " + e.getMessage());
        }
        return null;
    }
}
