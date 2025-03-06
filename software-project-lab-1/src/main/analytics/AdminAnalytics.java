package analytics;

import UI.UI;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AdminAnalytics {

    public static void showAnalyticsMenu(Scanner scanner) {
        while (true) {
            UI.clearScreen();
            System.out.println("\n=== Meal Sales Analytics ===");
            System.out.println("1. View last 5 weeks analytics");
            System.out.println("2. View monthly analytics (total + meal-wise breakdown)");
            System.out.println("3. Back");

            System.out.print("Select an option: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                UI.printMessage("Invalid input. Please enter a number between 1 and 3.", "error");
                continue;
            }

            switch (choice) {
                case 1:
                    showWeeklyAnalytics(scanner);
                    break;
                case 2:
                    showMonthlyAnalytics(scanner);
                    break;
                case 3:
                    UI.printMessage("Returning to admin panel...", "info");
                    return;
                default:
                    UI.printMessage("Invalid choice. Please enter a number between 1 and 3.", "error");
            }
        }
    }

    public static void showWeeklyAnalytics(Scanner scanner) {
        while (true) {
            UI.clearScreen();
            System.out.println("\n=== Weekly Meal Sales Analytics ===");
            System.out.println("1. Breakfast");
            System.out.println("2. Lunch");
            System.out.println("3. Dinner");
            System.out.println("4. Back");

            System.out.print("Select a meal type: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                UI.printMessage("Invalid input. Please enter a number between 1 and 4.", "error");
                continue;
            }

            switch (choice) {
                case 1:
                    displayWeeklyAnalytics("BREAKFAST");
                    break;
                case 2:
                    displayWeeklyAnalytics("LUNCH");
                    break;
                case 3:
                    displayWeeklyAnalytics("DINNER");
                    break;
                case 4:
                    UI.printMessage("Returning to analytics menu...", "info");
                    return;
                default:
                    UI.printMessage("Invalid choice. Please enter a number between 1 and 4.", "error");
            }

            UI.waitForUserInput("Press enter to continue", scanner);
        }
    }

    public static void showMonthlyAnalytics(Scanner scanner) {
        UI.clearScreen();
        System.out.println("\n=== Monthly Sales Analytics ===");

        displayMonthlyAnalytics();

        UI.waitForUserInput("Press enter to return", scanner);
    }

    public static void displayMonthlyAnalytics() {
        Map<Integer, Integer> monthlySales = new TreeMap<>();
        Map<Integer, Map<String, Integer>> monthlyMealWiseSales = new TreeMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            List<String> lines = Files.readAllLines(Paths.get("purchaseHistory.txt"));
            Calendar cal = Calendar.getInstance();

            for (int i = 1; i <= 12; i++) {
                monthlySales.put(i, 0);
                monthlyMealWiseSales.put(i, new HashMap<>());
            }

            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String mealType = parts[1].trim();
                String dateStr = parts[2].trim();

                Date date = dateFormat.parse(dateStr);
                cal.setTime(date);
                int saleMonth = cal.get(Calendar.MONTH) + 1;

                monthlySales.put(saleMonth, monthlySales.get(saleMonth) + 1);
                Map<String, Integer> mealSalesForMonth = monthlyMealWiseSales.get(saleMonth);
                mealSalesForMonth.put(mealType, mealSalesForMonth.getOrDefault(mealType, 0) + 1);
            }

            printGraph(monthlySales, "Months", "Yearly Meal Sales");

            System.out.println("\n=== Meal-wise Breakdown for Each Month ===");
            for (int month = 1; month <= 12; month++) {
                System.out.println("\nMonth " + month + ":");
                Map<String, Integer> mealSales = monthlyMealWiseSales.get(month);
                for (String meal : mealSales.keySet()) {
                    System.out.println("- " + meal + ": " + mealSales.get(meal) + " tokens");
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading purchase history file.");
        } catch (Exception e) {
            System.out.println("Error parsing dates.");
        }
    }

    public static void displayWeeklyAnalytics(String mealType) {
        Map<String, Integer> weekSales = new LinkedHashMap<>();
        Map<String, String> weekDateRanges = new LinkedHashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat readableDateFormat = new SimpleDateFormat("MMM d, yyyy");
    
        Calendar cal = Calendar.getInstance();
    
        try {
            // Today's date
            Date today = new Date();
            cal.setTime(today);
    
            // Track the last 4 rolling weeks (each week is 7 days before the current date)
            List<String> weeksList = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                // Calculate the end of the week (today - i * 7 days)
                cal.setTime(today);
                cal.add(Calendar.DATE, -i * 7);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                Date weekEnd = cal.getTime();
    
                // Calculate the start of the week (6 days before the end date)
                cal.add(Calendar.DATE, -6);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                Date weekStart = cal.getTime();
    
                // Format the week range
                String weekKey = String.format("Week %d", i + 1);
                String dateRange = String.format("(%s - %s)",
                        readableDateFormat.format(weekStart),
                        readableDateFormat.format(weekEnd));
    
                weeksList.add(weekKey);
                weekSales.put(weekKey, 0);
                weekDateRanges.put(weekKey, dateRange);
            }
    
            // Read purchase history file
            List<String> lines = Files.readAllLines(Paths.get("purchaseHistory.txt"));
    
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
    
                String meal = parts[1].trim();
                String dateStr = parts[2].trim();
    
                if (meal.equalsIgnoreCase(mealType)) {
                    Date date = dateFormat.parse(dateStr);
    
                    // Check which week the date falls into
                    for (String week : weeksList) {
                        String dateRange = weekDateRanges.get(week);
                        String[] dates = dateRange.substring(1, dateRange.length() - 1).split(" - ");
                        Date start = readableDateFormat.parse(dates[0]);
                        Date end = readableDateFormat.parse(dates[1]);
    
                        // ✅ Fix: Inclusive date range check (includes both start and end date)
                        if (!date.before(start) && !date.after(end)) {
                            weekSales.put(week, weekSales.get(week) + 1);
    
                            // Optional Debug: Show which meal maps to which week
                            System.out.printf("[DEBUG] Meal: %s on %s falls into %s %s%n",
                                    meal, dateStr, week, dateRange);
    
                            break;
                        }
                    }
                }
            }
    
            // Debug printout with exact date ranges
            System.out.println("\n[DEBUG] Weekly Sales Data for " + mealType + ":");
            for (String week : weeksList) {
                String dateRange = weekDateRanges.get(week);
                System.out.printf("%s %s -> %d meals%n", week, dateRange, weekSales.get(week));
            }
    
            if (weekSales.values().stream().allMatch(s -> s == 0)) {
                System.out.println("No sales data available for " + mealType + " in the last 4 weeks.");
                return;
            }
    
            // Call to your existing graph method (if applicable)
            printGraphWithWeekNumbersOnly(weekSales, weeksList, "Weeks", "Meal Sales (Last 4 Weeks)");
    
        } catch (IOException e) {
            System.out.println("Error reading purchase history file.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error parsing dates.");
        }
    }
    
    
    // Updated printGraphWithWeekNumbersOnly method
    public static void printGraphWithWeekNumbersOnly(Map<String, Integer> sales, List<String> weeks, String xLabel, String title) {
        int maxSales = sales.values().stream().max(Integer::compare).orElse(1);
        int scaleFactor = (maxSales > 10) ? maxSales / 10 : 1;
    
        System.out.println("\n" + title + "\n");
    
        // Print the y-axis and bars
        for (int y = maxSales; y >= 0; y -= scaleFactor) {
            System.out.printf("%4d | ", y);
    
            for (String week : weeks) {
                int value = sales.getOrDefault(week, 0);
                if (value >= y) {
                    System.out.print("  *   ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }
    
        // Print the x-axis
        System.out.println("      -----------------------------------------");
        System.out.print("       ");
        for (String week : weeks) {
            System.out.printf("%-7s", week); // Add proper spacing between week labels
        }
        System.out.println();
    }
    

    public static void printGraph(Map<?, Integer> sales, String xLabel, String title) {
        int maxSales = sales.values().stream().max(Integer::compare).orElse(1);
        int scaleFactor = (maxSales > 10) ? maxSales / 10 : 1;

        System.out.println("\n" + title + "\n");

        for (int y = maxSales; y >= 0; y -= scaleFactor) {
            System.out.printf("%4d | ", y);

            for (int value : sales.values()) {
                if (value >= y) {
                    System.out.print("  *   ");
                } else {
                    System.out.print("      ");
                }
            }
            System.out.println();
        }

        System.out.println("      -----------------------------------------");
        System.out.print("       ");
        for (Object key : sales.keySet()) {
            System.out.printf("%-6s", key.toString());
        }
        System.out.println();
    }
}
