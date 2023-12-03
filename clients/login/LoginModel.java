package clients.login;

public class LoginModel {
    private String username;
    private String password;

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean validateCredentials(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }
}
