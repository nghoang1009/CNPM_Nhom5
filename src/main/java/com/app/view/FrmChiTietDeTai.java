package com.app.view;

import com.app.dao.ResearchTopicDAO;
import com.app.model.ResearchTopic;
import com.app.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmChiTietDeTai extends JFrame {
    private User currentUser;
    private ResearchTopicDAO topicDAO;
    private JTable tblTopics;
    private DefaultTableModel tableModel;
    private JTextArea txtDetail;
    private JButton btnViewDetail, btnCancel;

    public FrmChiTietDeTai(User user) {
        this.currentUser = user;
        setTitle("Chi Tiết Đề Tài");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        topicDAO = new ResearchTopicDAO();
        initComponents();
        loadTopics();
    }

    private void initComponents() {
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        // Top: Table
        tableModel = new DefaultTableModel(
            new String[]{"ID", "Tiêu Đề", "Trạng Thái", "Lĩnh Vực"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTopics = new JTable(tableModel);
        JScrollPane topScroll = new JScrollPane(tblTopics);
        splitPane.setTopComponent(topScroll);

        // Bottom: Detail
        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("Chi Tiết Đề Tài"));
        
        txtDetail = new JTextArea(10, 50);
        txtDetail.setEditable(false);
        txtDetail.setLineWrap(true);
        txtDetail.setWrapStyleWord(true);
        JScrollPane detailScroll = new JScrollPane(txtDetail);
        detailPanel.add(detailScroll, BorderLayout.CENTER);

        splitPane.setBottomComponent(detailPanel);
        splitPane.setDividerLocation(250);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(splitPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        btnViewDetail = new JButton("Xem Chi Tiết");
        btnViewDetail.setBackground(new Color(0, 102, 204));
        btnViewDetail.setForeground(Color.WHITE);
        btnViewDetail.setPreferredSize(new Dimension(120, 35));

        btnCancel = new JButton("Đóng");
        btnCancel.setBackground(new Color(204, 0, 0));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(btnViewDetail);
        buttonPanel.add(btnCancel);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        btnViewDetail.addActionListener(e -> showDetail());
        btnCancel.addActionListener(e -> dispose());
        tblTopics.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() >= 1) showDetail();
            }
        });
    }

    private void loadTopics() {
        tableModel.setRowCount(0);
        List<ResearchTopic> topics = topicDAO.getAllTopics();
        for (ResearchTopic topic : topics) {
            tableModel.addRow(new Object[]{
                topic.getTopicId(),
                topic.getTitle(),
                topic.getStatus(),
                topic.getField()
            });
        }
    }

    private void showDetail() {
        int selectedRow = tblTopics.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đề tài!");
            return;
        }

        int topicId = (int) tableModel.getValueAt(selectedRow, 0);
        ResearchTopic topic = topicDAO.getTopicById(topicId);

        if (topic != null) {
            StringBuilder detail = new StringBuilder();
            detail.append("ID: ").append(topic.getTopicId()).append("\n");
            detail.append("Tiêu Đề: ").append(topic.getTitle()).append("\n");
            detail.append("Lĩnh Vực: ").append(topic.getField()).append("\n");
            detail.append("Trạng Thái: ").append(topic.getStatus()).append("\n");
            detail.append("Số Thành Viên Tối Đa: ").append(topic.getMaxMembers()).append("\n");
            detail.append("Giảng Viên Hướng Dẫn: ").append(topic.getLecturerId()).append("\n\n");
            detail.append("Mô Tả:\n").append(topic.getDescription());
            
            txtDetail.setText(detail.toString());
            txtDetail.setCaretPosition(0);
        }
    }
}
