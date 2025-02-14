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
        
        do {
            System.out.println(UI.MINT_GREEN + "Enter username:" + UI.RESET);
            username = sc.nextLine().trim();
            if (username.isEmpty()) {
                UI.printMessage("username cannot be empty", "error");
            }
        } while (username.isEmpty());
        
        do {
            System.out.println(UI.MINT_GREEN + "Enter password:" + UI.RESET);
            password = sc.nextLine().trim();
            if (password.isEmpty()) {
                UI.printMessage("Password cannot be blank. Please enter a valid password.", "error");
            }
        } while (password.isEmpty());
    
        User user = auth.login(username, password);
        if (user != null) {
            return user;
        } else {
            UI.printMessage("Login failed. Invalid username or password.", "error");
            UI.clearScreen();
        }
        return null; 
    }
    
    public void registerHelper(Scanner sc) {
        String name;
        String username;
        int id;
        String password;
        
        while (true) {
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
                UI.printMessage("student has been registered successfully", "success");
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