package com.app.view;

import com.app.dao.GradeDAO;
import com.app.dao.ProposalDAO;
import com.app.dao.UserDAO;
import com.app.model.Grade;
import com.app.model.Proposal;
import com.app.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmChamDeCuong extends JFrame {
    private User currentUser;
    private ProposalDAO proposalDAO;
    private GradeDAO gradeDAO;
    private UserDAO userDAO;
    private JTable tblProposals;
    private DefaultTableModel tableModel;
    private JTextArea txtContent;
    private JSpinner spnScore;
    private JTextArea txtFeedback;
    private JButton btnGrade, btnViewGrades, btnCancel;

    public FrmChamDeCuong(User user) {
        this.currentUser = user;
        setTitle("Chấm Đề Cương");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        proposalDAO = new ProposalDAO();
        gradeDAO = new GradeDAO();
        userDAO = new UserDAO();
        initComponents();
        loadProposals();
    }

    private void initComponents() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // Left: Proposals Table
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Danh Sách Đề Cương"));
        
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Đề Tài ID", "Sinh Viên", "Trạng Thái"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblProposals = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblProposals);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        splitPane.setLeftComponent(leftPanel);

        // Right: Detail & Grading Panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Content
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtContent = new JTextArea(15, 40);
        txtContent.setEditable(false);
        txtContent.setLineWrap(true);
        txtContent.setWrapStyleWord(true);
        contentPanel.add(new JScrollPane(txtContent), BorderLayout.CENTER);
        tabbedPane.addTab("Nội Dung", contentPanel);

        // Tab 2: Grading
        JPanel gradingPanel = new JPanel(new GridBagLayout());
        gradingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gradingPanel.add(new JLabel("Điểm (0-10):"), gbc);
        spnScore = new JSpinner(new SpinnerNumberModel(8.0, 0.0, 10.0, 0.5));
        gbc.gridx = 1;
        gradingPanel.add(spnScore, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gradingPanel.add(new JLabel("Nhận Xét:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 1;
        txtFeedback = new JTextArea(10, 40);
        txtFeedback.setLineWrap(true);
        txtFeedback.setWrapStyleWord(true);
        gradingPanel.add(new JScrollPane(txtFeedback), gbc);

        btnGrade = new JButton("Chấm Điểm");
        btnGrade.setBackground(new Color(34, 139, 34));
        btnGrade.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gradingPanel.add(btnGrade, gbc);

        tabbedPane.addTab("Chấm Điểm", gradingPanel);
        rightPanel.add(tabbedPane, BorderLayout.CENTER);

        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(400);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(splitPane, BorderLayout.CENTER);

        // Bottom Buttons
        JPanel buttonPanel = new JPanel();
        btnViewGrades = new JButton("Xem Điểm");
        btnViewGrades.setBackground(new Color(0, 102, 204));
        btnViewGrades.setForeground(Color.WHITE);
        btnViewGrades.setPreferredSize(new Dimension(120, 35));

        btnCancel = new JButton("Đóng");
        btnCancel.setBackground(new Color(204, 0, 0));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(btnViewGrades);
        buttonPanel.add(btnCancel);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        btnGrade.addActionListener(e -> submitGrade());
        btnViewGrades.addActionListener(e -> viewGrades());
        btnCancel.addActionListener(e -> dispose());
        tblProposals.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showProposalContent();
            }
        });
    }

    private void loadProposals() {
        tableModel.setRowCount(0);
        List<Proposal> proposals = proposalDAO.getAllProposals();
        for (Proposal proposal : proposals) {
            User student = userDAO.getUserById(proposal.getStudentId());
            tableModel.addRow(new Object[]{
                proposal.getProposalId(),
                proposal.getTopicId(),
                student != null ? student.getFullName() : "Unknown",
                proposal.getStatus()
            });
        }
    }

    private void showProposalContent() {
        int selectedRow = tblProposals.getSelectedRow();
        if (selectedRow < 0) return;

        int proposalId = (int) tableModel.getValueAt(selectedRow, 0);
        Proposal proposal = proposalDAO.getProposalById(proposalId);

        if (proposal != null) {
            txtContent.setText(proposal.getContent());
            txtContent.setCaretPosition(0);
        }
    }

    private void submitGrade() {
        int selectedRow = tblProposals.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đề cương!");
            return;
        }

        int proposalId = (int) tableModel.getValueAt(selectedRow, 0);
        double score = (Double) spnScore.getValue();
        String feedback = txtFeedback.getText();

        if (feedback.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nhận xét!");
            return;
        }

        Grade grade = new Grade(proposalId, currentUser.getUserId(), score, feedback);
        if (gradeDAO.addGrade(grade)) {
            JOptionPane.showMessageDialog(this, "Chấm điểm thành công!");
            txtFeedback.setText("");
            spnScore.setValue(8.0);
        } else {
            JOptionPane.showMessageDialog(this, "Chấm điểm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewGrades() {
        int selectedRow = tblProposals.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đề cương!");
            return;
        }

        int proposalId = (int) tableModel.getValueAt(selectedRow, 0);
        List<Grade> grades = gradeDAO.getGradesByProposal(proposalId);

        StringBuilder sb = new StringBuilder("Các Điểm Số:\n\n");
        for (Grade grade : grades) {
            User reviewer = userDAO.getUserById(grade.getReviewerId());
            sb.append("Người Chấm: ").append(reviewer != null ? reviewer.getFullName() : "Unknown").append("\n");
            sb.append("Điểm: ").append(grade.getScore()).append("\n");
            sb.append("Nhận Xét: ").append(grade.getFeedback()).append("\n\n");
        }

        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        JScrollPane sp = new JScrollPane(ta);
        JOptionPane.showMessageDialog(this, sp, "Chi Tiết Điểm", JOptionPane.INFORMATION_MESSAGE);
    }
}
