package user;
import java.io.*;

import wallet.Wallet;

public class Student extends User {
    private String name;
    private int id;
    private Wallet wallet;

    public Student(String name, String username, int id, String password) {
        super(username, password);
        this.name = name;
        this.id = id;
        this.wallet = new Wallet();  
        loadBalanceFromFile();      
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Wallet getWallet() {
        loadBalanceFromFile();
        return wallet;
    }

    @Override
    public String toString() {
        return name + "," + username + "," + id + "," + password;
    }

    public static Student fromString(String data) {
        String[] parts = data.split(",");
        if (parts.length != 4) { 
            throw new IllegalArgumentException("Invalid student data format: " + data);
        }
        return new Student(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
    }

    public void loadBalanceFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("studentWallet.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(this.username)) {
                    this.wallet = Wallet.fromString(parts[1]);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading wallet balance from file: " + e.getMessage());
        }
    }
}
