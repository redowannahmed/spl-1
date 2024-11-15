public class Wallet {
    private int balance;

    public Wallet() {
        this.balance = 0;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(int amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void deductBalance (int amount)
    {
        if (amount > 0)
        {
            balance -= amount;
        }
    }


    @Override
    public String toString() {
        return String.valueOf(balance);
    }

    public static Wallet fromString(String balanceData) {
        Wallet wallet = new Wallet();
        wallet.balance = Integer.parseInt(balanceData);
        return wallet;
    }
}
