package clients.login;

import clients.Main;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginClient {
    static MiddleFactory mlf = new LocalMiddleFactory();   //Direct access

    public static void main(){

        // username and password
        String username = "user1";
        String password = "password1";


        JFrame frame = new JFrame("Sign In"); // create frame
        JButton button1 = new JButton("Sign in"); // create sign in button
        JButton button2 = new JButton("Back"); // create back button
        JLabel labelUsername = new JLabel("User Name: "); //add text
        JLabel labelPassword = new JLabel(" Password: ");

        JTextField usernameField = new JTextField(20); // add textfield
        JPasswordField passwordField = new JPasswordField(20); // add passwordfield (to conceal sensitive data)

        // set bounds
        labelUsername.setBounds(50,50,100,30);
        labelPassword.setBounds(50,100,100,30);
        usernameField.setBounds(150,50,150,30);
        passwordField.setBounds(150,100,150,30);
        button1.setBounds(110,150, 95,30);
        button2.setBounds(210,150, 95,30);

        // log to console for debug purposes
        System.out.println(labelUsername.getText() + labelPassword.getText());


        //event listener
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Button clicked: signin"); //debug

                // get text and assign to variable
                String usernameInput = usernameField.getText();
                String passwordInput = passwordField.getText();
                System.out.println("Information gathered:\n"+ "Username: "+ usernameInput +"\n Password: "+ passwordInput ); // debug

                // validation of correct credentials - display error message if wrong
                if (usernameInput.equals(username) && passwordInput.equals(password)){
                    JOptionPane.showMessageDialog(frame, "Successfully Logged in! \n Welcome " + username);

                    Main employee = new Main();
                    employee.startEmployeeGUI_MVC( mlf) ;
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Error: Wrong login information");
                }

            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //debug
                System.out.println("Button Clicked: Back");

                Main startupWindow = new Main(); // create new object : main
                frame.dispose(); //close window
                startupWindow.startup();// start program again
            }
        });

        //add to frame
        frame.add(labelUsername);
        frame.add(labelPassword);
        frame.add(button1);
        frame.add(button2);
        frame.add(usernameField);
        frame.add(passwordField);
        frame.setSize(500,300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false); // stops resizing of frame

    }
}
