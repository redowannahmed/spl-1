package wallet;
import java.io.*;
import java.util.*;
import java.time.format.DateTimeFormatter;

import auth.Authentication;
import user.Student;
import UI.UI;

public class WalletManager {
    private static final String RECHARGE_REQUESTS_FILE = "rechargeRequests.txt";
    private Authentication auth;

    public WalletManager(Authentication auth) {
        this.auth = auth;
    }

    public boolean isDuplicateSlipId(String slipId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(RECHARGE_REQUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                RechargeRequest request = RechargeRequest.fromString(line);
                if (request.getSlipId().equals(slipId)) {
                    return true; 
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading recharge requests file: " + e.getMessage());
        }
        return false;
    }

    public boolean requestRecharge(String username, int amount, String slipId) {
        if (amount < 0) {
            UI.printMessage("Recharge amount cannot be negative", "error");
            return false;
        }
    
        if (slipId == null || slipId.trim().isEmpty()) {
            UI.printMessage("Slip ID cannot be blank or null", "error");
            return false;
        }
    
        if (isDuplicateSlipId(slipId)) {
            UI.printMessage("Slip ID cannot be duplicate", "error");
            return false;
        }
    
        RechargeRequest request = new RechargeRequest(username, amount, slipId);
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECHARGE_REQUESTS_FILE, true))) {
            writer.write(request.toString());
            writer.newLine();
        } catch (IOException e) {
            UI.printMessage("Error writing to recharge requests file: " + e.getMessage(), "error");
            return false;
        }
    
        return true;
    }
    
    

    private List<RechargeRequest> loadRequests() {
        List<RechargeRequest> requests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RECHARGE_REQUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requests.add(RechargeRequest.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error reading recharge requests file: " + e.getMessage());
        }
        return requests;
    }

    public void viewPendingRequests(String username) {
        List<RechargeRequest> requests = loadRequests();
    
        List<RechargeRequest> studentRequests = new ArrayList<>();
        for (RechargeRequest request : requests) {
            if (request.getUsername().equals(username)) {
                studentRequests.add(request);
            }
        }
    
        if (studentRequests.isEmpty()) {
            System.out.println(UI.colorText("No pending recharge requests found for " + username + ".", UI.EMERALD_GREEN));
            return;
        }
    
        String[] headers = { "No.", "Amount", "Slip ID", "Date" };
        String[][] data = new String[studentRequests.size()][4];
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
        int count = 0;
        for (RechargeRequest request : studentRequests) {
            data[count][0] = String.valueOf(count + 1);
            data[count][1] = String.valueOf(request.getAmount());
            data[count][2] = request.getSlipId();
            data[count][3] = request.getDate().format(formatter);
            count++;
        }
    
        System.out.println(UI.boldText(UI.colorText("Pending Recharge Requests for " + username, UI.EMERALD_GREEN)));
        System.out.println();
        UI.printTable(headers, data);
    }
    
    public void processRechargeRequests(Scanner sc) {
        List<RechargeRequest> requests = loadRequests();
    
        if (requests.isEmpty()) {
            System.out.println("No recharge requests available.");
            return;
        }
    
        String[] headers = {"No.", "Username", "Amount", "Slip ID", "Date"};
    
        String[][] data = new String[requests.size()][headers.length];
        int cnt = 0;
        for (RechargeRequest request : requests) {
            data[cnt][0] = String.valueOf(cnt + 1);
            data[cnt][1] = request.getUsername();
            data[cnt][2] = String.valueOf(request.getAmount());
            data[cnt][3] = request.getSlipId();
            data[cnt][4] = request.getDate().toString(); 
            cnt++;
        }
    
        UI.clearScreen();
        UI.printTable(headers, data);
    
        System.out.println("Enter the slip ID of the request you want to process:");
        String slipId = sc.nextLine();
    
        RechargeRequest matchedRequest = null;
        for (RechargeRequest r : requests) {
            if (r.getSlipId().equals(slipId)) {
                matchedRequest = r;
                break;
            }
        }
    
        if (matchedRequest != null) {
            System.out.println("1. Approve  2. Decline");
            int choice = sc.nextInt();
            sc.nextLine();
    
            if (choice == 1) {
                approveRequest(matchedRequest);
                System.out.println("Request approved and balance updated.");
            } else {
                System.out.println("Request declined.");
                removeRequest(matchedRequest);
            }
        } else {
            System.out.println("Invalid slip ID. Please try again.");
        }
    }
    

    private void approveRequest(RechargeRequest request) {
        Student student = auth.findStudentByUsername(request.getUsername());
        if (student != null) {
            int currentBalance = loadBalanceFromFile(student.getUsername());
            int updatedBalance = currentBalance + request.getAmount();
            student.getWallet().addBalance(request.getAmount());
            updateWalletBalanceInFile(student.getUsername(), updatedBalance);
            removeRequest(request);
        }
    }

    public int loadBalanceFromFile(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("studentWallet.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return Integer.parseInt(parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading wallet balance from file: " + e.getMessage());
        }
        return 0;
    }

    public void updateWalletBalanceInFile(String username, int newBalance) {
        File file = new File("studentWallet.txt");
        List<String> lines = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean found = false;
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    lines.add(username + "," + newBalance); 
                    found = true;
                } else {
                    lines.add(line);
                }
            }
    
            if (!found) {
                lines.add(username + "," + newBalance);
            }
        } catch (IOException e) {
            System.out.println("Error reading wallet file: " + e.getMessage());
            return; 
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) { // 'false' to overwrite
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to wallet file: " + e.getMessage());
        }
    }
    

    private void removeRequest(RechargeRequest request) {
        List<RechargeRequest> requests = loadRequests();

        for (Iterator<RechargeRequest> iterator = requests.iterator(); iterator.hasNext(); ) {
            RechargeRequest r = iterator.next();
            if (r.getSlipId().equals(request.getSlipId())) {
                iterator.remove();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECHARGE_REQUESTS_FILE))) {
            for (RechargeRequest req : requests) {
                writer.write(req.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating recharge requests file: " + e.getMessage());
        }
    }
}