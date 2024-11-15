package UI;
import java.util.Scanner;

public class UI {

    public static void clearScreen() {
        // Clears the console (platform-dependent behavior)
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printHeader(String title) {
        System.out.println("+-----------------------------------------+");
        System.out.printf("| %-39s |\n", centerText(title, 39));
        System.out.println("+-----------------------------------------+");
    }

    public static void printMessage(String message) {
        System.out.println("| " + centerText(message, 39) + " |");
        System.out.println("+-----------------------------------------+");
    }

    public static String centerText(String text, int width) {
        int padding = Math.max(0, (width - text.length()) / 2);
        return " ".repeat(padding) + text + " ".repeat(padding);
    }

    public static int getMenuChoice(String[] options, Scanner sc) {
        printHeader("Menu Options");
        for (int i = 0; i < options.length; i++) {
            System.out.printf("| %2d. %-35s |\n", i + 1, options[i]);
        }
        System.out.println("+-----------------------------------------+");
        System.out.print("Enter your choice: ");
        
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            sc.next();
        }
        return sc.nextInt();
    }

    public static void waitForUser(Scanner sc) {
        System.out.println("Press Enter to continue...");
        sc.nextLine();
    }
}
