import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class UpdateInfo
{

    public static void updateInfo(Student student, Authentication auth) {
        Scanner sc = new Scanner(System.in);
    
        while (true) {
            System.out.println("\nUpdate Info:");
            System.out.println("1. Update Username");
            System.out.println("2. Update Password");
            System.out.println("3. Return to Menu");
    
            int choice = sc.nextInt();
            sc.nextLine();
    
            switch (choice) {
                case 1:
                    System.out.println("Enter new username:");
                    String newUsername = sc.nextLine();
                    if (updateUsername(student, auth, newUsername)) {
                        System.out.println("Username updated successfully.");
                    } else {
                        System.out.println("Please try again with a different username.");
                    }
                    break;
                case 2:
                    System.out.println("Enter new password:");
                    String newPassword = sc.nextLine();
                    updatePassword(student, auth, newPassword);
                    System.out.println("Password updated successfully.");
                    break;
                case 3:
                    return; // Exit update info menu
                default:
                    System.out.println("Invalid option. Please choose again.");
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
