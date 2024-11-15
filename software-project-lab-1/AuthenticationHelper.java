import java.util.*;

public class AuthenticationHelper {
    private Authentication auth;

    public AuthenticationHelper(Authentication auth) {
        this.auth = auth;
    }

    public User loginHelper(Scanner sc) {
        String username;
        String password;
        
        do {
            System.out.println("Enter username:");
            username = sc.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Username cannot be blank. Please enter a valid username.");
            }
        } while (username.isEmpty());
        
        do {
            System.out.println("Enter password:");
            password = sc.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Password cannot be blank. Please enter a valid password.");
            }
        } while (password.isEmpty());
    
        User user = auth.login(username, password);
        if (user != null) {
            return user; // Return user if login is successful
        } else {
            System.out.println("Login failed. Invalid username or password.");
            UI.waitForUser(sc); // Wait for user acknowledgment before clearing screen
            UI.clearScreen();
        }
        return null; // Return null if login fails
    }
    
    
    public void registerHelper(Scanner sc) {
        String name;
        String username;
        int id;
        String password;
    
        do {
            System.out.println("Enter name:");
            name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be blank. Please enter a valid name.");
            }
        } while (name.isEmpty());
    
        do {
            System.out.println("Enter username:");
            username = sc.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Username cannot be blank. Please enter a valid username.");
            }
        } while (username.isEmpty());
    
        System.out.println("Enter student ID:");
        while (true) {
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine(); // Clear the newline character
                break;
            } else {
                System.out.println("Invalid ID. Please enter a numeric ID:");
                sc.next(); // Consume invalid input
            }
        }
    
        do {
            System.out.println("Enter password:");
            password = sc.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Password cannot be blank. Please enter a valid password.");
            }
        } while (password.isEmpty());
    
        try {
            auth.registerStudent(name, username, id, password);
        } catch (DuplicateUsernameException | DuplicateIDException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
    
}
