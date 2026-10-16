package com.airline.management;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BoardingPass extends JFrame {
    private final JTextField bookingId = new JTextField(10);
    private final JTextArea output = new JTextArea();

    public BoardingPass() {
        setTitle("Boarding Pass");
        setSize(560, 390);
        setLocationRelativeTo(null);

        JPanel top = new JPanel();
        top.add(new JLabel("Booking ID:"));
        top.add(bookingId);
        JButton generate = new JButton("Generate");
        generate.addActionListener(e -> generatePass());
        top.add(generate);

        output.setEditable(false);
        output.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);
    }

    private void generatePass() {
        String sql = """
                SELECT b.id, b.status, c.name, c.nid,
                       f.flight_code, f.flight_name, f.source, f.destination, f.flight_date
                FROM bookings b
                JOIN customers c ON c.id=b.customer_id
                JOIN flights f ON f.id=b.flight_id
                WHERE b.id=?
                """;

        try (Connection con = Conn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(bookingId.getText().trim()));
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    output.setText("Booking not found.");
                    return;
                }
                output.setText("""
                        =====================================
                               AIRLINE BOARDING PASS
                        =====================================
                        Booking ID : %d
                        Passenger  : %s
                        NID/Pass   : %s
                        Flight     : %s - %s
                        Route      : %s -> %s
                        Date       : %s
                        Status     : %s
                        =====================================
                        """.formatted(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("nid"),
                        rs.getString("flight_code"),
                        rs.getString("flight_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getDate("flight_date"),
                        rs.getString("status")
                ));
            }
        } catch (Exception ex) {
            output.setText("Error: " + ex.getMessage());
        }
    }
}
