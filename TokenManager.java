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
            wallet.deductBalance(tokenCost); // Deduct balance
            walletManager.updateWalletBalanceInFile(student.getUsername(), wallet.getBalance()); // Persist change
    
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
            return false; // Indicate unsuccessful purchase due to insufficient balance
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
