package com.app.view;

import com.app.dao.UserDAO;
import com.app.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmDangKi extends JFrame {
    private JTextField txtUsername, txtFullName, txtEmail, txtPhone, txtDepartment;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JComboBox<String> cmbRole;
    private JButton btnRegister, btnBack, btnClear;
    private JLabel lblStatus;
    private UserDAO userDAO;

    public FrmDangKi() {
        setTitle("Đăng Ký - Hệ Thống Quản Lý Nghiên Cứu Khoa Học");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        userDAO = new UserDAO();
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("ĐĂNG KÝ TÀI KHOẢN");
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
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Mật khẩu:"), gbc);
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Xác nhận mật khẩu:"), gbc);
        txtConfirmPassword = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(txtConfirmPassword, gbc);

        // Full Name
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Họ và tên:"), gbc);
        txtFullName = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtFullName, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Email:"), gbc);
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Số điện thoại:"), gbc);
        txtPhone = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtPhone, gbc);

        // Department
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Khoa/Bộ môn:"), gbc);
        txtDepartment = new JTextField(20);
        gbc.gridx = 1;
        panel.add(txtDepartment, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Vai trò:"), gbc);
        cmbRole = new JComboBox<>(new String[]{"STUDENT", "LECTURER"});
        gbc.gridx = 1;
        panel.add(cmbRole, gbc);

        // Status
        lblStatus = new JLabel("");
        lblStatus.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(lblStatus, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        btnRegister = new JButton("Đăng Ký");
        btnRegister.setBackground(new Color(34, 139, 34));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setPreferredSize(new Dimension(100, 35));

        btnClear = new JButton("Xóa");
        btnClear.setBackground(new Color(100, 100, 100));
        btnClear.setForeground(Color.WHITE);
        btnClear.setPreferredSize(new Dimension(100, 35));

        btnBack = new JButton("Quay Lại");
        btnBack.setBackground(new Color(204, 102, 0));
        btnBack.setForeground(Color.WHITE);
        btnBack.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnBack);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(new JScrollPane(panel));

        // Event Listeners
        btnRegister.addActionListener(e -> register());
        btnClear.addActionListener(e -> clearForm());
        btnBack.addActionListener(e -> {
            new FrmDangNhap().setVisible(true);
            dispose();
        });
    }

    private void register() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        String fullName = txtFullName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String department = txtDepartment.getText();
        String role = (String) cmbRole.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
            lblStatus.setText("Vui lòng điền đầy đủ thông tin!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            lblStatus.setText("Mật khẩu xác nhận không trùng khớp!");
            return;
        }

        if (userDAO.checkUsernameExists(username)) {
            lblStatus.setText("Tên đăng nhập đã tồn tại!");
            return;
        }

        User newUser = new User(username, password, fullName, email, phone, role, department);
        if (userDAO.addUser(newUser)) {
            lblStatus.setText("Đăng ký thành công!");
            lblStatus.setForeground(Color.GREEN);
            JOptionPane.showMessageDialog(this, "Đăng ký thành công! Vui lòng đăng nhập.");
            new FrmDangNhap().setVisible(true);
            dispose();
        } else {
            lblStatus.setText("Lỗi đăng ký!");
        }
    }

    private void clearForm() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
        txtFullName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtDepartment.setText("");
        lblStatus.setText("");
    }
}
