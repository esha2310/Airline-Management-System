package com.airline.management;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {
    private final JTextField usernameField = new JTextField(18);
    private final JPasswordField passwordField = new JPasswordField(18);

    public Login() {
        setTitle("Airline Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(430, 260);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        form.add(new JLabel("Username:"), c);
        c.gridx = 1;
        form.add(usernameField, c);

        c.gridx = 0; c.gridy = 1;
        form.add(new JLabel("Password:"), c);
        c.gridx = 1;
        form.add(passwordField, c);

        JButton login = new JButton("Login");
        JButton reset = new JButton("Reset");

        login.addActionListener(e -> authenticate());
        reset.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });

        JPanel buttons = new JPanel();
        buttons.add(login);
        buttons.add(reset);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        form.add(buttons, c);

        add(form);
        getRootPane().setDefaultButton(login);
    }

    private void authenticate() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password.");
            return;
        }

        String sql = "SELECT id, username, role FROM users WHERE username=? AND password_hash=?";
        try (Connection con = Conn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, SecurityUtil.sha256(password));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    dispose();
                    new Home(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("role")
                    ).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials.");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Database connection failed.\n" + ex.getMessage(),
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
