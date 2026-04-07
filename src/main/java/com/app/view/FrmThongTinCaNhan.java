package com.app.view;

import com.app.dao.UserDAO;
import com.app.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmThongTinCaNhan extends JFrame {
    private User currentUser;
    private JTextField txtFullName, txtEmail, txtPhone, txtDepartment, txtUsername;
    private JButton btnUpdate, btnCancel;
    private UserDAO userDAO;

    public FrmThongTinCaNhan(User user) {
        this.currentUser = user;
        setTitle("Thông Tin Cá Nhân");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        userDAO = new UserDAO();
        initComponents();
        loadUserInfo();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("THÔNG TIN CÁ NHÂN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);
        txtUsername = new JTextField(20);
        txtUsername.setEditable(false);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Họ và tên:"), gbc);
        txtFullName = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtFullName, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Số điện thoại:"), gbc);
        txtPhone = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtPhone, gbc);

        // Department
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Khoa/Bộ môn:"), gbc);
        txtDepartment = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtDepartment, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        btnUpdate = new JButton("Cập Nhật");
        btnUpdate.setBackground(new Color(34, 139, 34));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setPreferredSize(new Dimension(100, 35));

        btnCancel = new JButton("Hủy");
        btnCancel.setBackground(new Color(204, 0, 0));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        btnUpdate.addActionListener(e -> updateUserInfo());
        btnCancel.addActionListener(e -> dispose());
    }

    private void loadUserInfo() {
        txtUsername.setText(currentUser.getUsername());
        txtFullName.setText(currentUser.getFullName());
        txtEmail.setText(currentUser.getEmail());
        txtPhone.setText(currentUser.getPhone());
        txtDepartment.setText(currentUser.getDepartment());
    }

    private void updateUserInfo() {
        currentUser.setFullName(txtFullName.getText());
        currentUser.setEmail(txtEmail.getText());
        currentUser.setPhone(txtPhone.getText());
        currentUser.setDepartment(txtDepartment.getText());

        if (userDAO.updateUser(currentUser)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
