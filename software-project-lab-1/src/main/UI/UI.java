package UI;

import java.util.Scanner;

public class UI {
    Scanner sc = new Scanner(System.in);

    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String OFF_WHITE = "\033[38;2;224;224;224m";
    private static final String BOLD_WHITE = "\u001B[1;37m";
    private static final String BRIGHT_CYAN = "\u001B[1;36m";
    private static final String BRIGHT_WHITE = "\u001B[1;97m";
    public static final String MINT_GREEN = "\033[38;2;165;214;167m";
    public static final String EMERALD_GREEN = "\033[38;2;102;187;106m";
    public static final String CORAL_RED = "\033[38;2;239;83;80m";
    public static final String SKY_BLUE = "\033[38;2;79;195;247m";
    public static final String AMBER_YELLOW = "\033[38;2;255;202;40m";
    public static final String SLATE_BLUE = "\033[38;2;92;107;192m";
    public static final String LAVENDER_BLUE = "\033[38;2;121;134;203m";
    public static final String LIGHT_GRAY = "\033[38;2;176;190;197m";
    public static final String DARK_GRAY = "\033[38;2;120;144;156m";
    public static final String MUTED_TEAL = "\033[38;2;77;182;172m";
    public static final String MAGENTA = "\033[0;35m";
    public static final String BOLD_BRIGHT_WHITE = "\033[1;97m";


    public static final String BOLD = "\033[1m";
    public static final String UNDERLINE = "\033[4m";

   public static final String BOX_COLOR = MUTED_TEAL;
    public static final String TITLE_COLOR = EMERALD_GREEN;
   public static final String MENU_OPTION_COLOR = BRIGHT_WHITE;
    public static final String PROMPT_COLOR = MINT_GREEN;
    private static final String INPUT_COLOR = MINT_GREEN;
    private static final String HEADER_COLOR = SLATE_BLUE;

    public static final String SUCCESS_COLOR = EMERALD_GREEN;
    public static final String ERROR_COLOR = CORAL_RED;
    public static final String INFO_COLOR = SKY_BLUE;
    public static final String WARNING_COLOR = AMBER_YELLOW;

    public static final String PRIMARY_TEXT_COLOR = MINT_GREEN;
    public static final String SECONDARY_TEXT_COLOR = LIGHT_GRAY;

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Unable to clear screen.");
        }
    }

    public static void showLoading(String message) {
        System.out.print(colorText(message, INFO_COLOR));
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(250);
                System.out.print(colorText(".", INFO_COLOR));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    public static String boldText(String text) {
        return BOLD + text + RESET;
    }

    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        int extraSpace = (width - text.length()) % 2;
        return " ".repeat(padding) + text + " ".repeat(padding + extraSpace);
    }

    public static String colorText(String text, String color) {
        return color + text + RESET;
    }

    public static void printBoxedMenu(String[] options, String title) {
        int maxLength = title.length();

        for (String option : options) {
            maxLength = Math.max(maxLength, option.length() + 4);
        }

        int boxWidth = Math.max(50, maxLength + 4);
        String horizontalBorder = BOX_COLOR + "═".repeat(boxWidth) + RESET;

        System.out.println(BOX_COLOR + "╔" + horizontalBorder + "╗" + RESET);
        String centeredTitle = centerText(title, boxWidth);
        System.out.println(BOX_COLOR + "║" + TITLE_COLOR + BOLD + centeredTitle + RESET + BOX_COLOR + "║" + RESET);
        System.out.println(BOX_COLOR + "║" + " ".repeat(boxWidth) + "║" + RESET);

        for (int i = 0; i < options.length; i++) {
            String optionText = String.format("%d. %s", i + 1, options[i]);
            String paddedOption = String.format("%-" + boxWidth + "s", " " + optionText);
            System.out.println(BOX_COLOR + "║" + MENU_OPTION_COLOR + paddedOption + RESET + BOX_COLOR + "║" + RESET);
        }

        System.out.println(BOX_COLOR + "║" + " ".repeat(boxWidth) + "║" + RESET);
        System.out.println(BOX_COLOR + "╚" + horizontalBorder + "╝" + RESET);
        System.out.print(colorText("Select an option: ", PROMPT_COLOR));
    }

    public static void printMessage(String message, String type) {
        String color;
        switch (type.toLowerCase()) {
            case "success" -> color = SUCCESS_COLOR;
            case "error" -> color = ERROR_COLOR;
            case "info" -> color = INFO_COLOR;
            case "warning" -> color = WARNING_COLOR;
            default -> color = PRIMARY_TEXT_COLOR;
        }
        System.out.println(color + BOLD + message + RESET);
    }
    
    public static void printTable(String[] headers, String[][] data) {
        int[] colWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            colWidths[i] = headers[i].length();
            for (String[] row : data) {
                if (i < row.length) {
                    colWidths[i] = Math.max(colWidths[i], row[i].length());
                }
            }
            colWidths[i] += 2;
        }
    
        StringBuilder formatBuilder = new StringBuilder();
        for (int width : colWidths) {
            formatBuilder.append("| %-").append(width).append("s ");
        }
        formatBuilder.append("|\n");
        String format = formatBuilder.toString();
    
        StringBuilder borderBuilder = new StringBuilder();
        for (int width : colWidths) {
            borderBuilder.append("+-").append("-".repeat(width)).append("-");
        }
        borderBuilder.append("+");
        String border = borderBuilder.toString();
    
        System.out.println(BOLD_BRIGHT_WHITE + border + RESET);
        System.out.printf(BOLD_BRIGHT_WHITE + BOLD + format + RESET, (Object[]) headers);
        System.out.println(BOLD_BRIGHT_WHITE + border + RESET);
    
        for (String[] row : data) {
            System.out.printf(BOLD_BRIGHT_WHITE + format + RESET, (Object[]) row);
        }
    
        System.out.println(BOLD_BRIGHT_WHITE + border + RESET);
    }
    

    public static void showBanner() {
        String banner = """
            ╔════════════════════════════════════════════════╗
            ║            WELCOME TO TOKEN-TABLE              ║
            ╚════════════════════════════════════════════════╝
            """;

        for (char c : banner.toCharArray()) {
            System.out.print(MUTED_TEAL + BOLD + c + RESET);
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    public static void waitForUserInput(String prompt, Scanner sc) {
        System.out.println(colorText(prompt, PROMPT_COLOR));
        sc.nextLine();
    }
}
