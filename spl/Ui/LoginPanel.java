package Ui;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class LoginPanel
{
    Scanner sc = new Scanner(System.in);
    public static void display (Scanner sc)
    {
        String email, password;
        System.out.println("please enter your email: ");
        email = sc.nextLine();
        System.out.println("enter your password: ");
        password = sc.nextLine();

        String ans = login (email, password);
        if (ans.equals("student"))
        {
            StudentPanel.display(sc);
        }
        else if (ans.equals("admin"))
        {
            AdminPanel.display();
        }
        else 
        {
            System.out.println("please enter correct credentials");
            return;
        }
    }

    public static String login (String email, String password)
    {
        String inputEmail = email;
        String inputPassword = password;

        if (inputEmail.equals("admin@gmail.com") && inputPassword.equals("1234"))
        {         
           return "admin";
        }
            
        try (BufferedReader br = new BufferedReader(new FileReader("Files/StudentCredentials.txt")))
        {
            String line;
            while ((line = br.readLine()) !=null)
            {
                String[] data = line.split(",");
                String actualEmail = data[0].trim();
                String actualPassword = data[1].trim();

                if (inputEmail.equals(actualEmail) && inputPassword.equals(actualPassword))
                {
                    return "student";
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return "none";
    }
}
