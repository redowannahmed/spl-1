package token;

import UI.UI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MenuManagement
{
    private static final String MENU_FILE = "menu.txt";
    private static final String[] HEADERS = {"Day", "Breakfast", "Lunch", "Dinner"};
    private static String[][] menuData = new String[7][4];

    public static void loadMenus(String filename)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int index = 0;

            while ((line = reader.readLine()) != null && index < menuData.length)
            {
                String[] menuItems = line.split(";");
                // Ensure we don't exceed array bounds
                for (int i = 0; i < menuItems.length && i < menuData[index].length; i++) {
                    menuData[index][i] = menuItems[i] != null ? menuItems[i].trim() : "";
                }
                index++;
            }
            reader.close();
        }
        catch (IOException e)
        {
            System.out.println("Error reading menu file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void displayMenu ()
    {
        loadMenus(MENU_FILE);
        UI.printTable(HEADERS, menuData);
    }


    public static void showMenuOptions(Scanner sc) {
        while (true) {
            loadMenus(MENU_FILE);

            String[] mainMenuOptions = {"View Menu", "Edit menu", "Exit"};
            UI.printBoxedMenu(mainMenuOptions, "Menu Options");
            System.out.println();

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    UI.clearScreen();
                    displayMenu();
                    UI.waitForUserInput("Press enter to return to Menu Options", sc);
                    UI.clearScreen();
                    break;

                case 2:
                    editMenu(sc);
                    break;

                case 3:
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void editMenu(Scanner scanner) {
        String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        UI.printBoxedMenu(days, "Enter the day you want to edit");
        int day = scanner.nextInt();

        int dayIndex = day - 1;
        if (dayIndex < 0 || dayIndex >= days.length)
        {
            System.out.println("Invalid day entered. Please try again.");
            return;
        }

        String[] tokenList = {"Breakfast", "Lunch", "Dinner"};
        UI.printBoxedMenu(tokenList, "Enter the meal you want to edit");

        int mealChoice = scanner.nextInt();
        scanner.nextLine();

        if (mealChoice < 1 || mealChoice > 3) {
            System.out.println("Invalid meal choice. Please try again.");
            return;
        }

        System.out.println(UI.MINT_GREEN + "Current menu for " + HEADERS[mealChoice] + ": " + UI.RESET + menuData[dayIndex][mealChoice]);
        System.out.print(UI.MINT_GREEN + "Enter the new menu for " + HEADERS[mealChoice] + ": " + UI.RESET);
        String newMenu = scanner.nextLine();

        menuData[dayIndex][mealChoice] = newMenu;
        saveMenuData();
        System.out.println("Menu updated successfully!");
    }

    public static int findDayIndex(String day) {
        for (int i = 0; i < menuData.length; i++) {
            if (menuData[i][0] != null && menuData[i][0].equalsIgnoreCase(day)) {
                return i;
            }
        }
        return -1;
    }

    public static void saveMenuData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MENU_FILE))) {
            for (String[] row : menuData) {
                String[] safeRow = new String[row.length];
                for (int i = 0; i < row.length; i++) {
                    safeRow[i] = row[i] != null ? row[i] : "";
                }
                writer.write(String.join(";", safeRow));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving menu data: " + e.getMessage());
        }
    }
}