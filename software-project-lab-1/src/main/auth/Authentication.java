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
        String pass = customHash(password);
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = Student.fromString(line);
                if (student.getUsername().equals(username) && student.getPassword().equals(pass)) {
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

        String hashedPassword = customHash(password);

        Student student = new Student(name, username, id, hashedPassword);
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

    public static boolean isValidPassword (String password)
    {
        if (password.length() < 6)
        {
            return false;
        }

        boolean hasUpperCase = false, hasLowerCase = false, hasNumber = false;

        for (char ch: password.toCharArray())
        {
            if (Character.isUpperCase(ch))
            {
                hasUpperCase = true;
            }

            if (Character.isLowerCase(ch))
            {
                hasLowerCase = true;
            }

            if (Character.isDigit(ch))
            {
                hasNumber = true;
            }
        }

        return hasLowerCase && hasUpperCase && hasNumber;
    }

    public static String customHash (String password)
    {
        int hash = 7;
        for (int i = 0; i<password.length(); i++)
        {
            char ch = password.charAt(i);
            hash = (hash * 31 + ch) ^ (hash >> 5);
        }

        StringBuilder sb = new StringBuilder();

        while (hash !=0 ) {
            int hexDigit = hash & 0xF;
            sb.append(Integer.toHexString(hexDigit));
            hash >>>= 4;
        }

        return sb.toString();
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
