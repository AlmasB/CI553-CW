package clients.login;

import javax.swing.*;
import java.awt.*;

public class LoginView {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JButton backButton;

    public LoginView() {
        frame = new JFrame("Sign In");
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }



    public void show() {
        frame.setSize(500, 300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public JButton getSignInButton() {
        return signInButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JFrame getFrame(){
        return frame;
    }

}
