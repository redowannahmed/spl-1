package Menu;

import java.io.*;
import java.util.Scanner;
import UI.UI;
public class MenuManager {
    private final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final String[] breakfastMenu = new String[7];
    private final String[] lunchMenu = new String[7];
    private final String[] dinnerMenu = new String[7];
    private final String fileName = "C:\\Users\\wahid\\Downloads\\spl-1-main (2)\\spl-1-main\\software-project-lab-1\\menu.txt";

    public MenuManager() {
        loadMenuFromFile();
    }

    private void loadMenuFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            for (int i = 0; i < 7; i++) {
                String line = reader.readLine();
                if (line != null) {
                    String[] parts = line.split(",");
                    breakfastMenu[i] = parts[0].trim();
                    lunchMenu[i] = parts[1].trim();
                    dinnerMenu[i] = parts[2].trim();
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading menu from file: " + e.getMessage());
        }
    }

    private void saveMenuToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < 7; i++) {
                writer.write(breakfastMenu[i] + "," + lunchMenu[i] + "," + dinnerMenu[i]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving menu to file: " + e.getMessage());
        }
    }

    public void viewMenu() {
        System.out.println();
        System.out.println(UI.colorText("===================================", UI.BOX_COLOR));
        System.out.println(UI.colorText("              MENU                 ", UI.TITLE_COLOR));
        System.out.println(UI.colorText("===================================", UI.BOX_COLOR));

        for (int i = 0; i < 7; i++) {
            // Printing day header in white
            System.out.printf(UI.colorText("Day: %s", UI.MENU_OPTION_COLOR), days[i]);
            System.out.println(UI.colorText("-----------------------------------", UI.BOX_COLOR));
            // Printing breakfast, lunch, and dinner menus in white
            System.out.printf(UI.colorText("|   Breakfast: %-20s |", UI.MENU_OPTION_COLOR), breakfastMenu[i]);
            System.out.printf(UI.colorText("|   Lunch:     %-20s |", UI.MENU_OPTION_COLOR), lunchMenu[i]);
            System.out.printf(UI.colorText("|   Dinner:    %-20s |", UI.MENU_OPTION_COLOR), dinnerMenu[i]);
            System.out.println(UI.colorText("-----------------------------------", UI.BOX_COLOR));
        }

        System.out.println(UI.colorText("===================================", UI.BOX_COLOR));
    }

    public void updateMenu(Scanner sc) {
        while (true) {
            try {
                System.out.println(UI.colorText("Enter the update in the format: <Day>,<Time>,<Item>", UI.MENU_OPTION_COLOR));
                String input = sc.nextLine();
                String[] parts = input.split(",");
                if (parts.length != 3) {
                    System.out.println(UI.colorText("Invalid input format. Please try again.", UI.ERROR_COLOR));
                    continue;
                }

                String day = parts[0].trim();
                String time = parts[1].trim().toLowerCase();
                String item = parts[2].trim();

                int dayIndex = -1;
                for (int i=0;i<days.length;i++) {
                    if (days[i].equalsIgnoreCase(day)) {
                        dayIndex = i;
                        break;
                    }
                }

                if (dayIndex == -1) {
                    System.out.println(UI.colorText("Invalid day name. Please try again.", UI.ERROR_COLOR));
                    continue;
                }

                switch (time) {
                    case "breakfast":
                        breakfastMenu[dayIndex] = item;
                        break;
                    case "lunch":
                        lunchMenu[dayIndex] = item;
                        break;
                    case "dinner":
                        dinnerMenu[dayIndex] = item;
                        break;
                    default:
                        System.out.println(UI.colorText("Invalid time. Must be 'breakfast', 'lunch', or 'dinner'. Please try again.", UI.ERROR_COLOR));
                        continue;
                }

                saveMenuToFile();
                System.out.println(UI.colorText("Menu updated successfully!", UI.SUCCESS_COLOR));
                break;
            } catch (Exception e) {
                System.out.println(UI.colorText("An error occurred. Please try again.", UI.ERROR_COLOR));
            }
        }
    }
}
