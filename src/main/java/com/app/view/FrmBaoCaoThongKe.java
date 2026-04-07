package com.app.view;

import com.app.model.User;
import com.app.dao.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FrmBaoCaoThongKe extends JFrame {
    private User currentUser;
    private UserDAO userDAO;
    private ResearchTopicDAO topicDAO;
    private TopicAssignmentDAO assignmentDAO;
    private ProposalDAO proposalDAO;
    private GradeDAO gradeDAO;
    private ReviewBoardDAO boardDAO;

    public FrmBaoCaoThongKe(User user) {
        this.currentUser = user;
        this.userDAO = new UserDAO();
        this.topicDAO = new ResearchTopicDAO();
        this.assignmentDAO = new TopicAssignmentDAO();
        this.proposalDAO = new ProposalDAO();
        this.gradeDAO = new GradeDAO();
        this.boardDAO = new ReviewBoardDAO();

        initializeUI();
        loadStatistics();
    }

    private void initializeUI() {
        setTitle("Báo Cáo & Thống Kê");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header
        JLabel headerLabel = new JLabel("📊 BÁO CÁO & THỐNG KÊ HỆ THỐNG", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Content - multiple statistics panels
        JPanel contentPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        contentPanel.setBackground(new Color(240, 240, 240));

        contentPanel.add(createStatisticPanel("👥 Tổng Số Người Dùng", "totalUsers"));
        contentPanel.add(createStatisticPanel("📋 Tổng Số Đề Tài", "totalTopics"));
        contentPanel.add(createStatisticPanel("✅ Đề Tài Đã Duyệt", "approvedTopics"));
        contentPanel.add(createStatisticPanel("👨‍🎓 Sinh Viên", "totalStudents"));
        contentPanel.add(createStatisticPanel("👨‍🏫 Giảng Viên", "totalLecturers"));
        contentPanel.add(createStatisticPanel("👔 Hội Đồng Chấm", "totalBoards"));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(240, 240, 240));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with more statistics
        JPanel detailPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        detailPanel.setBorder(BorderFactory.createTitledBorder("📈 CHI TIẾT THÊM"));
        detailPanel.setBackground(new Color(240, 240, 240));

        detailPanel.add(createStatisticPanel("📝 Đề Cương Submitted", "totalProposals"));
        detailPanel.add(createStatisticPanel("⭐ Hoàn Thành Chấm Điểm", "totalGrades"));
        detailPanel.add(createStatisticPanel("⏳ Đề Tài Đang Xử Lý", "inProgressTopics"));
        detailPanel.add(createStatisticPanel("📊 Điểm Trung Bình", "averageGrade"));

        mainPanel.add(detailPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createStatisticPanel(String title, String stat) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JLabel valueLabel = new JLabel("0");
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setForeground(new Color(33, 150, 243));
        valueLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        valueLabel.setName(stat);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    private void loadStatistics() {
        try {
            // Count users
            List<User> allUsers = userDAO.getAllUsers();
            int totalUsers = allUsers.size();
            int totalStudents = (int) allUsers.stream().filter(u -> "STUDENT".equals(u.getRole())).count();
            int totalLecturers = (int) allUsers.stream().filter(u -> "LECTURER".equals(u.getRole())).count();

            // Count topics
            List<com.app.model.ResearchTopic> allTopics = topicDAO.getAllTopics();
            int totalTopics = allTopics.size();
            int approvedTopics = (int) allTopics.stream().filter(t -> "APPROVED".equals(t.getStatus())).count();
            int inProgressTopics = (int) allTopics.stream().filter(t -> "IN_PROGRESS".equals(t.getStatus())).count();

            // Count boards
            int totalBoards = boardDAO.getAllBoards().size();

            // Count proposals and grades
            List<com.app.model.Proposal> allProposals = proposalDAO.getAllProposals();
            int totalProposals = allProposals.size();
            List<com.app.model.Grade> allGrades = gradeDAO.getAllGrades();
            int totalGrades = allGrades.size();

            // Calculate average grade
            double averageGrade = 0;
            if (!allGrades.isEmpty()) {
                averageGrade = allGrades.stream().mapToDouble(com.app.model.Grade::getScore).average().orElse(0);
            }

            // Update UI
            updateStatisticValue("totalUsers", String.valueOf(totalUsers));
            updateStatisticValue("totalTopics", String.valueOf(totalTopics));
            updateStatisticValue("approvedTopics", String.valueOf(approvedTopics));
            updateStatisticValue("totalStudents", String.valueOf(totalStudents));
            updateStatisticValue("totalLecturers", String.valueOf(totalLecturers));
            updateStatisticValue("totalBoards", String.valueOf(totalBoards));
            updateStatisticValue("totalProposals", String.valueOf(totalProposals));
            updateStatisticValue("totalGrades", String.valueOf(totalGrades));
            updateStatisticValue("inProgressTopics", String.valueOf(inProgressTopics));
            updateStatisticValue("averageGrade", String.format("%.2f", averageGrade));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải thống kê: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateStatisticValue(String statName, String value) {
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JScrollPane) {
                JScrollPane scroll = (JScrollPane) comp;
                JPanel content = (JPanel) scroll.getViewport().getView();
                updateInPanel(content, statName, value);
            } else if (comp instanceof JPanel && ((JPanel) comp).getBorder() != null) {
                updateInPanel((JPanel) comp, statName, value);
            }
        }
    }

    private void updateInPanel(JPanel panel, String statName, String value) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                for (Component subComp : ((JPanel) comp).getComponents()) {
                    if (subComp instanceof JLabel) {
                        JLabel label = (JLabel) subComp;
                        if (statName.equals(label.getName())) {
                            label.setText(value);
                            return;
                        }
                    }
                }
            }
        }
    }
}
