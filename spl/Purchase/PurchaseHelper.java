package Purchase;
import Wallet.SmartWallet;
import java.io.*;
import java.util.*;

public class PurchaseHelper
{

    public static void buyToken (SmartWallet wallet, String mealType, Date date)
    {
        double mealCost = mealType.equals("breakfast") ? 40.00 : 70.00;

        if (wallet.deductBalance(mealCost))
        {
            logTransaction(wallet.getStudentID(), mealType, date);
        } else 
        {
            System.out.println("purchase failed due to insufficient balance");
        }
    }

    private static void logTransaction (int studentID, String mealType, Date date)
    {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("Tokens.txt", true))))
        {
            writer.println(studentID + "," + mealType + "," + date);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
