package com.app.view;

import com.app.dao.TopicAssignmentDAO;
import com.app.dao.UserDAO;
import com.app.model.TopicAssignment;
import com.app.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmPhanCongDeTai extends JFrame {
    private User currentUser;
    private TopicAssignmentDAO assignmentDAO;
    private UserDAO userDAO;
    private JTable tblAssignments;
    private DefaultTableModel tableModel;
    private JButton btnApprove, btnReject, btnCancel;

    public FrmPhanCongDeTai(User user) {
        this.currentUser = user;
        setTitle("Phân Công Đề Tài");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        assignmentDAO = new TopicAssignmentDAO();
        userDAO = new UserDAO();
        initComponents();
        loadAssignments();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout());

        // Title
        JLabel lblTitle = new JLabel("DANH SÁCH PHÂN CÔNG ĐỀ TÀI");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(lblTitle, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Đề Tài ID", "Sinh Viên", "Trạng Thái", "Ngày Phân Công"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblAssignments = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblAssignments);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        btnApprove = new JButton("Duyệt");
        btnApprove.setBackground(new Color(34, 139, 34));
        btnApprove.setForeground(Color.WHITE);
        btnApprove.setPreferredSize(new Dimension(100, 35));

        btnReject = new JButton("Từ Chối");
        btnReject.setBackground(new Color(204, 0, 0));
        btnReject.setForeground(Color.WHITE);
        btnReject.setPreferredSize(new Dimension(100, 35));

        btnCancel = new JButton("Đóng");
        btnCancel.setBackground(new Color(100, 100, 100));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(btnApprove);
        buttonPanel.add(btnReject);
        buttonPanel.add(btnCancel);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        btnApprove.addActionListener(e -> updateStatus("ACCEPTED"));
        btnReject.addActionListener(e -> updateStatus("REJECTED"));
        btnCancel.addActionListener(e -> dispose());
    }

    private void loadAssignments() {
        tableModel.setRowCount(0);
        List<TopicAssignment> assignments = assignmentDAO.getAllAssignments();
        for (TopicAssignment assignment : assignments) {
            User student = userDAO.getUserById(assignment.getStudentId());
            tableModel.addRow(new Object[]{
                assignment.getAssignmentId(),
                assignment.getTopicId(),
                student != null ? student.getFullName() : "Unknown",
                assignment.getStatus(),
                assignment.getAssignedAt()
            });
        }
    }

    private void updateStatus(String newStatus) {
        int selectedRow = tblAssignments.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phân công!");
            return;
        }

        int assignmentId = (int) tableModel.getValueAt(selectedRow, 0);
        TopicAssignment assignment = assignmentDAO.getAssignmentById(assignmentId);
        
        if (assignment != null) {
            assignment.setStatus(newStatus);
            if (assignmentDAO.updateAssignment(assignment)) {
                JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thành công!");
                loadAssignments();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật trạng thái thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
