package com.app.view;

import com.app.dao.ResearchTopicDAO;
import com.app.dao.TopicAssignmentDAO;
import com.app.model.ResearchTopic;
import com.app.model.TopicAssignment;
import com.app.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmDangKiDeTai extends JFrame {
    private User currentUser;
    private ResearchTopicDAO topicDAO;
    private TopicAssignmentDAO assignmentDAO;
    private JTable tblTopics;
    private DefaultTableModel tableModel;
    private JButton btnRegister, btnCancel, btnRefresh;

    public FrmDangKiDeTai(User user) {
        this.currentUser = user;
        setTitle("Đăng Ký Đề Tài");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        topicDAO = new ResearchTopicDAO();
        assignmentDAO = new TopicAssignmentDAO();
        initComponents();
        loadTopics();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout());

        // Title
        JLabel lblTitle = new JLabel("DANH SÁCH ĐỀ TÀI ĐĂNG KÝ");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(lblTitle, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Tiêu Đề", "Mô Tả", "Giảng Viên", "Lĩnh Vực", "Trạng Thái", "Max Thành Viên"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTopics = new JTable(tableModel);
        tblTopics.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JScrollPane scrollPane = new JScrollPane(tblTopics);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        btnRegister = new JButton("Đăng Ký");
        btnRegister.setBackground(new Color(34, 139, 34));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setPreferredSize(new Dimension(100, 35));

        btnRefresh = new JButton("Làm Mới");
        btnRefresh.setBackground(new Color(0, 102, 204));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setPreferredSize(new Dimension(100, 35));

        btnCancel = new JButton("Đóng");
        btnCancel.setBackground(new Color(204, 0, 0));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnCancel);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        btnRegister.addActionListener(e -> registerTopic());
        btnRefresh.addActionListener(e -> loadTopics());
        btnCancel.addActionListener(e -> dispose());
    }

    private void loadTopics() {
        tableModel.setRowCount(0);
        List<ResearchTopic> topics = topicDAO.getTopicsByStatus("APPROVED");
        for (ResearchTopic topic : topics) {
            tableModel.addRow(new Object[]{
                topic.getTopicId(),
                topic.getTitle(),
                topic.getDescription() != null ? topic.getDescription().substring(0, Math.min(50, topic.getDescription().length())) + "..." : "",
                "Giảng viên ID: " + topic.getLecturerId(),
                topic.getField(),
                topic.getStatus(),
                topic.getMaxMembers()
            });
        }
    }

    private void registerTopic() {
        int selectedRow = tblTopics.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đề tài!");
            return;
        }

        int topicId = (int) tableModel.getValueAt(selectedRow, 0);
        
        TopicAssignment assignment = new TopicAssignment(topicId, currentUser.getUserId());
        if (assignmentDAO.addAssignment(assignment)) {
            JOptionPane.showMessageDialog(this, "Đăng ký đề tài thành công!");
            loadTopics();
        } else {
            JOptionPane.showMessageDialog(this, "Đăng ký đề tài thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
