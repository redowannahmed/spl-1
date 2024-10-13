package Ui;

import java.util.Scanner;

import Purchase.PurchaseHelper;
import User.Student;

public class StudentPanel
{
    Scanner sc = new Scanner(System.in);

    public static void display (Scanner sc, Student student)
    {
        while (true) {
            System.out.println("1. Buy Token (Breakfast, Lunch, Dinner)");
            System.out.println("2. Recharge Wallet");
            System.out.println("3. View Purchase History");
            System.out.println("4. View Menu");
            System.out.println("5. View Pending Requests");
            System.out.println("6. Update Info");
            System.out.println("7. Go Back to Main Menu");

            int choice = sc.nextInt();
            switch (choice)
            {
                case 1:
                    PurchaseHelper.buyToken(student.getWallet(), null, null);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    return;
                default:
                    break;
            }
        }
    }
}
