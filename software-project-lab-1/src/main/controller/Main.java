package controller;

import java.util.Scanner;
import UI.UI;
import auth.Authentication;
import auth.AuthenticationHelper;
import auth.UpdateInfo;
import token.TokenManager;
import user.Admin;
import user.Student;

import user.User;
import wallet.WalletManager;
import menumanagement.MenuManager;

public class Main {
    public static void main(String[] args) {
        Authentication auth = new Authentication();
        WalletManager walletManager = new WalletManager(auth);
        AuthenticationHelper authHelper = new AuthenticationHelper(auth);
        TokenManager tokenHelper = new TokenManager(walletManager);
        MenuManager menuManager = new MenuManager();
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
                        UI.showLoading("Redirecting to your dashboard");
                        UI.clearScreen();

                        if (loggedInUser instanceof Admin) {
                            showAdminMenu(sc, walletManager, menuManager);
                        } else if (loggedInUser instanceof Student) {
                            showStudentMenu(sc, (Student) loggedInUser, auth, walletManager, tokenHelper, menuManager); // Pass menuManager to Student
                        }

                    } else {
                        UI.printMessage("User couldn't be found", "error");
                        UI.waitForUserInput("Press enter to go back to main panel", sc);
                    }
                    break;

                case 2:
                    UI.clearScreen();
                    authHelper.registerHelper(sc);
                    UI.waitForUserInput("press enter to go back to main panel", sc);
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

    private static void showStudentMenu(Scanner sc, Student student, Authentication auth, WalletManager walletManager, TokenManager tokenManager, MenuManager menuManager) {
        while (true) {
            UI.clearScreen();
            String[] studentMenuOptions = {
                    "Recharge Wallet", "View Balance", "Buy Token",
                    "View Pending Recharge Requests", "View Purchase History",
                    "View Menu", "Update Info", "Logout"
            };
            UI.printBoxedMenu(studentMenuOptions, "Student Dashboard");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    UI.clearScreen();
                    UI.printMessage("Enter recharge amount:", "info");
                    int amount = sc.nextInt();
                    sc.nextLine();

                    String slipId;

                    while (true) {
                        UI.printMessage("Enter slip ID:", "info");
                        slipId = sc.nextLine();
                        if (!walletManager.isDuplicateSlipId(slipId)) {
                            break;
                        }
                        UI.printMessage("Error: Slip ID already exists. Try again.", "error");
                    }

                    if (walletManager.requestRecharge(student.getUsername(), amount, slipId)) {
                        UI.printMessage("Recharge request submitted", "success");
                        UI.waitForUserInput("Press enter to go back to main panel", sc);
                    } else {
                        UI.printMessage("Error requesting recharge", "error");
                        UI.waitForUserInput("Press enter to go back to main panel", sc);
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

                case 6: // Call MenuManager's viewMenu method
                    UI.clearScreen();
                    menuManager.viewMenu();
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

    private static void showAdminMenu(Scanner sc, WalletManager walletManager, MenuManager menuManager) {
        while (true) {
            UI.clearScreen();
            String[] adminMenuOptions = {
                    "View and Process Recharge Requests",
                    "View Menu",
                    "Update Menu",
                    "Change Meal Time Labels",
                    "Logout"
            };
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
                    menuManager.viewMenu();
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
                    break;

                case 3:
                    UI.clearScreen();
                    menuManager.updateMenu(sc);
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
                    break;

                case 4:
                    UI.clearScreen();
                    menuManager.changeMealTimeNames(sc);
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
                    break;

                case 5:
                    UI.printMessage("Logging out...", "info");
                    return;

                default:
                    UI.printMessage("Invalid option. Please try again.", "error");
                    UI.waitForUserInput("Press enter to go back to main panel", sc);
            }
        }
    }
}