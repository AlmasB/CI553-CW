package clients.login;

import javax.swing.*;

public class LoginClient {
    public static void main() {
        displayGUI();
    }

    public static void displayGUI(){
        JFrame window = new JFrame("Sign in");
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        LoginModel model = new LoginModel();
        LoginView view = new LoginView();
        LoginController controller = new LoginController(model, view);
        model.addObserver(view);

    }

}
