package auth;
import java.io.*;
import java.util.*;
import UI.UI;
import user.Student;

public class UpdateInfo {
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
                    System.out.print("Enter old username: ");
                    String oldUsername = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();
                    System.out.print("Enter new username: ");
                    String newUsername = sc.nextLine();
                    
                    if (updateUsername(student, auth, oldUsername, password, newUsername)) {
                        UI.printMessage("Username updated successfully.", "success");
                    } else {
                        UI.printMessage("Error: Invalid credentials or username already exists.", "error");
                    }
                    UI.waitForUserInput("Press Enter to continue...", sc);
                    break;
                
                case 2:
                    UI.clearScreen();
                    System.out.print("Enter old password: ");
                    String oldPassword = sc.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = sc.nextLine();
                    System.out.print("Confirm new password: ");
                    String confirmPassword = sc.nextLine();
                    
                    if (updatePassword(student, auth, oldPassword, newPassword, confirmPassword)) {
                        UI.printMessage("Password updated successfully.", "success");
                    } else {
                        UI.printMessage("Error: Invalid credentials or password format incorrect.", "error");
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

    public static boolean updateUsername(Student student, Authentication auth, String oldUsername, String password, String newUsername) {
        if (!student.getUsername().equals(oldUsername) || !student.getPassword().equals(auth.customHash(password))) {
            return false;
        }

        if (!auth.isStudentUsernameUnique(newUsername)) {
            return false;
        }
        
        student.setUsername(newUsername);
        return saveStudentChanges(student);
    }

    public static boolean updatePassword(Student student, Authentication auth, String oldPassword, String newPassword, String confirmPassword) {
        if (!student.getPassword().equals(auth.customHash(oldPassword))) {
            return false;
        }

        if (!newPassword.equals(confirmPassword) || !auth.isValidPassword(newPassword)) {
            return false;
        }
        
        student.setPassword(auth.customHash(newPassword));
        return saveStudentChanges(student);
    }

    private static boolean saveStudentChanges(Student student) {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student std = Student.fromString(line);
                if (std.getId() == student.getId()) {
                    students.add(student);
                } else {
                    students.add(std);
                }
            }
        } catch (IOException e) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"))) {
            for (Student st : students) {
                writer.write(st.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
