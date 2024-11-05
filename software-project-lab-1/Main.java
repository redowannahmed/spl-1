import java.util.*;

public class Main {
    public static void main(String[] args) {
        Authentication auth = new Authentication();
        WalletManager walletManager = new WalletManager(auth);
        AuthenticationHelper authHelper = new AuthenticationHelper(auth);
        TokenManager tokenHelper = new TokenManager(walletManager);
        Scanner sc = new Scanner(System.in);

        while (true) {
            UI.clearScreen();
            System.out.println("\nChoose an option: ");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
    
            int choice = sc.nextInt();
            sc.nextLine();
    
            switch (choice) {
                case 1:
                    User loggedInUser = authHelper.loginHelper(sc);
                    if (loggedInUser != null) {
                        UI.waitForUser(sc); 
                        UI.clearScreen();  
                        
                        if (loggedInUser instanceof Admin) {
                            showAdminMenu(sc, walletManager);
                        } else if (loggedInUser instanceof Student) {
                            showStudentMenu(sc, (Student) loggedInUser, walletManager, tokenHelper);
                        }
                    }
                    break;
                case 2:
                    authHelper.registerHelper(sc);
                    UI.waitForUser(sc);  // Pause for registration success or error message
                    UI.clearScreen();    // Clear screen after user acknowledges
                    break;
                case 3:
                    System.out.println("Exiting program.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    UI.waitForUser(sc);
                    UI.clearScreen();
            }
        }
    }

    private static void showStudentMenu(Scanner sc, Student student, WalletManager walletManager, TokenManager tokenManager) {
        while (true) {
            UI.clearScreen();
            System.out.println("\nStudent Menu:");
            System.out.println("1. Recharge Wallet");
            System.out.println("2. View Balance");
            System.out.println("3. Buy Token");
            System.out.println("4. View your pending recharge requests");
            System.out.println("5. View your purchase history");
            System.out.println("6. View Menu");
            System.out.println("7. Update Info");
            System.out.println("8. Exit");
    
            int choice = sc.nextInt();
            sc.nextLine();
    
            switch (choice) {
                case 1:
                    System.out.println("Enter recharge amount:");
                    int amount = sc.nextInt();
                    sc.nextLine();
    
                    String slipId;
                    while (true) {
                        System.out.println("Enter slip ID:");
                        slipId = sc.nextLine();
                        if (!walletManager.isDuplicateSlipId(slipId)) {
                            break;
                        }
                        System.out.println("Error: Slip ID already exists. Please enter a unique slip ID.");
                    }
    
                    walletManager.requestRecharge(student.getUsername(), amount, slipId);
                    UI.waitForUser(sc);  // Pause after recharge request to show confirmation
                    UI.clearScreen();    // Clear screen after confirmation
                    break;
                case 2:
                    System.out.println("Current Balance: " + student.getWallet().getBalance());
                    UI.waitForUser(sc);  // Pause to allow the user to view the balance
                    UI.clearScreen();    // Clear screen after showing balance
                    break;
                case 3:
                    UI.clearScreen();    // Clear screen before showing token options
                    tokenManager.showTokenBuyingOptions(student, sc);
                    UI.waitForUser(sc);  // Pause to show token purchase confirmation
                    UI.clearScreen();    // Clear screen before returning to main student menu
                    break;
                case 4:
                    UI.clearScreen();
                    System.out.println("Pending Recharge Requests:");
                    walletManager.viewPendingRequests(student.getUsername());
                    UI.waitForUser(sc);  // Pause to allow user to view pending requests
                    UI.clearScreen();    // Clear screen after showing pending requests
                    break;
                case 5:
                    System.out.println("feature under development");
                    UI.waitForUser(sc);
                    UI.clearScreen();
                    break;
                case 6: 
                    System.out.println("feature under development");
                    UI.waitForUser(sc);
                    UI.clearScreen();
                    break;
                case 7:
                    System.out.println("feature under development");
                    UI.waitForUser(sc);
                    UI.clearScreen();
                    break;
                case 8:
                    System.out.println("Logging out...");
                    UI.waitForUser(sc);  // Pause for logout message
                    UI.clearScreen();    // Clear screen after logout
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    UI.waitForUser(sc);  // Pause for invalid option message
                    UI.clearScreen();    // Clear screen to show menu again
            }
        }
    }
    
    
    
    private static void showAdminMenu(Scanner sc, WalletManager walletManager) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View and Process Recharge Requests");
            System.out.println("2. Logout");
    
            int choice = sc.nextInt();
            sc.nextLine();
    
            switch (choice) {
                case 1:
                    walletManager.processRechargeRequests(sc);
                    UI.waitForUser(sc); 
                    UI.clearScreen();
                    break;
                case 2:
                    System.out.println("Logging out...");
                    UI.waitForUser(sc); 
                    return;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }
}
