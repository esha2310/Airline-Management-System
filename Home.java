package com.airline.management;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {
    private final int userId;
    private final String username;
    private final String role;

    public Home(int userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;

        setTitle("Airline Management System - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 470);
        setLocationRelativeTo(null);

        JLabel header = new JLabel(
                "Welcome, " + username + " (" + role + ")",
                SwingConstants.CENTER
        );
        header.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel grid = new JPanel(new GridLayout(3, 3, 12, 12));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        grid.add(button("Flight Details", () -> new FlightInfo().setVisible(true)));
        grid.add(button("Add Customer", () -> new AddCustomer().setVisible(true)));
        grid.add(button("Book Flight", () -> new BookFlight().setVisible(true)));
        grid.add(button("Journey Details", () -> new JourneyDetails().setVisible(true)));
        grid.add(button("Cancel Ticket", () -> new Cancel().setVisible(true)));
        grid.add(button("Boarding Pass", () -> new BoardingPass().setVisible(true)));

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
        grid.add(logout);

        add(header, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
    }

    private JButton button(String text, Runnable action) {
        JButton b = new JButton(text);
        b.addActionListener(e -> action.run());
        return b;
    }
}
