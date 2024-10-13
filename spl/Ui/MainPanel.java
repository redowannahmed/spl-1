package Ui;

import java.util.Scanner;

public class MainPanel 
{
    public Scanner sc;

    public MainPanel ()
    {
        sc = new Scanner(System.in);
    }

    public void display (Scanner sc)
    {
        while (true) {
            System.out.println("\n--- Welcome ---");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice)
            {
                case 1:
                    LoginPanel.display(sc);
                    break;
                case 2:
                    RegistrationPanel.display(sc);
                    break;
                case 3:
                    System.exit(0);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MainPanel m = new MainPanel();
        m.display(sc);
    }
}
