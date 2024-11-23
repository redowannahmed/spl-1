package auth;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import UI.UI;
import user.Student;

public class UpdateInfo
{
    Scanner sc = new Scanner(System.in);

        public static void updateInfo(Student student, Authentication auth, Scanner sc) {
        while (true) {
            UI.clearScreen();
            UI.printBoxedMenu(new String[]{
                    "Update Username",
                    "Update Password",
                    "Return to Main Menu"
            }, "Update Info Panel");

            System.out.print(UI.colorText("Enter your choice: ", UI.EMERALD_GREEN));
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    UI.clearScreen();
                    System.out.print(UI.colorText("Enter new username: ", UI.EMERALD_GREEN));
                    String newUsername = sc.nextLine();

                    if (updateUsername(student, auth, newUsername)) {
                        UI.printMessage("Username updated successfully.", "success");
                    } else {
                        UI.printMessage("Error: Username already exists or update failed.", "error");
                    }
                    UI.waitForUserInput("Press Enter to continue...", sc);
                    break;

                case 2:
                    UI.clearScreen();
                    System.out.print(UI.colorText("Enter new password: ", UI.EMERALD_GREEN));
                    String newPassword = sc.nextLine();

                    if (newPassword.trim().isEmpty()) {
                        UI.printMessage("Password cannot be empty.", "error");
                    } else {
                        updatePassword(student, auth, newPassword);
                        UI.printMessage("Password updated successfully.", "success");
                    }
                    UI.waitForUserInput("Press Enter to continue...", sc);
                    break;

                case 3:
                    UI.printMessage("Returning to the main menu...", "info");
                    return;

                default:
                    UI.printMessage("Invalid option. Please try again.", "error");
                    UI.waitForUserInput("Press Enter to continue...", sc);
            }
        }
    }    

    public static boolean updateUsername(Student student, Authentication auth, String newUsername) {
        List<Student> students = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Student stdnt = new Student(data[0], data[1], Integer.parseInt(data[2]), data[3]);
                students.add(stdnt);
            }
        } catch (IOException e) {
            System.out.println("Error reading student data from file: " + e.getMessage());
            return false;
        }
    
        // Check if the new username already exists
        for (Student st : students) {
            if (st.getUsername().equals(newUsername)) {
                System.out.println("Error: Username already taken. Please choose a different username.");
                return false;
            }
        }
    
        // Update the student's username if it's unique
        for (Student st : students) {
            if (st.getUsername().equals(student.getUsername())) {
                if (newUsername != null) {
                    st.setUsername(newUsername);
                    break;
                }
            }
        }
    
        // Write updated data back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"))) {
            for (Student st : students) {
                writer.write(st.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing updated student data to file: " + e.getMessage());
            return false;
        }
    
        // Update the current student's username in memory
        student.setUsername(newUsername);
        return true;
    }
    

    public static void updatePassword(Student student, Authentication auth, String newPassword)
    {
        {
            List<Student> students = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
                String line;
                while ((line = reader.readLine()) != null)
                {
                    String[] data = line.split(",");
                    Student stdnt = new Student(data[0], data[1], Integer.parseInt(data[2]), data[3]);

                    students.add(stdnt);
                }
            }

            catch (IOException e) {
                e.printStackTrace();
            }

            for (Student st: students)
            {
                if (st.getUsername().equals(student.getUsername()))
                {
                    if (newPassword !=null)
                    {
                        st.setPassword(newPassword);
                        break;
                    }
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt")))
            {
                for (Student st: students)
                {
                    writer.write(st.toString());
                    writer.newLine();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            student.setPassword(newPassword);
        }
    }
}