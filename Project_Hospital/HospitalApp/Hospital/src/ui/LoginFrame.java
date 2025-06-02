package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

	
public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login - Hospital System");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel());
        add(loginBtn);

        loginBtn.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        UserDAO dao = new UserDAO();
        User user = dao.authenticate(email, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Welcome, " + user.getFullName());
            dispose();
            new MainFrame(user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password.");
        }
    }
}
