package com.airline.management;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BookFlight extends JFrame {
    private final JTextField customerId = new JTextField();
    private final JTextField flightCode = new JTextField();

    public BookFlight() {
        setTitle("Book Flight");
        setSize(430, 240);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridLayout(3, 2, 8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        p.add(new JLabel("Customer ID:"));
        p.add(customerId);
        p.add(new JLabel("Flight Code:"));
        p.add(flightCode);

        JButton book = new JButton("Book");
        book.addActionListener(e -> bookFlight());
        p.add(new JLabel());
        p.add(book);

        add(p);
    }

    private void bookFlight() {
        String findFlight = "SELECT id, available_seats FROM flights WHERE flight_code=? FOR UPDATE";
        String insertBooking = "INSERT INTO bookings(customer_id, flight_id, status) VALUES(?, ?, 'BOOKED')";
        String updateSeats = "UPDATE flights SET available_seats=available_seats-1 WHERE id=?";

        try (Connection con = Conn.getConnection()) {
            con.setAutoCommit(false);
            try {
                int cid = Integer.parseInt(customerId.getText().trim());
                int flightId;

                try (PreparedStatement ps = con.prepareStatement(findFlight)) {
                    ps.setString(1, flightCode.getText().trim());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Flight not found.");
                        }
                        if (rs.getInt("available_seats") <= 0) {
                            throw new SQLException("No seats available.");
                        }
                        flightId = rs.getInt("id");
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(insertBooking)) {
                    ps.setInt(1, cid);
                    ps.setInt(2, flightId);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = con.prepareStatement(updateSeats)) {
                    ps.setInt(1, flightId);
                    ps.executeUpdate();
                }

                con.commit();
                JOptionPane.showMessageDialog(this, "Flight booked successfully.");
                dispose();
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Booking Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
