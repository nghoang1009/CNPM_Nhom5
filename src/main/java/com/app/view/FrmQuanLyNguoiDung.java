package com.app.view;

import com.app.dao.UserDAO;
import com.app.model.User;
import com.app.util.PermissionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmQuanLyNguoiDung extends JFrame {
    private User currentUser;
    private UserDAO userDAO;
    private JTable tblUsers;
    private DefaultTableModel tableModel;
    private JComboBox<String> cmbRoleFilter;
    private JButton btnAdd, btnEdit, btnDelete, btnRefresh, btnFilter, btnClose;
    private JTextField txtSearch;

    public FrmQuanLyNguoiDung(User user) {
        this.currentUser = user;
        
        // Kiểm tra quyền
        if (!PermissionManager.hasPermission(user, "VIEW_USERS")) {
            JOptionPane.showMessageDialog(null, 
                "Bạn không có quyền quản lý người dùng!", 
                "Lỗi Quyền Hạn", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        setTitle("Quản Lý Người Dùng");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        
        userDAO = new UserDAO();
        initComponents();
        loadUsers();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top Panel: Search & Filter
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBorder(BorderFactory.createTitledBorder("Tìm Kiếm & Lọc"));
        topPanel.setBackground(new Color(240, 240, 240));
        
        topPanel.add(new JLabel("Tìm kiếm:"));
        txtSearch = new JTextField(15);
        topPanel.add(txtSearch);
        
        topPanel.add(new JLabel("Vai trò:"));
        cmbRoleFilter = new JComboBox<>(new String[]{"Tất Cả", "ADMIN", "LECTURER", "STUDENT", "REVIEWER"});
        topPanel.add(cmbRoleFilter);
        
        btnFilter = new JButton("🔍 Lọc");
        btnFilter.setBackground(new Color(0, 102, 204));
        btnFilter.setForeground(Color.WHITE);
        topPanel.add(btnFilter);
        
        btnRefresh = new JButton("🔄 Làm Mới");
        btnRefresh.setBackground(new Color(100, 100, 100));
        btnRefresh.setForeground(Color.WHITE);
        topPanel.add(btnRefresh);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Middle Panel: Table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh Sách Người Dùng"));
        
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Tên Đăng Nhập", "Họ Tên", "Email", "Điện Thoại", "Vai Trò", "Khoa/Bộ Môn"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsers = new JTable(tableModel);
        tblUsers.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblUsers.setRowHeight(25);
        tblUsers.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(tblUsers);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Bottom Panel: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnAdd = new JButton("➕ Thêm");
        btnAdd.setBackground(new Color(34, 139, 34));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setPreferredSize(new Dimension(120, 35));
        btnAdd.setEnabled(PermissionManager.hasPermission(currentUser, "ADD_USER"));
        
        btnEdit = new JButton("✏️ Sửa");
        btnEdit.setBackground(new Color(0, 102, 204));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setPreferredSize(new Dimension(120, 35));
        btnEdit.setEnabled(PermissionManager.hasPermission(currentUser, "EDIT_USER"));
        
        btnDelete = new JButton("🗑️ Xóa");
        btnDelete.setBackground(new Color(204, 0, 0));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setPreferredSize(new Dimension(120, 35));
        btnDelete.setEnabled(PermissionManager.hasPermission(currentUser, "DELETE_USER"));
        
        btnClose = new JButton("❌ Đóng");
        btnClose.setBackground(new Color(100, 100, 100));
        btnClose.setForeground(Color.WHITE);
        btnClose.setPreferredSize(new Dimension(120, 35));
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClose);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);

        // Event Listeners
        btnAdd.addActionListener(e -> showAddUserDialog());
        btnEdit.addActionListener(e -> showEditUserDialog());
        btnDelete.addActionListener(e -> deleteUser());
        btnRefresh.addActionListener(e -> loadUsers());
        btnFilter.addActionListener(e -> filterUsers());
        btnClose.addActionListener(e -> dispose());
    }

    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            tableModel.addRow(new Object[]{
                user.getUserId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone() != null ? user.getPhone() : "",
                getRoleDisplay(user.getRole()),
                user.getDepartment() != null ? user.getDepartment() : ""
            });
        }
    }

    private void filterUsers() {
        String searchText = txtSearch.getText().toLowerCase();
        String roleFilter = (String) cmbRoleFilter.getSelectedItem();
        
        tableModel.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        
        for (User user : users) {
            // Lọc theo role
            if (!roleFilter.equals("Tất Cả") && !user.getRole().equals(roleFilter)) {
                continue;
            }
            
            // Lọc theo tìm kiếm
            if (!searchText.isEmpty()) {
                if (!user.getUsername().toLowerCase().contains(searchText) &&
                    !user.getFullName().toLowerCase().contains(searchText) &&
                    !user.getEmail().toLowerCase().contains(searchText)) {
                    continue;
                }
            }
            
            tableModel.addRow(new Object[]{
                user.getUserId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone() != null ? user.getPhone() : "",
                getRoleDisplay(user.getRole()),
                user.getDepartment() != null ? user.getDepartment() : ""
            });
        }
    }

    private void showAddUserDialog() {
        JDialog dialog = new JDialog(this, "Thêm Người Dùng", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input fields
        JTextField txtUsername = new JTextField(20);
        JPasswordField txtPassword = new JPasswordField(20);
        JPasswordField txtConfirmPass = new JPasswordField(20);
        JTextField txtFullName = new JTextField(20);
        JTextField txtEmail = new JTextField(20);
        JTextField txtPhone = new JTextField(20);
        JTextField txtDepartment = new JTextField(20);
        JComboBox<String> cmbRole = new JComboBox<>(new String[]{"ADMIN", "LECTURER", "STUDENT", "REVIEWER"});

        int row = 0;
        
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Xác nhận mật khẩu:"), gbc);
        gbc.gridx = 1;
        panel.add(txtConfirmPass, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 1;
        panel.add(txtFullName, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Điện thoại:"), gbc);
        gbc.gridx = 1;
        panel.add(txtPhone, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Khoa/Bộ môn:"), gbc);
        gbc.gridx = 1;
        panel.add(txtDepartment, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Vai trò:"), gbc);
        gbc.gridx = 1;
        panel.add(cmbRole, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        
        JButton btnSave = new JButton("💾 Lưu");
        btnSave.setBackground(new Color(34, 139, 34));
        btnSave.setForeground(Color.WHITE);
        
        JButton btnCancel = new JButton("❌ Hủy");
        btnCancel.setBackground(new Color(204, 0, 0));
        btnCancel.setForeground(Color.WHITE);
        
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        panel.add(btnPanel, gbc);

        btnSave.addActionListener(e -> {
            String password = new String(txtPassword.getPassword());
            String confirmPass = new String(txtConfirmPass.getPassword());
            
            if (txtUsername.getText().isEmpty() || password.isEmpty() || txtFullName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!");
                return;
            }
            
            if (!password.equals(confirmPass)) {
                JOptionPane.showMessageDialog(dialog, "Mật khẩu xác nhận không trùng!");
                return;
            }
            
            User newUser = new User(
                txtUsername.getText(),
                password,
                txtFullName.getText(),
                txtEmail.getText(),
                txtPhone.getText(),
                (String) cmbRole.getSelectedItem(),
                txtDepartment.getText()
            );
            
            if (userDAO.addUser(newUser)) {
                JOptionPane.showMessageDialog(dialog, "Thêm người dùng thành công!");
                dialog.dispose();
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(dialog, "Thêm người dùng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancel.addActionListener(e -> dialog.dispose());
        
        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }

    private void showEditUserDialog() {
        int selectedRow = tblUsers.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng!");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        User user = userDAO.getUserById(userId);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Không tìm được người dùng!");
            return;
        }

        JDialog dialog = new JDialog(this, "Sửa Thông Tin Người Dùng", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input fields
        JTextField txtUsername = new JTextField(user.getUsername(), 20);
        txtUsername.setEditable(false);
        JTextField txtFullName = new JTextField(user.getFullName(), 20);
        JTextField txtEmail = new JTextField(user.getEmail(), 20);
        JTextField txtPhone = new JTextField(user.getPhone(), 20);
        JTextField txtDepartment = new JTextField(user.getDepartment(), 20);
        JComboBox<String> cmbRole = new JComboBox<>(new String[]{"ADMIN", "LECTURER", "STUDENT", "REVIEWER"});
        cmbRole.setSelectedItem(user.getRole());

        int row = 0;
        
        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 1;
        panel.add(txtFullName, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Điện thoại:"), gbc);
        gbc.gridx = 1;
        panel.add(txtPhone, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Khoa/Bộ môn:"), gbc);
        gbc.gridx = 1;
        panel.add(txtDepartment, gbc);

        gbc.gridx = 0; gbc.gridy = row++;
        panel.add(new JLabel("Vai trò:"), gbc);
        gbc.gridx = 1;
        panel.add(cmbRole, gbc);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        
        JButton btnSave = new JButton("💾 Lưu");
        btnSave.setBackground(new Color(34, 139, 34));
        btnSave.setForeground(Color.WHITE);
        
        JButton btnCancel = new JButton("❌ Hủy");
        btnCancel.setBackground(new Color(204, 0, 0));
        btnCancel.setForeground(Color.WHITE);
        
        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        panel.add(btnPanel, gbc);

        btnSave.addActionListener(e -> {
            user.setFullName(txtFullName.getText());
            user.setEmail(txtEmail.getText());
            user.setPhone(txtPhone.getText());
            user.setDepartment(txtDepartment.getText());
            user.setRole((String) cmbRole.getSelectedItem());
            
            if (userDAO.updateUser(user)) {
                JOptionPane.showMessageDialog(dialog, "Cập nhật người dùng thành công!");
                dialog.dispose();
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(dialog, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnCancel.addActionListener(e -> dialog.dispose());
        
        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }

    private void deleteUser() {
        int selectedRow = tblUsers.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn người dùng!");
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String username = (String) tableModel.getValueAt(selectedRow, 1);

        // Không cho xóa user cuối cùng hoặc user hiện tại
        if (userId == currentUser.getUserId()) {
            JOptionPane.showMessageDialog(this, 
                "Không thể xóa tài khoản của chính mình!", 
                "Cảnh Báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int option = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa người dùng \"" + username + "\"?\n" +
            "Hành động này không thể hoàn tác!",
            "Xác Nhận Xóa",
            JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            if (userDAO.deleteUser(userId)) {
                JOptionPane.showMessageDialog(this, "Xóa người dùng thành công!");
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa người dùng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String getRoleDisplay(String role) {
        switch (role) {
            case "ADMIN":
                return "👑 Quản Trị Viên";
            case "LECTURER":
                return "👨‍🏫 Giảng Viên";
            case "STUDENT":
                return "👨‍🎓 Sinh Viên";
            case "REVIEWER":
                return "👔 Hội Đồng Chấm";
            default:
                return role;
        }
    }
}
