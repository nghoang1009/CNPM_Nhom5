package com.app.view;

import com.app.dao.ReviewBoardDAO;
import com.app.dao.UserDAO;
import com.app.model.ReviewBoard;
import com.app.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmQuanLyHoiDong extends JFrame {
    private User currentUser;
    private ReviewBoardDAO boardDAO;
    private UserDAO userDAO;
    private JTable tblBoards;
    private DefaultTableModel tableModel;
    private JTextField txtName, txtDescription;
    private JComboBox<User> cmbChairman;
    private JComboBox<String> cmbStatus;
    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh, btnClose;
    private int selectedBoardId = -1;

    public FrmQuanLyHoiDong(User user) {
        this.currentUser = user;
        setTitle("Quản Lý Hội Đồng Chấm Điểm");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        boardDAO = new ReviewBoardDAO();
        userDAO = new UserDAO();
        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top: Input Panel for CRUD operations
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Tạo / Chỉnh Sửa Hội Đồng"));
        inputPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tên Hội Đồng
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Tên Hội Đồng:"), gbc);
        txtName = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        inputPanel.add(txtName, gbc);
        gbc.gridwidth = 1;

        // Trưởng Hội Đồng
        gbc.gridx = 3;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Trưởng Hội Đồng:"), gbc);
        cmbChairman = new JComboBox<>();
        gbc.gridx = 4;
        gbc.gridwidth = 2;
        inputPanel.add(cmbChairman, gbc);
        gbc.gridwidth = 1;

        // Trạng Thái
        gbc.gridx = 6;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Trạng Thái:"), gbc);
        cmbStatus = new JComboBox<>(new String[]{"ACTIVE", "INACTIVE"});
        gbc.gridx = 7;
        inputPanel.add(cmbStatus, gbc);

        // Mô Tả (row 2)
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Mô Tả:"), gbc);
        txtDescription = new JTextField(60);
        gbc.gridx = 1;
        gbc.gridwidth = 6;
        gbc.ipady = 20;
        inputPanel.add(txtDescription, gbc);
        gbc.ipady = 0;
        gbc.gridwidth = 1;

        // Buttons (row 3)
        JPanel btnInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        btnAdd = new JButton("➕ Thêm Mới");
        btnAdd.setBackground(new Color(34, 139, 34));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setPreferredSize(new Dimension(110, 35));
        btnInputPanel.add(btnAdd);

        btnUpdate = new JButton("✏️ Cập Nhật");
        btnUpdate.setBackground(new Color(0, 102, 204));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setPreferredSize(new Dimension(110, 35));
        btnInputPanel.add(btnUpdate);

        btnRefresh = new JButton("🔄 Làm Mới");
        btnRefresh.setBackground(new Color(100, 100, 100));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setPreferredSize(new Dimension(110, 35));
        btnInputPanel.add(btnRefresh);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 8;
        inputPanel.add(btnInputPanel, gbc);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Middle: Table for displaying boards
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Tên Hội Đồng", "Mô Tả", "Trưởng Hội Đồng", "Trạng Thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblBoards = new JTable(tableModel);
        tblBoards.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblBoards.setRowHeight(25);
        
        ListSelectionModel selectionModel = tblBoards.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblBoards.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedBoardId = (int) tableModel.getValueAt(selectedRow, 0);
                    loadBoardDetails();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblBoards);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom: Delete and Close buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bottomPanel.setBackground(new Color(240, 240, 240));

        btnDelete = new JButton("🗑️ Xóa");
        btnDelete.setBackground(new Color(204, 0, 0));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setPreferredSize(new Dimension(100, 35));
        bottomPanel.add(btnDelete);

        btnClose = new JButton("🚪 Đóng");
        btnClose.setBackground(new Color(100, 100, 100));
        btnClose.setForeground(Color.WHITE);
        btnClose.setPreferredSize(new Dimension(100, 35));
        bottomPanel.add(btnClose);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // Event Listeners
        btnAdd.addActionListener(e -> addBoard());
        btnUpdate.addActionListener(e -> updateBoard());
        btnDelete.addActionListener(e -> deleteBoard());
        btnRefresh.addActionListener(e -> {
            clearForm();
            loadData();
        });
        btnClose.addActionListener(e -> dispose());
    }

    private void loadData() {
        // Load chairmen (lecturers and managers)
        List<User> lecturers = userDAO.getUsersByRole("LECTURER");
        List<User> managers = userDAO.getUsersByRole("MANAGER");
        cmbChairman.removeAllItems();
        cmbChairman.addItem(null);
        for (User lecturer : lecturers) {
            cmbChairman.addItem(lecturer);
        }
        for (User manager : managers) {
            cmbChairman.addItem(manager);
        }

        loadBoards();
    }

    private void loadBoards() {
        tableModel.setRowCount(0);
        List<ReviewBoard> boards = boardDAO.getAllBoards();
        for (ReviewBoard board : boards) {
            User chairman = (board.getChairman() > 0) ? userDAO.getUserById(board.getChairman()) : null;
            tableModel.addRow(new Object[]{
                board.getBoardId(),
                board.getName(),
                board.getDescription(),
                chairman != null ? chairman.getFullName() : "Chưa gán",
                board.getStatus()
            });
        }
    }

    private void loadBoardDetails() {
        ReviewBoard board = boardDAO.getBoardById(selectedBoardId);
        if (board != null) {
            txtName.setText(board.getName());
            txtDescription.setText(board.getDescription() != null ? board.getDescription() : "");
            
            // Set chairman
            if (board.getChairman() > 0) {
                for (int i = 0; i < cmbChairman.getItemCount(); i++) {
                    User user = cmbChairman.getItemAt(i);
                    if (user != null && user.getUserId() == board.getChairman()) {
                        cmbChairman.setSelectedIndex(i);
                        break;
                    }
                }
            } else {
                cmbChairman.setSelectedIndex(0);
            }
            
            cmbStatus.setSelectedItem(board.getStatus());
        }
    }

    private void addBoard() {
        String name = txtName.getText().trim();
        String description = txtDescription.getText().trim();
        User chairman = (User) cmbChairman.getSelectedItem();
        String status = (String) cmbStatus.getSelectedItem();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hội đồng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (chairman == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trưởng hội đồng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ReviewBoard board = new ReviewBoard();
        board.setName(name);
        board.setDescription(description);
        board.setChairman(chairman.getUserId());
        board.setStatus(status);

        if (boardDAO.addBoard(board)) {
            JOptionPane.showMessageDialog(this, "✅ Tạo hội đồng mới thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadBoards();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Tạo hội đồng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBoard() {
        if (selectedBoardId < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hội đồng để cập nhật!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = txtName.getText().trim();
        String description = txtDescription.getText().trim();
        User chairman = (User) cmbChairman.getSelectedItem();
        String status = (String) cmbStatus.getSelectedItem();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên hội đồng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (chairman == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trưởng hội đồng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ReviewBoard board = new ReviewBoard();
        board.setBoardId(selectedBoardId);
        board.setName(name);
        board.setDescription(description);
        board.setChairman(chairman.getUserId());
        board.setStatus(status);

        if (boardDAO.updateBoard(board)) {
            JOptionPane.showMessageDialog(this, "✅ Cập nhật hội đồng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadBoards();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Cập nhật hội đồng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBoard() {
        if (selectedBoardId < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hội đồng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc chắn muốn xóa hội đồng này?\nHành động này sẽ xóa tất cả phân công liên quan!", 
            "Xác Nhận Xóa", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (boardDAO.deleteBoard(selectedBoardId)) {
                JOptionPane.showMessageDialog(this, "✅ Xóa hội đồng thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadBoards();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Xóa hội đồng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtDescription.setText("");
        cmbChairman.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
        selectedBoardId = -1;
        tblBoards.clearSelection();
    }
}
