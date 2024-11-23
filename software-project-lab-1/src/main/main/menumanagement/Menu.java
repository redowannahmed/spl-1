package menumanagement;


import java.io.*;
import java.util.Scanner;

public class Menu {
    private static final String FILE_NAME = "C:\\Users\\wahid\\Downloads\\spl-1-main (2)\\spl-1-main\\software-project-lab-1\\menu.txt";
    private static final String[] DAYS = {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };
    private static final String[] MEAL_TYPES = {"Breakfast", "Lunch", "Dinner"};
    private String[][] menu; // 7 days x 3 meal types

    // Constructor to load the menu or initialize with defaults
    public Menu() {
        menu = new String[7][3];
        loadMenu();
    }

    // Load menu from file or initialize with default values
    private void loadMenu() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                for (int i = 0; i < 7; i++) {
                    String line = reader.readLine();
                    if (line != null) {
                        menu[i] = line.split(",", 3); // Split by commas
                    } else {
                        menu[i] = new String[]{"-", "-", "-"}; // Default empty values
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading the menu file. Initializing with defaults.");
                initializeDefaultMenu();
            }
        } else {
            initializeDefaultMenu();
        }
    }

    // Initialize menu with default values
    private void initializeDefaultMenu() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 3; j++) {
                menu[i][j] = "-"; // Default empty value
            }
        }
    }

    // Save the menu to file
    private void saveMenu() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < 7; i++) {
                writer.write(String.join(",", menu[i]));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving the menu: " + e.getMessage());
        }
    }

    // View the menu
    public void viewMenu() {
        System.out.println("MENU:");
        for (int i = 0; i < 7; i++) {
            System.out.println(DAYS[i] + ":");
            for (int j = 0; j < 3; j++) {
                System.out.println("  " + MEAL_TYPES[j] + ": " + menu[i][j]);
            }
        }
    }

    // Update the menu
    public void updateMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the day of the week (e.g., Monday): ");
        String day = scanner.nextLine().trim();
        int dayIndex = getDayIndex(day);
        if (dayIndex == -1) {
            System.out.println("Invalid day entered.");
            return;
        }

        System.out.print("Enter the meal type (Breakfast, Lunch, Dinner): ");
        String meal = scanner.nextLine().trim();
        int mealIndex = getMealIndex(meal);
        if (mealIndex == -1) {
            System.out.println("Invalid meal type entered.");
            return;
        }

        System.out.print("Enter the new food item: ");
        String food = scanner.nextLine().trim();

        menu[dayIndex][mealIndex] = food;
        saveMenu();
        System.out.println("Menu updated successfully!");
    }

    // Helper method to get day index
    private int getDayIndex(String day) {
        for (int i = 0; i < DAYS.length; i++) {
            if (DAYS[i].equalsIgnoreCase(day)) {
                return i;
            }
        }
        return -1;
    }

    // Helper method to get meal index
    private int getMealIndex(String meal) {
        for (int i = 0; i < MEAL_TYPES.length; i++) {
            if (MEAL_TYPES[i].equalsIgnoreCase(meal)) {
                return i;
            }
        }
        return -1;
    }

    // Main method for testing
    public static void main(String[] args) {
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu Options:");
            System.out.println("1. View Menu");
            System.out.println("2. Update Menu");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    menu.viewMenu();
                    break;

                case 2:
                    menu.updateMenu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
