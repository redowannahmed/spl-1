package controller;

import UI.UI;
import analytics.AdminAnalytics;
import auth.Authentication;
import auth.AuthenticationHelper;
import auth.UpdateInfo;
import java.util.Scanner;
import token.MenuManagement;
import token.TokenManager;
import user.Admin;
import user.Student;
import user.User;
import wallet.WalletManager;

public class Main {
    public static void main(String[] args) {
        Authentication auth = new Authentication();
        WalletManager walletManager = new WalletManager(auth);
        AuthenticationHelper authHelper = new AuthenticationHelper(auth);
        TokenManager tokenHelper = new TokenManager(walletManager);
        Scanner sc = new Scanner(System.in);
    
        while (true) {
            UI.clearScreen();
            UI.showBanner();
    
            String[] mainMenuOptions = {"Login", "Register", "Exit"};
            UI.printBoxedMenu(mainMenuOptions, "Main Menu");
    
            int choice = sc.nextInt();
            sc.nextLine();
    
            switch (choice) {
                case 1: 
                    UI.clearScreen();
                    User loggedInUser = authHelper.loginHelper(sc);
    
                    if (loggedInUser != null) {
                        System.out.println();
                        UI.showLoading("Redirecting to your dashboard");
                        UI.clearScreen();
    
                        if (loggedInUser instanceof Admin) {
                            showAdminMenu(sc, walletManager);
                        } else if (loggedInUser instanceof Student) {
                            showStudentMenu(sc, (Student) loggedInUser, auth, walletManager, tokenHelper);
                        }
    
                    } else {
                        UI.waitForUserInput("Press enter to go back to main panel", sc);
                    }
                    break;
    
                case 2: 
                    UI.clearScreen();
                    authHelper.registerHelper(sc);
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
                    break;
    
                case 3: 
                    UI.printMessage("Thank you for using Token-Table!", "info");
                    sc.close();
                    return;
    
                default:
                    UI.printMessage("Invalid option. Please try again.", "error");
            }
        }
    }

    private static void showStudentMenu(Scanner sc, Student student, Authentication auth, WalletManager walletManager, TokenManager tokenManager) {
        while (true) {
            UI.clearScreen();

            System.out.println(UI.SLATE_BLUE + UI.BOLD + "WELCOME, " + student.getName().toUpperCase() + "!" + UI.RESET);

            System.out.println();

            String[] studentMenuOptions = {
                "Recharge Wallet", "View Balance", "Buy Token",
                "View Pending Recharge Requests", "View Purchase History",
                "View Menu", "Update Info", "Logout"
            };
            UI.printBoxedMenu(studentMenuOptions, "Dashboard");

        
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    UI.clearScreen();
                    UI.printMessage("Enter recharge amount:", "info");
                    int amount = sc.nextInt();
                    sc.nextLine();

                    String slipId = WalletManager.generateUniqueSlipID();
                    UI.printMessage("Your unique transaction ID: " + UI.CORAL_RED+ slipId + UI.RESET, "info");
                    System.out.println();
                    UI.printMessage("Use this transaction ID as the payment reference in bKash/Nagad/Bank transfer.", "info");
                    System.out.println();
                    
                    if (walletManager.requestRecharge(student.getUsername(), amount, slipId)) {
                        UI.printMessage("Recharge request submitted successfully.", "success");
                        UI.waitForUserInput("Press enter to go back to the main panel", sc);
                    } else {
                        UI.printMessage("Error requesting recharge", "error");
                        UI.waitForUserInput("Press enter to go back to the main panel", sc);
                    }
                    break;

                case 2: 
                    UI.clearScreen();
                    UI.printMessage("Your current balance: " + student.getWallet().getBalance(), "info");
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
                    break;

                case 3: 
                    UI.clearScreen();
                    tokenManager.showTokenBuyingOptions(student, sc);
                    break;
                    
                case 4: 
                    UI.clearScreen();
                    walletManager.viewPendingRequests(student.getUsername());
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
                    break;

                case 5: 
                    UI.clearScreen();
                    tokenManager.viewPurchaseHistory(student);
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
                    break;

                case 6: 
                    UI.clearScreen();
                    MenuManagement.displayMenu();
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
                    break;
                
                case 7:
                    UI.clearScreen();
                    UpdateInfo.updateInfo(student, auth, sc);
                    UI.waitForUserInput("Press Enter to go back to the main panel", sc);
                    break;

                case 8: 
                    UI.printMessage("Logging out...", "info");
                    return;

                default:
                    UI.printMessage("Invalid option. Please try again.", "error");
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
            }
        }
    }

    private static void showAdminMenu(Scanner sc, WalletManager walletManager) {
        while (true) {
            UI.clearScreen();
            String[] adminMenuOptions = {"View and Process Recharge Requests", "Show Menu Options", "View Analytics", "Logout"};
            UI.printBoxedMenu(adminMenuOptions, "Admin Dashboard");
    
            int choice = sc.nextInt();
            sc.nextLine();
    
            switch (choice) {
                case 1: 
                    UI.clearScreen();
                    walletManager.processRechargeRequests(sc);
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
                    break;
                case 2:
                    UI.clearScreen();
                    MenuManagement.showMenuOptions(sc); 
                    break;
                case 3: 
                    UI.clearScreen();
                    AdminAnalytics.showAnalyticsMenu(sc);
                    break;
                case 4:
                    UI.printMessage("Logging out...", "info");
                    return;
                default:
                    UI.printMessage("Invalid option. Please try again.", "error");
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
            }
        }
    }
    
}