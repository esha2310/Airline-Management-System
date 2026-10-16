package com.airline.management;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Cancel extends JFrame {
    private final JTextField bookingId = new JTextField();

    public Cancel() {
        setTitle("Cancel Ticket");
        setSize(430, 200);
        setLocationRelativeTo(null);

        JPanel p = new JPanel(new GridLayout(2, 2, 8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(30, 25, 30, 25));

        p.add(new JLabel("Booking ID:"));
        p.add(bookingId);

        JButton cancel = new JButton("Cancel Ticket");
        cancel.addActionListener(e -> cancelBooking());
        p.add(new JLabel());
        p.add(cancel);
        add(p);
    }

    private void cancelBooking() {
        String find = "SELECT flight_id, status FROM bookings WHERE id=? FOR UPDATE";
        String cancel = "UPDATE bookings SET status='CANCELLED', cancelled_at=NOW() WHERE id=?";
        String seat = "UPDATE flights SET available_seats=available_seats+1 WHERE id=?";

        try (Connection con = Conn.getConnection()) {
            con.setAutoCommit(false);
            try {
                int bid = Integer.parseInt(bookingId.getText().trim());
                int flightId;

                try (PreparedStatement ps = con.prepareStatement(find)) {
                    ps.setInt(1, bid);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new SQLException("Booking not found.");
                        if ("CANCELLED".equalsIgnoreCase(rs.getString("status"))) {
                            throw new SQLException("Booking is already cancelled.");
                        }
                        flightId = rs.getInt("flight_id");
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(cancel)) {
                    ps.setInt(1, bid);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = con.prepareStatement(seat)) {
                    ps.setInt(1, flightId);
                    ps.executeUpdate();
                }

                con.commit();
                JOptionPane.showMessageDialog(this, "Booking cancelled.");
                dispose();
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Cancellation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
