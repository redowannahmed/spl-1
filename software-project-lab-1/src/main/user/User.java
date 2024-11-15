package user;
public abstract class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername (String NewUsername)
    {
        this.username = NewUsername;
    }   

    public void setPassword (String newPassword)
    {
        this.password = newPassword;
    }
}
