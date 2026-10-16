package com.airline.management;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddCustomer extends JFrame {
    private final JTextField name = new JTextField();
    private final JTextField nationality = new JTextField();
    private final JTextField nid = new JTextField();
    private final JTextField address = new JTextField();
    private final JTextField gender = new JTextField();
    private final JTextField phone = new JTextField();

    public AddCustomer() {
        setTitle("Add Customer");
        setSize(470, 390);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridLayout(7, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        form.add(new JLabel("Name:")); form.add(name);
        form.add(new JLabel("Nationality:")); form.add(nationality);
        form.add(new JLabel("NID/Passport:")); form.add(nid);
        form.add(new JLabel("Address:")); form.add(address);
        form.add(new JLabel("Gender:")); form.add(gender);
        form.add(new JLabel("Phone:")); form.add(phone);

        JButton save = new JButton("Save Customer");
        save.addActionListener(e -> saveCustomer());
        form.add(new JLabel());
        form.add(save);

        add(form);
    }

    private void saveCustomer() {
        String sql = "INSERT INTO customers(name,nationality,nid,address,gender,phone) VALUES(?,?,?,?,?,?)";
        try (Connection con = Conn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name.getText().trim());
            ps.setString(2, nationality.getText().trim());
            ps.setString(3, nid.getText().trim());
            ps.setString(4, address.getText().trim());
            ps.setString(5, gender.getText().trim());
            ps.setString(6, phone.getText().trim());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Customer added.");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
