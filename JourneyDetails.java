package com.airline.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JourneyDetails extends JFrame {
    private final JTextField customerId = new JTextField(10);
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Booking ID", "Flight", "Source", "Destination", "Date", "Status"}, 0);

    public JourneyDetails() {
        setTitle("Journey Details");
        setSize(800, 450);
        setLocationRelativeTo(null);

        JPanel top = new JPanel();
        top.add(new JLabel("Customer ID:"));
        top.add(customerId);
        JButton search = new JButton("Search");
        search.addActionListener(e -> loadJourneys());
        top.add(search);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(new JTable(model)), BorderLayout.CENTER);
    }

    private void loadJourneys() {
        model.setRowCount(0);
        String sql = """
                SELECT b.id, f.flight_code, f.source, f.destination, f.flight_date, b.status
                FROM bookings b
                JOIN flights f ON f.id=b.flight_id
                WHERE b.customer_id=?
                ORDER BY f.flight_date DESC
                """;

        try (Connection con = Conn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(customerId.getText().trim()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"),
                            rs.getString("flight_code"),
                            rs.getString("source"),
                            rs.getString("destination"),
                            rs.getDate("flight_date"),
                            rs.getString("status")
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
