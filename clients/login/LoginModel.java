package clients.login;

import java.util.Observable;

public class LoginModel extends Observable {
    private String username = "user1";
    private String password = "password1";

    public LoginModel() {
        this.username = username;
        this.password = password;
    }

    public boolean validateCredentials(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }

}
