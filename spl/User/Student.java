package User;

import Wallet.SmartWallet;

public class Student extends User {
    private String name;
    private int id;
    private String department;
    private int semester;
    private SmartWallet wallet;

    public Student (String email, String password, String name, int id, String department, int semester)
    {
        super(email, password);
        this.id = id;
        this.department = department;
        this.semester = semester;
        this.wallet = null;
    }

    public String getName ()
    {
        return name;
    }

    public int getID ()
    {
        return id;
    }

    public String getDepartment ()
    {
        return department;
    }

    public int getSemester ()
    {
        return semester;
    }

    public SmartWallet getWallet ()
    {
        return wallet;
    }
}

