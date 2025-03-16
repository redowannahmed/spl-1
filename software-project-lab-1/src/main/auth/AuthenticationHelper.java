package auth;
import java.util.*;

import UI.UI;
import exceptions.DuplicateIDException;
import exceptions.DuplicateUsernameException;
import user.User;

public class AuthenticationHelper {
    private Authentication auth;

    public AuthenticationHelper(Authentication auth) {
        this.auth = auth;
    }

    public User loginHelper(Scanner sc) {
        String username;
        String password;
        User user = null;
        boolean tryAgain = true;
    
        while (tryAgain) {
            UI.clearScreen();
    
            System.out.println(UI.SLATE_BLUE + UI.BOLD + "======================================" + UI.RESET);
            System.out.println(UI.SLATE_BLUE + UI.BOLD + "   ENTER YOUR CREDENTIALS TO LOG IN   " + UI.RESET);
            System.out.println(UI.SLATE_BLUE + UI.BOLD + "======================================" + UI.RESET);
            System.out.println();
    
            do {
                UI.printMessage("Enter username:", "info");
                System.out.print(UI.MINT_GREEN);
                username = sc.nextLine().trim();
                System.out.print(UI.RESET);
                if (username.isEmpty()) {
                    UI.printMessage("Username cannot be empty", "error");
                }
            } while (username.isEmpty());
    
            do {
                UI.printMessage("Enter password:", "info");
                System.out.print(UI.MINT_GREEN);
                password = sc.nextLine().trim();
                System.out.print(UI.RESET);
                if (password.isEmpty()) {
                    UI.printMessage("Password cannot be blank. Please enter a valid password.", "error");
                }
            } while (password.isEmpty());
    
            user = auth.login(username, password);
            if (user != null) {
                UI.printMessage("LOGIN SUCCESSFUL! WELCOME BACK.", "success");
                System.out.println();
                return user;
            } else {
                System.out.println();
                UI.printMessage("LOGIN FAILED. INVALID USERNAME OR PASSWORD.", "error");
    
                System.out.println();
                UI.printMessage("Would you like to try again? (yes/no)", "info");
                System.out.print(UI.MINT_GREEN);
                String choice = sc.nextLine().trim().toLowerCase();
                System.out.print(UI.RESET);
    
                if (!choice.equals("yes")) {
                    tryAgain = false;
                }
            }
        }
        return null;
    }
    
    
    public void registerHelper(Scanner sc) {
        String name;
        String username;
        int id;
        String password;

        while (true) {
            System.out.println(UI.SLATE_BLUE + UI.BOLD + "======================================" + UI.RESET);
            System.out.println(UI.SLATE_BLUE + UI.BOLD + "   ENTER YOUR CREDENTIALS TO REGISTER   " + UI.RESET);
            System.out.println(UI.SLATE_BLUE + UI.BOLD + "======================================" + UI.RESET);
            System.out.println();
            do {
                UI.printMessage("Enter name:", "info");
                name = sc.nextLine().trim();
                if (name.isEmpty()) {
                    UI.printMessage("Name cannot be blank. Please enter a valid name.", "error");
                }
            } while (name.isEmpty());
    
            do {
                UI.printMessage("Enter username:", "info");
                username = sc.nextLine().trim();
                if (username.isEmpty()) {
                    UI.printMessage("Username cannot be blank. Please enter a valid username.", "error");
                }
            } while (username.isEmpty());
    
            UI.printMessage("Enter Student ID:", "info");
    
            while (true) {
                if (sc.hasNextInt()) {
                    id = sc.nextInt();
                    sc.nextLine(); 
                    break;
                } else {
                    UI.printMessage("Invalid ID. Please enter a numeric ID:", "error");
                    sc.next(); 
                }
            }
    
            do {
                UI.printMessage("Enter password:", "info");
                password = sc.nextLine().trim();
                
                if (password.isEmpty()) {
                    UI.printMessage("Password cannot be left blank.", "error");
                } else if (!Authentication.isValidPassword(password)) {
                    UI.printMessage("Password must contain at least 6 characters, including one uppercase letter, one lowercase letter, and one number.", "error");
                    password = ""; // Reset password to prompt again
                }
            } while (password.isEmpty());
            
    
            try {
                auth.registerStudent(name, username, id, password);
                UI.printMessage("registration successful", "success");
                System.out.println();
                break; 
            } catch (DuplicateUsernameException | DuplicateIDException e) {
                UI.printMessage("Registration failed: " + e.getMessage(), "error");
                
                UI.printMessage("Do you want to retry? (Y/N)", "info");
                String retry = sc.nextLine().trim().toUpperCase();
                UI.clearScreen();

                if (retry.equals("N")) {
                    break;
                }
            }
        }
    }    
    
}