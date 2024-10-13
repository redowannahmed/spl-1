package Ui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import User.Student;

public class RegistrationPanel {
    Scanner sc = new Scanner(System.in);
    
    public static void display (Scanner sc)
    {
        String email, password, name, department;
        int id, semester;

        System.out.print("please enter your name: ");
        name = sc.nextLine();
        System.out.println();
        System.out.print("email: ");
        email = sc.nextLine();
        System.out.println();
        System.out.print("department: ");
        department = sc.nextLine();
        System.out.println();
        System.out.print("id: ");
        id = sc.nextInt();
        sc.nextLine();
        System.out.print("semester: ");
        semester = sc.nextInt();
        sc.nextLine();
        System.out.print("set a password: ");
        password = sc.nextLine();
        System.out.println();

        Student student = new Student(email, password, name, id, department, semester);

        if (registerAsStudent(student))
        {
            System.out.println("congratulations for successfully registering");
            return;
        } else 
        {
            System.out.println("provide valid details");
        }
    }

    public static boolean registerAsStudent (Student student)
    {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("Files/StudentCredentials.txt", true))))
        {
            writer.println(student.getEmail() +","+ student.getPassword());
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
