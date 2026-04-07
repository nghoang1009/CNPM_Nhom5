package com.app.view;

import com.app.model.User;
import com.app.util.PermissionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmTrangChu extends JFrame {
    private User currentUser;
    private JPanel mainPanel;

    public FrmTrangChu(User user) {
        this.currentUser = user;
        setTitle("Hệ Thống Quản Lý Nghiên Cứu Khoa Học - " + user.getFullName());
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        
        initComponents();
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setPreferredSize(new Dimension(900, 70));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel lblWelcome = new JLabel("Xin chào, " + currentUser.getFullName());
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        lblWelcome.setForeground(Color.WHITE);
        
        JLabel lblRole = new JLabel("Vai trò: " + getRoleDisplay(currentUser.getRole()));
        lblRole.setFont(new Font("Arial", Font.PLAIN, 14));
        lblRole.setForeground(new Color(200, 220, 255));
        
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(lblWelcome);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lblRole);
        
        headerPanel.add(infoPanel, BorderLayout.WEST);
        
        // Status panel
        JLabel lblTime = new JLabel("Đăng nhập lúc: " + java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        lblTime.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTime.setForeground(new Color(200, 220, 255));
        headerPanel.add(lblTime, BorderLayout.EAST);

        // Content Panel
        JPanel contentPanel = new JPanel(new GridLayout(3, 3, 15, 15));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        contentPanel.setBackground(new Color(240, 240, 240));

        // Add buttons based on role
        addMenuButtons(contentPanel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        add(mainPanel);
    }

    private String getRoleDisplay(String role) {
        switch (role) {
            case "ADMIN":
                return "Quản Trị Viên";
            case "LECTURER":
                return "Giảng Viên";
            case "STUDENT":
                return "Sinh Viên";
            case "MANAGER":
                return "Người Quản Lý";
            default:
                return role;
        }
    }

    private void addMenuButtons(JPanel contentPanel) {
        if (PermissionManager.isStudent(currentUser)) {
            addButton(contentPanel, "👤 Thông Tin Cá Nhân", 
                () -> new FrmThongTinCaNhan(currentUser).setVisible(true));
            addButton(contentPanel, "📋 Đăng Ký Đề Tài", 
                () -> new FrmDangKiDeTai(currentUser).setVisible(true));
            addButton(contentPanel, "📄 Xem Đề Tài", 
                () -> new FrmChiTietDeTai(currentUser).setVisible(true));
            addButton(contentPanel, "� Xem Điểm", 
                () -> showStudentGrades());
            addButton(contentPanel, "�📊 Thống Kê", 
                () -> showStudentStats());
        } else if (PermissionManager.isLecturer(currentUser)) {
            addButton(contentPanel, "👤 Thông Tin Cá Nhân", 
                () -> new FrmThongTinCaNhan(currentUser).setVisible(true));
            addButton(contentPanel, "➕ Tạo Đề Tài", 
                () -> JOptionPane.showMessageDialog(this, "Chức năng đang phát triển"));
            addButton(contentPanel, "📋 Quản Lý Đề Tài", 
                () -> new FrmDangKiDeTai(currentUser).setVisible(true));
            addButton(contentPanel, "📄 Xem Đề Tài", 
                () -> new FrmChiTietDeTai(currentUser).setVisible(true));
            addButton(contentPanel, "⭐ Chấm Đề Cương", 
                () -> new FrmChamDeCuong(currentUser).setVisible(true));
            addButton(contentPanel, "👥 Phân Công Đề Tài", 
                () -> new FrmPhanCongDeTai(currentUser).setVisible(true));
        } else if (PermissionManager.isAdmin(currentUser)) {
            addButton(contentPanel, "👥 Quản Lý Người Dùng", 
                () -> new FrmQuanLyNguoiDung(currentUser).setVisible(true));
            addButton(contentPanel, "📋 Quản Lý Đề Tài", 
                () -> new FrmDangKiDeTai(currentUser).setVisible(true));
            addButton(contentPanel, "👔 Quản Lý Hội Đồng", 
                () -> new FrmPhanCongHoiDong(currentUser).setVisible(true));
            addButton(contentPanel, "📊 Báo Cáo & Thống Kê", 
                () -> new FrmBaoCaoThongKe(currentUser).setVisible(true));
            addButton(contentPanel, "⚙️ Cài Đặt Hệ Thống", 
                () -> new FrmCaiDatHeThong(currentUser).setVisible(true));
        } else if (PermissionManager.isManager(currentUser)) {
            addButton(contentPanel, "👤 Thông Tin Cá Nhân", 
                () -> new FrmThongTinCaNhan(currentUser).setVisible(true));
            addButton(contentPanel, "👥 Quản Lý Người Dùng", 
                () -> new FrmQuanLyNguoiDung(currentUser).setVisible(true));
            addButton(contentPanel, "📋 Quản Lý Đề Tài", 
                () -> new FrmDangKiDeTai(currentUser).setVisible(true));
            addButton(contentPanel, "👔 Quản Lý Hội Đồng", 
                () -> new FrmQuanLyHoiDong(currentUser).setVisible(true));
            addButton(contentPanel, "⭐ Chấm Đề Cương", 
                () -> new FrmChamDeCuong(currentUser).setVisible(true));
            addButton(contentPanel, "📊 Báo Cáo & Thống Kê", 
                () -> new FrmBaoCaoThongKe(currentUser).setVisible(true));
        }

        addButton(contentPanel, "🚪 Đăng Xuất", () -> {
            int option = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn đăng xuất?", 
                "Xác Nhận", 
                JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                new FrmDangNhap().setVisible(true);
                dispose();
            }
        });
    }

    private void showStudentStats() {
        StringBuilder stats = new StringBuilder();
        stats.append("📊 THỐNG KÊ CỦA CÁC EM\n\n");
        stats.append("Tên: ").append(currentUser.getFullName()).append("\n");
        stats.append("Khoa: ").append(currentUser.getDepartment()).append("\n");
        stats.append("Email: ").append(currentUser.getEmail()).append("\n");
        stats.append("SĐT: ").append(currentUser.getPhone()).append("\n\n");
        stats.append("Các thông tin thống kê sẽ được hiển thị tại đây.");
        
        JTextArea ta = new JTextArea(stats.toString());
        ta.setEditable(false);
        ta.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(this, sp, "Thống Kê", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showStudentGrades() {
        StringBuilder grades = new StringBuilder();
        grades.append("📈 ĐIỂM CỦA CÁC EM\n");
        grades.append("═══════════════════════════════════════════════════════\n\n");
        grades.append(String.format("%-30s | %-7s | %-15s\n", "Đề Tài", "Điểm", "Ngày Chấm"));
        grades.append("───────────────────────────────────────────────────────\n");
        
        try {
            com.app.dao.GradeDAO gradeDAO = new com.app.dao.GradeDAO();
            com.app.dao.ProposalDAO proposalDAO = new com.app.dao.ProposalDAO();
            com.app.dao.ResearchTopicDAO topicDAO = new com.app.dao.ResearchTopicDAO();
            
            java.util.List<com.app.model.Grade> studentGrades = gradeDAO.getGradesByStudent(currentUser.getUserId());
            
            if (studentGrades.isEmpty()) {
                grades.append("Chưa có điểm. Vui lòng chờ giảng viên chấm.\n");
            } else {
                double totalScore = 0;
                for (com.app.model.Grade g : studentGrades) {
                    com.app.model.Proposal proposal = proposalDAO.getProposalById(g.getProposalId());
                    if (proposal != null) {
                        com.app.model.ResearchTopic topic = topicDAO.getTopicById(proposal.getTopicId());
                        String topicTitle = (topic != null) ? topic.getTitle() : "Không xác định";
                        if (topicTitle.length() > 28) {
                            topicTitle = topicTitle.substring(0, 25) + "...";
                        }
                        grades.append(String.format("%-30s | %7.2f | %s\n", 
                            topicTitle, 
                            g.getScore(),
                            g.getGradedAt() != null ? g.getGradedAt().format(
                                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A"));
                        totalScore += g.getScore();
                    }
                }
                grades.append("───────────────────────────────────────────────────────\n");
                grades.append(String.format("%-30s | %7.2f |\n", "Điểm Trung Bình", 
                    totalScore / studentGrades.size()));
            }
        } catch (Exception e) {
            grades.append("Lỗi kết nối cơ sở dữ liệu: ").append(e.getMessage());
        }
        
        JTextArea ta = new JTextArea(grades.toString());
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 11));
        ta.setBackground(new Color(245, 245, 245));
        JScrollPane sp = new JScrollPane(ta);
        sp.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, sp, "Xem Điểm", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addButton(JPanel panel, String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 13));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 80, 160), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        button.addActionListener(e -> action.run());
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(0, 122, 244));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(0, 102, 204));
            }
        });
        panel.add(button);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(245, 245, 245));
        
        // 📁 File Menu
        JMenu mnuFile = new JMenu("📁 File");
        mnuFile.setMnemonic('F');
        
        JMenuItem itemProfile = new JMenuItem("👤 Thông Tin Cá Nhân");
        itemProfile.addActionListener(e -> new FrmThongTinCaNhan(currentUser).setVisible(true));
        mnuFile.add(itemProfile);
        
        mnuFile.addSeparator();
        
        JMenuItem itemLogout = new JMenuItem("🚪 Đăng Xuất");
        itemLogout.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn đăng xuất?", 
                "Xác Nhận", 
                JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                new FrmDangNhap().setVisible(true);
                dispose();
            }
        });
        mnuFile.add(itemLogout);
        
        mnuFile.addSeparator();
        
        JMenuItem itemExit = new JMenuItem("⏻ Thoát Ứng Dụng");
        itemExit.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, 
                "Thoát ứng dụng?", 
                "Xác Nhận", 
                JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        mnuFile.add(itemExit);
        
        // 📋 Features Menu (dựa trên role)
        JMenu mnuFeatures = new JMenu("📋 Chức Năng");
        mnuFeatures.setMnemonic('C');
        
        if (PermissionManager.isStudent(currentUser)) {
            JMenuItem itemRegister = new JMenuItem("Đăng Ký Đề Tài");
            itemRegister.addActionListener(e -> new FrmDangKiDeTai(currentUser).setVisible(true));
            mnuFeatures.add(itemRegister);
            
            JMenuItem itemViewTopics = new JMenuItem("Xem Đề Tài");
            itemViewTopics.addActionListener(e -> new FrmChiTietDeTai(currentUser).setVisible(true));
            mnuFeatures.add(itemViewTopics);
            
            mnuFeatures.addSeparator();
            
            JMenuItem itemGrades = new JMenuItem("Xem Điểm");
            itemGrades.addActionListener(e -> showStudentGrades());
            mnuFeatures.add(itemGrades);
        } 
        else if (PermissionManager.isLecturer(currentUser)) {
            JMenuItem itemCreateTopic = new JMenuItem("Tạo Đề Tài Mới");
            itemCreateTopic.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chức năng đang phát triển"));
            mnuFeatures.add(itemCreateTopic);
            
            JMenuItem itemManageTopics = new JMenuItem("Quản Lý Đề Tài");
            itemManageTopics.addActionListener(e -> new FrmDangKiDeTai(currentUser).setVisible(true));
            mnuFeatures.add(itemManageTopics);
            
            mnuFeatures.addSeparator();
            
            JMenuItem itemApprove = new JMenuItem("Duyệt Đề Tài");
            itemApprove.addActionListener(e -> new FrmPhanCongDeTai(currentUser).setVisible(true));
            mnuFeatures.add(itemApprove);
            
            JMenuItem itemGrade = new JMenuItem("Chấm Đề Cương");
            itemGrade.addActionListener(e -> new FrmChamDeCuong(currentUser).setVisible(true));
            mnuFeatures.add(itemGrade);
        }
        else if (PermissionManager.isAdmin(currentUser)) {
            JMenuItem itemUsers = new JMenuItem("Quản Lý Người Dùng");
            itemUsers.addActionListener(e -> new FrmQuanLyNguoiDung(currentUser).setVisible(true));
            mnuFeatures.add(itemUsers);
            
            JMenuItem itemTopics = new JMenuItem("Quản Lý Đề Tài");
            itemTopics.addActionListener(e -> new FrmDangKiDeTai(currentUser).setVisible(true));
            mnuFeatures.add(itemTopics);
            
            mnuFeatures.addSeparator();
            
            JMenuItem itemBoards = new JMenuItem("Quản Lý Hội Đồng");
            itemBoards.addActionListener(e -> new FrmQuanLyHoiDong(currentUser).setVisible(true));
            mnuFeatures.add(itemBoards);
            
            JMenuItem itemReports = new JMenuItem("Báo Cáo & Thống Kê");
            itemReports.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chức năng đang phát triển"));
            mnuFeatures.add(itemReports);
        }
        else if (PermissionManager.isManager(currentUser)) {
            JMenuItem itemUsers = new JMenuItem("Quản Lý Người Dùng");
            itemUsers.addActionListener(e -> new FrmQuanLyNguoiDung(currentUser).setVisible(true));
            mnuFeatures.add(itemUsers);
            
            JMenuItem itemTopics = new JMenuItem("Quản Lý Đề Tài");
            itemTopics.addActionListener(e -> new FrmDangKiDeTai(currentUser).setVisible(true));
            mnuFeatures.add(itemTopics);
            
            mnuFeatures.addSeparator();
            
            JMenuItem itemBoards = new JMenuItem("Quản Lý Hội Đồng");
            itemBoards.addActionListener(e -> new FrmQuanLyHoiDong(currentUser).setVisible(true));
            mnuFeatures.add(itemBoards);
            
            JMenuItem itemGrade = new JMenuItem("Chấm Đề Cương");
            itemGrade.addActionListener(e -> new FrmChamDeCuong(currentUser).setVisible(true));
            mnuFeatures.add(itemGrade);
            
            JMenuItem itemReports = new JMenuItem("Báo Cáo & Thống Kê");
            itemReports.addActionListener(e -> new FrmBaoCaoThongKe(currentUser).setVisible(true));
            mnuFeatures.add(itemReports);
        }
        
        // 📊 Tools Menu
        JMenu mnuTools = new JMenu("🛠️ Công Cụ");
        mnuTools.setMnemonic('T');
        
        JMenuItem itemRefresh = new JMenuItem("🔄 Làm Mới");
        itemRefresh.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Dữ liệu đã được làm mới!");
        });
        mnuTools.add(itemRefresh);
        
        JMenuItem itemSettings = new JMenuItem("⚙️ Cài Đặt");
        itemSettings.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Cửa sổ cài đặt");
        });
        mnuTools.add(itemSettings);
        
        // 📞 Help Menu
        JMenu mnuHelp = new JMenu("❓ Trợ Giúp");
        mnuHelp.setMnemonic('H');
        
        JMenuItem itemHelp = new JMenuItem("📖 Hướng Dẫn Sử Dụng");
        itemHelp.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Tham khảo file USER_GUIDE.md trong thư mục dự án\n\n" +
                "Hoặc mở README.md để biết thêm chi tiết.",
                "Hướng Dẫn",
                JOptionPane.INFORMATION_MESSAGE);
        });
        mnuHelp.add(itemHelp);
        
        mnuHelp.addSeparator();
        
        JMenuItem itemShortcuts = new JMenuItem("⌨️ Phím Tắt");
        itemShortcuts.addActionListener(e -> {
            JTextArea ta = new JTextArea(
                "⌨️ PHÍM TẮT\n\n" +
                "Alt+F - Menu File\n" +
                "Alt+C - Menu Chức Năng\n" +
                "Alt+T - Menu Công Cụ\n" +
                "Alt+H - Menu Trợ Giúp\n" +
                "Ctrl+Q - Thoát ứng dụng\n"
            );
            ta.setEditable(false);
            ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane sp = new JScrollPane(ta);
            sp.setPreferredSize(new Dimension(350, 250));
            JOptionPane.showMessageDialog(this, sp, "Phím Tắt", JOptionPane.INFORMATION_MESSAGE);
        });
        mnuHelp.add(itemShortcuts);
        
        mnuHelp.addSeparator();
        
        JMenuItem itemAbout = new JMenuItem("ℹ️ Về Chương Trình");
        itemAbout.addActionListener(e -> {
            JTextArea ta = new JTextArea(
                "📌 HỆ THỐNG QUẢN LÝ NGHIÊN CỨU KHOA HỌC\n\n" +
                "Version: 1.0.0\n" +
                "Developer: CNPM_Nhom5\n" +
                "Technology: Java Swing + MySQL + MVC\n\n" +
                "Đây là hệ thống quản lý nghiên cứu khoa học\n" +
                "của trường đại học được phát triển\n" +
                "theo mô hình MVC hoàn chỉnh.\n\n" +
                "Ngôn Ngữ: Tiếng Việt\n" +
                "Mục Đích: Giáo Dục & Ứng Dụng"
            );
            ta.setEditable(false);
            ta.setFont(new Font("Arial", Font.PLAIN, 12));
            JScrollPane sp = new JScrollPane(ta);
            sp.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(this, sp, "Về Chương Trình", JOptionPane.INFORMATION_MESSAGE);
        });
        mnuHelp.add(itemAbout);
        
        menuBar.add(mnuFile);
        menuBar.add(mnuFeatures);
        menuBar.add(mnuTools);
        menuBar.add(mnuHelp);
        
        return menuBar;
    }
}
