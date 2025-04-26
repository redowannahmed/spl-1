package analytics;

import UI.UI;
import java.io.*;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class AdminAnalytics {

    public static void showAnalyticsMenu(Scanner scanner) {
        while (true) {
            UI.clearScreen();
            String[] analyticsOptions = {
                "View mealwise analytics for last 4 weeks", "View monthly analytics (total + meal-wise breakdown)",
                "Back"};

            UI.printBoxedMenu(analyticsOptions, "View Analytics");

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
            String[] weeklyanalyticsStr = {
                "BREAKFAST", "LUNCH", "DINNER",
                "Back"};

            UI.printBoxedMenu(weeklyanalyticsStr, "Weekly Analytics");

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
                    displayWeeklyAnalyticsAlternative("BREAKFAST");
                    break;
                case 2:
                    displayWeeklyAnalyticsAlternative("LUNCH");
                    break;
                case 3:
                    displayWeeklyAnalyticsAlternative("DINNER");
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


    public static void displayWeeklyAnalyticsAlternative(String mealType) {
            Map<String, Integer> weekSales = new LinkedHashMap<>();
            Map<String, String> weekDateRanges = new LinkedHashMap<>();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter readableDateFormat = DateTimeFormatter.ofPattern("MMM d, yyyy");

            // Get today's date
            LocalDate today = LocalDate.now();
            List<String> weeksList = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                LocalDate weekEnd = today.minusWeeks(i); // Sunday of the week
                LocalDate weekStart = weekEnd.minusDays(6); // Monday of that week

                String weekKey = String.format("Week %d", i + 1);
                String dateRange = String.format("(%s - %s)",
                        weekStart.format(readableDateFormat),
                        weekEnd.format(readableDateFormat));

                weeksList.add(weekKey);
                weekSales.put(weekKey, 0);
                weekDateRanges.put(weekKey, dateRange);
            }

            try {
                List<String> lines = Files.readAllLines(Paths.get("purchaseHistory.txt"));
                for (String line : lines) {
                    String[] parts = line.split(",");
                    if (parts.length < 3) continue;

                    String meal = parts[1].trim();
                    String dateStr = parts[2].trim();

                    if (meal.equalsIgnoreCase(mealType)) {
                        LocalDate date = LocalDate.parse(dateStr, dateFormat);
                        for (String week : weeksList) {
                            String dateRange = weekDateRanges.get(week);
                            String[] dates = dateRange.substring(1, dateRange.length() - 1).split(" - ");
                            LocalDate start = LocalDate.parse(dates[0], readableDateFormat);
                            LocalDate end = LocalDate.parse(dates[1], readableDateFormat);

                            if (!date.isBefore(start) && !date.isAfter(end)) {
                                weekSales.put(week, weekSales.get(week) + 1);
                                break;
                            }
                        }
                    }
                }

                System.out.println("\nWeekly Sales Data for " + mealType + ":");
                for (String week : weeksList) {
                    String dateRange = weekDateRanges.get(week);
                    System.out.printf("%s %s -> %d meals%n", week, dateRange, weekSales.get(week));
                }

                if (weekSales.values().stream().allMatch(s -> s == 0)) {
                    System.out.println("No sales data available for " + mealType + " in the last 4 weeks.");
                    return;
                }

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
    
        for (int y = maxSales; y >= 0; y -= scaleFactor) {
            System.out.printf("%4d | ", y);
    
            for (String week : weeks) {
                int value = sales.getOrDefault(week, 0);
                if (value >= y) {
                    System.out.print("   *   "); // Adjusted spacing
                } else {
                    System.out.print("       "); // Matched empty spacing
                }
            }
            System.out.println();
        }
    
        // Print the x-axis
        System.out.println("    " + "-".repeat(weeks.size() * 8));
    
        System.out.print("     ");
        for (String week : weeks) {
            System.out.printf("%-8s", week); // Ensured proper spacing
        }
        System.out.println();
    }
    

    public static void printGraph(Map<?, Integer> sales, String xLabel, String title) {
        int maxSales = sales.values().stream().max(Integer::compare).orElse(1);
        int scaleFactor = (maxSales > 10) ? maxSales / 10 : 1;
    
        System.out.println("\n" + title + "\n");
    
        // Print the y-axis and graph
        for (int y = maxSales; y >= 0; y -= scaleFactor) {
            System.out.printf("%4d | ", y);
    
            for (int value : sales.values()) {
                if (value >= y) {
                    System.out.print("  *  "); // Adjusted spacing for centering
                } else {
                    System.out.print("     "); // Adjusted spacing for centering
                }
            }
            System.out.println();
        }
    
        // Print the x-axis separator
        System.out.print("      ");
        for (int i = 0; i < sales.size(); i++) {
            System.out.print("-----");
        }
        System.out.println();
    
        // Print the x-axis labels
        System.out.print("       ");
        for (Object key : sales.keySet()) {
            System.out.printf("%-5s", key.toString()); // Adjusted spacing for centering
        }
        System.out.println();
    }
}
