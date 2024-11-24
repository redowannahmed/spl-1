package menumanagement;

import UI.UI;

import java.io.*;
import java.util.Scanner;




public class MenuManager {
    private final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final String[] breakfastMenu = new String[7];
    private final String[] lunchMenu = new String[7];
    private final String[] dinnerMenu = new String[7];
    private final String fileName = "spl-1-main\\menu.txt";


    private String breakfastLabel = "Breakfast";
    private String lunchLabel = "Lunch";
    private String dinnerLabel = "Dinner";

    public MenuManager() {
        loadMenuFromFile();
    }

    private void loadMenuFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String labelsLine = reader.readLine();
            if (labelsLine != null) {
                String[] labels = labelsLine.split(",");
                breakfastLabel = labels[0].trim();
                lunchLabel = labels[1].trim();
                dinnerLabel = labels[2].trim();
            }


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

            writer.write(breakfastLabel + "," + lunchLabel + "," + dinnerLabel);
            writer.newLine();


            for (int i = 0; i < 7; i++) {
                writer.write(breakfastMenu[i] + "," + lunchMenu[i] + "," + dinnerMenu[i]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving menu to file: " + e.getMessage());
        }
    }

    public void viewMenu() {
        System.out.println(UI.colorText("====================================", UI.BOX_COLOR));
        System.out.println(UI.colorText("               MENU                 ", UI.TITLE_COLOR));
        System.out.println(UI.colorText("====================================", UI.BOX_COLOR));


        System.out.printf("%-10s | %-15s | %-15s | %-15s %n",
                "Day", breakfastLabel, lunchLabel, dinnerLabel);
        System.out.println("----------------------------------------------------------");


        for (int i = 0; i < days.length; i++) {
            System.out.printf("%-10s | %-15s | %-15s | %-15s %n",
                    days[i], breakfastMenu[i], lunchMenu[i], dinnerMenu[i]);
        }

        System.out.println(UI.colorText("====================================", UI.BOX_COLOR));
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
                for (int i = 0; i < days.length; i++) {
                    if (days[i].equalsIgnoreCase(day)) {
                        dayIndex = i;
                        break;
                    }
                }

                if (dayIndex == -1) {
                    System.out.println(UI.colorText("Invalid day name. Please try again.", UI.ERROR_COLOR));
                    continue;
                }


                if (time.equalsIgnoreCase(breakfastLabel)) {
                    breakfastMenu[dayIndex] = item;
                } else if (time.equalsIgnoreCase(lunchLabel)) {
                    lunchMenu[dayIndex] = item;
                } else if (time.equalsIgnoreCase(dinnerLabel)) {
                    dinnerMenu[dayIndex] = item;
                } else {
                    System.out.println(UI.colorText("Invalid time. Must match one of the meal time labels: '"
                            + breakfastLabel + "', '" + lunchLabel + "', or '" + dinnerLabel + "'.", UI.ERROR_COLOR));
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



    public void changeMealTimeNames(Scanner sc) {
        System.out.println("Enter the current meal time name and the new name, separated by a comma (e.g., Breakfast,Brunch):");
        String input = sc.nextLine().trim();

        if (!input.contains(",")) {
            System.out.println("Invalid input format. Please use the format: OldName,NewName.");
            return;
        }

        String[] parts = input.split(",", 2);
        String oldName = parts[0].trim();
        String newName = parts[1].trim();

        if (oldName.equalsIgnoreCase(breakfastLabel)) {
            breakfastLabel = newName;
        } else if (oldName.equalsIgnoreCase(lunchLabel)) {
            lunchLabel = newName;
        } else if (oldName.equalsIgnoreCase(dinnerLabel)) {
            dinnerLabel = newName;
        } else {
            System.out.println("Error: Meal time name \"" + oldName + "\" not found.");
            return;
        }

        saveMenuToFile();
        System.out.println("Meal time name successfully changed from \"" + oldName + "\" to \"" + newName + "\".");
    }
}