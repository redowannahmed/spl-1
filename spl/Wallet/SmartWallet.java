package Wallet;

public class SmartWallet
{
    private double balance;
    private int studentID;

    public SmartWallet (int studentID)
    {
        this.balance = 0.00;
        this.studentID = studentID;
    }

    public double getBalance ()
    {
        return balance;
    }

    public int getStudentID ()
    {
        return studentID;
    }

    public void addBalance (double amount)
    {
        balance += amount;
    }

    public boolean deductBalance (double amount)
    {
        if (balance >= amount)
        {
            balance -= amount;
            return true;
        }
        
        return false;
    }
}
