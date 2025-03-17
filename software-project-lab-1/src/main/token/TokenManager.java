package token;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import UI.UI;
import user.Student;
import wallet.Wallet;
import wallet.WalletManager;

public class TokenManager {
    private WalletManager walletManager;

    Scanner sc = new Scanner(System.in);

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
                return true; 
            } else {
                System.out.println("Error: Balance update mismatch. Please try again.");
                return false;
            }
        } else {
            return false;
        }
    }

    public void writePurchaseInFile (Student student, TokenType tokenType)
    {
        LocalDateTime purchaseTime = LocalDateTime.now();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchaseHistory.txt", true)))
        {
            writer.write(student.getUsername() + "," + tokenType + "," + purchaseTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n");
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
            UI.clearScreen();
            String[] tokenOptions = {
                "Breakfast Token",
                "Lunch Token",
                "Dinner Token",
                "Exit to Student Menu"
            };
            UI.printBoxedMenu(tokenOptions, "Select a Token to Buy");
    
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 
    
            switch (choice) {
                case 1:
                    if (buyToken(student, TokenType.BREAKFAST)) {
                        UI.clearScreen();
                        displayTokenPurchaseReceipt(student, "Breakfast Token");
                        UI.waitForUserInput("Press enter to go back to main panel" + UI.EMERALD_GREEN, sc);
                        UI.clearScreen();
                    }
                    else
                    {
                        UI.printMessage("Not sufficient balance to buy breakfast token", "error");
                        UI.waitForUserInput("Press enter to go back to main panel" + UI.EMERALD_GREEN, sc);
                    }
                    break;
                case 2:
                    if (buyToken(student, TokenType.LUNCH)) {
                        UI.clearScreen();
                        displayTokenPurchaseReceipt(student, "Lunch Token");
                        UI.waitForUserInput("Press enter to go back to main panel" + UI.EMERALD_GREEN, sc);
                        UI.clearScreen();
                    }
                    else
                    {
                        UI.printMessage("Not sufficient balance to buy Lunch token", "error");
                        UI.waitForUserInput("Press enter to go back to main panel" + UI.EMERALD_GREEN, sc);
                    }
                    break;
                case 3:
                    if (buyToken(student, TokenType.DINNER)) {
                        UI.clearScreen();
                        displayTokenPurchaseReceipt(student, "Dinner Token");
                        UI.waitForUserInput("Press enter to go back to main panel" + UI.EMERALD_GREEN, sc);
                        UI.clearScreen();
                    }
                    else
                    {
                        UI.printMessage("Not sufficient balance to buy dinner token", "error");
                        UI.waitForUserInput("Press enter to go back to main panel" + UI.EMERALD_GREEN, sc);
                    }
                    break;
                case 4:
                    UI.printMessage("Returning to Student Menu...", "info");
                    return;
                default:
                    UI.printMessage("Invalid choice. Please try again.", "error");
                    UI.waitForUserInput("Press Enter to retry", sc);
            }
        }
    }
    

    public void displayTokenPurchaseReceipt(Student student, String tokenType) {
        String studentName = student.getUsername();
        int studentId=student.getId();
        LocalDateTime purchaseDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = purchaseDate.format(formatter);
        String currentBalance = String.valueOf(student.getWallet().getBalance());
    
        UI.clearScreen();
    
        // Table headers
        String[] headers = {"Field", "Details"};
    
        // Table data
        String[][] data = {
            {"Student Name", studentName},
            {"Student Id", String.valueOf(studentId)},
            {"Purchase Date", formattedDate},
            {"Token Type", tokenType},
            {"Current Balance", currentBalance}
        };
    
        System.out.println(UI.BOLD + UI.LAVENDER_BLUE + "TOKEN PURCHASE RECEIPT" + UI.RESET);
        UI.printTable(headers, data);
    }
    
}