import java.time.LocalDate;

public class RechargeRequest {
    private String username;
    private int amount;
    private String slipId;
    private LocalDate date;

    public RechargeRequest(String username, int amount, String slipId) {
        this.username = username;
        this.amount = amount;
        this.slipId = slipId;
        this.date = LocalDate.now();
    }

    public String getUsername() {
        return username;
    }

    public int getAmount() {
        return amount;
    }

    public String getSlipId() {
        return slipId;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return username + "," + amount + "," + slipId + "," + date;
    }

    public static RechargeRequest fromString(String data) {
        String[] parts = data.split(",");
        return new RechargeRequest(parts[0], Integer.parseInt(parts[1]), parts[2]);
    }
}
