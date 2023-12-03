package clients.login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginModel model;
    private LoginView view;

    public LoginController(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;

        view.getSignInButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = view.getUsername();
                String passwordInput = view.getPassword();

                if (model.validateCredentials(usernameInput, passwordInput)) {
                    JOptionPane.showMessageDialog(view.getFrame(), "Successfully Logged in! \n Welcome " + usernameInput);
                } else {
                    view.showErrorMessage("Error: Wrong login information");
                }
            }
        });

        view.getBackButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the "Back" button click, e.g., navigate back to the startup screen
            }
        });
    }

    public void showLoginView() {
        view.show();
    }
}
