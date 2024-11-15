import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TokenManager {
    private WalletManager walletManager;

    public TokenManager(WalletManager walletManager) {
        this.walletManager = walletManager;
    }

    
    public boolean buyToken(Student student, TokenType tokenType) {
        int tokenCost = tokenType.getCost();
        Wallet wallet = student.getWallet();
    
        if (wallet.getBalance() >= tokenCost) {
            wallet.deductBalance(tokenCost);
            walletManager.updateWalletBalanceInFile(student.getUsername(), wallet.getBalance()); // Persist change
            writePurchaseInFile(student, tokenType);

            int confirmedBalance = walletManager.loadBalanceFromFile(student.getUsername());
            if (confirmedBalance == wallet.getBalance()) {
                System.out.println("Token purchased successfully: " + tokenType);
                System.out.println("Remaining Balance: " + wallet.getBalance());
                return true; // Indicate successful purchase
            } else {
                System.out.println("Error: Balance update mismatch. Please try again.");
                return false;
            }
        } else {
            System.out.println("Insufficient balance to buy " + tokenType + " token.");
            return false;
        }
    }

    public void writePurchaseInFile (Student student, TokenType tokenType)
    {
        LocalDateTime purchaseTime = LocalDateTime.now();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchaseHistory.txt", true)))
        {
            writer.write(student.getUsername() + "," + tokenType + "," + purchaseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void viewPurchaseHistory(Student student) {
        boolean hasPurchases = false;
    
        try (BufferedReader br = new BufferedReader(new FileReader("purchaseHistory.txt"))) {
            String line;
    
            System.out.println("+-----------------------------------------+");
            System.out.println("|           Your Purchase History         |");
            System.out.println("+-----------------------------------------+");
    
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(student.getUsername())) {
                    System.out.printf("| %-15s | %-20s  |\n", "Purchase Date:", data[2]);
                    System.out.printf("| %-15s | %-20s  |\n", "Token Type:", data[1]);
                    System.out.println("+-----------------------------------------+");
                    hasPurchases = true;
                }
            }
    
            if (!hasPurchases) {
                System.out.println("|       No purchase history found         |");
                System.out.println("+-----------------------------------------+");
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void showTokenBuyingOptions(Student student, Scanner sc) {
        while (true) {
            System.out.println("\nSelect a token to buy:");
            System.out.println("1. Breakfast Token");
            System.out.println("2. Lunch Token");
            System.out.println("3. Dinner Token");
            System.out.println("4. Exit to Student Menu");
    
            int choice = sc.nextInt();
            sc.nextLine(); // Clear buffer
    
            switch (choice) {
                case 1:
                    if (buyToken(student, TokenType.BREAKFAST)) {
                        UI.clearScreen();
                        displayTokenPurchaseReceipt(student, "Breakfast Token");
                    }
                    UI.waitForUser(sc);
                    break;
                case 2:
                    if (buyToken(student, TokenType.LUNCH)) {
                        UI.clearScreen();
                        displayTokenPurchaseReceipt(student, "Lunch Token");
                    }
                    UI.waitForUser(sc);
                    break;
                case 3:
                    if (buyToken(student, TokenType.DINNER)) {
                        UI.clearScreen();
                        displayTokenPurchaseReceipt(student, "Dinner Token");
                    }
                    UI.waitForUser(sc);
                    break;
                case 4:
                    System.out.println("Returning to Student Menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    

    public void displayTokenPurchaseReceipt(Student student, String tokenType) {
        String studentName = student.getUsername();
        LocalDateTime purchaseDate = LocalDateTime.now(); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println();

        
        System.out.println("+-----------------------------------------+");
        System.out.println("|           Token Purchase Receipt        |");
        System.out.println("+-----------------------------------------+");
        System.out.printf("| %-15s | %-20s  |\n", "Student Name:", studentName);
        System.out.printf("| %-15s | %-20s  |\n", "Purchase Date:", purchaseDate.format(formatter));
        System.out.printf("| %-15s | %-20s  |\n", "Token Type:", tokenType);
        System.out.println("+-----------------------------------------+");

        System.out.println();
        
        System.out.println("Current balance: " + student.getWallet().getBalance());
    }
}
