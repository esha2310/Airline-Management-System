package com.airline.management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FlightInfo extends JFrame {
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Flight Code", "Flight Name", "Source", "Destination", "Date", "Seats"}, 0);

    public FlightInfo() {
        setTitle("Flight Details");
        setSize(760, 420);
        setLocationRelativeTo(null);

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> loadFlights());
        add(refresh, BorderLayout.SOUTH);

        loadFlights();
    }

    private void loadFlights() {
        model.setRowCount(0);
        String sql = "SELECT flight_code, flight_name, source, destination, flight_date, available_seats FROM flights ORDER BY flight_date";
        try (Connection con = Conn.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("flight_code"),
                        rs.getString("flight_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getDate("flight_date"),
                        rs.getInt("available_seats")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
