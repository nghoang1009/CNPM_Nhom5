package com.app.view;

import com.app.dao.BoardAssignmentDAO;
import com.app.dao.ReviewBoardDAO;
import com.app.dao.UserDAO;
import com.app.model.BoardAssignment;
import com.app.model.ReviewBoard;
import com.app.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmPhanCongHoiDong extends JFrame {
    private User currentUser;
    private ReviewBoardDAO boardDAO;
    private BoardAssignmentDAO assignmentDAO;
    private UserDAO userDAO;
    private JTable tblAssignments;
    private DefaultTableModel tableModel;
    private JComboBox<ReviewBoard> cmbBoard;
    private JComboBox<User> cmbMember;
    private JComboBox<String> cmbRole;
    private JButton btnAdd, btnRemove, btnCancel;

    public FrmPhanCongHoiDong(User user) {
        this.currentUser = user;
        setTitle("Phân Công Hội Đồng");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        boardDAO = new ReviewBoardDAO();
        assignmentDAO = new BoardAssignmentDAO();
        userDAO = new UserDAO();
        initComponents();
        loadData();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top: Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Phân Công Hội Đồng"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Hội Đồng:"), gbc);
        cmbBoard = new JComboBox<>();
        gbc.gridx = 1;
        inputPanel.add(cmbBoard, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Thành Viên:"), gbc);
        cmbMember = new JComboBox<>();
        gbc.gridx = 3;
        inputPanel.add(cmbMember, gbc);

        gbc.gridx = 4;
        inputPanel.add(new JLabel("Vai Trò:"), gbc);
        cmbRole = new JComboBox<>(new String[]{"MEMBER", "CHAIRMAN"});
        gbc.gridx = 5;
        inputPanel.add(cmbRole, gbc);

        btnAdd = new JButton("Thêm");
        btnAdd.setBackground(new Color(34, 139, 34));
        btnAdd.setForeground(Color.WHITE);
        gbc.gridx = 6;
        inputPanel.add(btnAdd, gbc);

        mainPanel.add(inputPanel, BorderLayout.NORTH);

        // Middle: Table
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Hội Đồng", "Thành Viên", "Vai Trò", "Ngày Phân Công"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblAssignments = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblAssignments);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom: Buttons
        JPanel buttonPanel = new JPanel();
        btnRemove = new JButton("Xóa");
        btnRemove.setBackground(new Color(204, 0, 0));
        btnRemove.setForeground(Color.WHITE);
        btnRemove.setPreferredSize(new Dimension(100, 35));

        btnCancel = new JButton("Đóng");
        btnCancel.setBackground(new Color(100, 100, 100));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(btnRemove);
        buttonPanel.add(btnCancel);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        btnAdd.addActionListener(e -> addAssignment());
        btnRemove.addActionListener(e -> removeAssignment());
        btnCancel.addActionListener(e -> dispose());
    }

    private void loadData() {
        // Load boards
        List<ReviewBoard> boards = boardDAO.getAllBoards();
        cmbBoard.removeAllItems();
        for (ReviewBoard board : boards) {
            cmbBoard.addItem(board);
        }

        // Load members (lecturers and reviewers)
        List<User> members = userDAO.getUsersByRole("LECTURER");
        members.addAll(userDAO.getUsersByRole("REVIEWER"));
        cmbMember.removeAllItems();
        for (User member : members) {
            cmbMember.addItem(member);
        }

        loadAssignments();
    }

    private void loadAssignments() {
        tableModel.setRowCount(0);
        List<BoardAssignment> assignments = assignmentDAO.getAllAssignments();
        for (BoardAssignment assignment : assignments) {
            ReviewBoard board = boardDAO.getBoardById(assignment.getBoardId());
            User member = userDAO.getUserById(assignment.getMemberId());
            tableModel.addRow(new Object[]{
                assignment.getAssignmentId(),
                board != null ? board.getName() : "Unknown",
                member != null ? member.getFullName() : "Unknown",
                assignment.getRole(),
                assignment.getAssignedAt()
            });
        }
    }

    private void addAssignment() {
        ReviewBoard board = (ReviewBoard) cmbBoard.getSelectedItem();
        User member = (User) cmbMember.getSelectedItem();
        String role = (String) cmbRole.getSelectedItem();

        if (board == null || member == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hội đồng và thành viên!");
            return;
        }

        BoardAssignment assignment = new BoardAssignment(board.getBoardId(), member.getUserId(), role);
        if (assignmentDAO.addAssignment(assignment)) {
            JOptionPane.showMessageDialog(this, "Thêm phân công thành công!");
            loadAssignments();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm phân công thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeAssignment() {
        int selectedRow = tblAssignments.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phân công!");
            return;
        }

        int assignmentId = (int) tableModel.getValueAt(selectedRow, 0);
        if (assignmentDAO.deleteAssignment(assignmentId)) {
            JOptionPane.showMessageDialog(this, "Xóa phân công thành công!");
            loadAssignments();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa phân công thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
