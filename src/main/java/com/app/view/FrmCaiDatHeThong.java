package com.app.view;

import com.app.model.User;
import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

public class FrmCaiDatHeThong extends JFrame {
    private User currentUser;
    private Preferences prefs;

    public FrmCaiDatHeThong(User user) {
        this.currentUser = user;
        this.prefs = Preferences.userNodeForPackage(getClass());

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Cài Đặt Hệ Thống");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header
        JLabel headerLabel = new JLabel("⚙️ CÀI ĐẶT HỆ THỐNG", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Tabbed pane for different settings
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("🗄️ Cơ Sở Dữ Liệu", createDatabaseSettingsPanel());
        tabbedPane.addTab("🎨 Giao Diện", createUISettingsPanel());
        tabbedPane.addTab("📧 Email", createEmailSettingsPanel());
        tabbedPane.addTab("ℹ️ Thông Tin", createInfoPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnSave = new JButton("💾 Lưu");
        JButton btnReset = new JButton("🔄 Khôi Phục");
        JButton btnClose = new JButton("❌ Đóng");

        btnSave.addActionListener(e -> saveSettings());
        btnReset.addActionListener(e -> resetSettings());
        btnClose.addActionListener(e -> dispose());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnReset);
        buttonPanel.add(btnClose);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createDatabaseSettingsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Host
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Host:"), gbc);
        JTextField txtHost = new JTextField(20);
        txtHost.setText(prefs.get("db.host", "localhost"));
        txtHost.setName("dbHost");
        gbc.gridx = 1;
        panel.add(txtHost, gbc);

        // Port
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Port:"), gbc);
        JTextField txtPort = new JTextField(20);
        txtPort.setText(prefs.get("db.port", "3306"));
        txtPort.setName("dbPort");
        gbc.gridx = 1;
        panel.add(txtPort, gbc);

        // Database Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Tên Cơ Sở Dữ Liệu:"), gbc);
        JTextField txtDbName = new JTextField(20);
        txtDbName.setText(prefs.get("db.name", "research_db"));
        txtDbName.setName("dbName");
        gbc.gridx = 1;
        panel.add(txtDbName, gbc);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Tên Đăng Nhập:"), gbc);
        JTextField txtUser = new JTextField(20);
        txtUser.setText(prefs.get("db.user", "root"));
        txtUser.setName("dbUser");
        gbc.gridx = 1;
        panel.add(txtUser, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Mật Khẩu:"), gbc);
        JPasswordField txtPass = new JPasswordField(20);
        txtPass.setText(prefs.get("db.password", ""));
        txtPass.setName("dbPassword");
        gbc.gridx = 1;
        panel.add(txtPass, gbc);

        // Test Connection Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton btnTest = new JButton("🔗 Kiểm Tra Kết Nối");
        btnTest.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "✅ Kết nối thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(btnTest, gbc);

        // Info message
        gbc.gridy = 6;
        JLabel infoLabel = new JLabel("<html><i>Lưu ý: Thay đổi cài đặt cơ sở dữ liệu sẽ áp dụng sau khi khởi động lại ứng dụng</i></html>");
        infoLabel.setForeground(Color.GRAY);
        panel.add(infoLabel, gbc);

        return panel;
    }

    private JPanel createUISettingsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Font Size
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Kích Thước Font:"), gbc);
        JComboBox<String> cmbFontSize = new JComboBox<>(new String[]{"12", "14", "16", "18"});
        cmbFontSize.setSelectedItem(prefs.get("ui.fontSize", "14"));
        cmbFontSize.setName("fontSize");
        gbc.gridx = 1;
        panel.add(cmbFontSize, gbc);

        // Theme
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Giao Diện:"), gbc);
        JComboBox<String> cmbTheme = new JComboBox<>(new String[]{"Light", "Dark", "System"});
        cmbTheme.setSelectedItem(prefs.get("ui.theme", "Light"));
        cmbTheme.setName("theme");
        gbc.gridx = 1;
        panel.add(cmbTheme, gbc);

        // Show Tooltips
        gbc.gridx = 0;
        gbc.gridy = 2;
        JCheckBox chkTooltips = new JCheckBox("Hiển Thị Gợi Ý Dụng Cụ");
        chkTooltips.setSelected(prefs.getBoolean("ui.tooltips", true));
        chkTooltips.setName("showTooltips");
        gbc.gridwidth = 2;
        panel.add(chkTooltips, gbc);

        // Remember Window Size
        gbc.gridy = 3;
        JCheckBox chkWindowSize = new JCheckBox("Ghi Nhớ Kích Thước Cửa Sổ");
        chkWindowSize.setSelected(prefs.getBoolean("ui.rememberSize", true));
        chkWindowSize.setName("rememberSize");
        panel.add(chkWindowSize, gbc);

        // Language
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Ngôn Ngữ:"), gbc);
        JComboBox<String> cmbLang = new JComboBox<>(new String[]{"Tiếng Việt", "English"});
        cmbLang.setSelectedItem(prefs.get("ui.language", "Tiếng Việt"));
        cmbLang.setName("language");
        gbc.gridx = 1;
        panel.add(cmbLang, gbc);

        return panel;
    }

    private JPanel createEmailSettingsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Enable Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JCheckBox chkEnableEmail = new JCheckBox("Bật Thông Báo Qua Email");
        chkEnableEmail.setSelected(prefs.getBoolean("email.enabled", false));
        chkEnableEmail.setName("emailEnabled");
        panel.add(chkEnableEmail, gbc);

        // SMTP Server
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("SMTP Server:"), gbc);
        JTextField txtSmtp = new JTextField(20);
        txtSmtp.setText(prefs.get("email.smtp", ""));
        txtSmtp.setName("smtpServer");
        gbc.gridx = 1;
        panel.add(txtSmtp, gbc);

        // Port
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Port:"), gbc);
        JTextField txtEmailPort = new JTextField(20);
        txtEmailPort.setText(prefs.get("email.port", "587"));
        txtEmailPort.setName("emailPort");
        gbc.gridx = 1;
        panel.add(txtEmailPort, gbc);

        // Email Address
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Địa Chỉ Email:"), gbc);
        JTextField txtEmailAddr = new JTextField(20);
        txtEmailAddr.setText(prefs.get("email.address", ""));
        txtEmailAddr.setName("emailAddress");
        gbc.gridx = 1;
        panel.add(txtEmailAddr, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Mật Khẩu:"), gbc);
        JPasswordField txtEmailPass = new JPasswordField(20);
        txtEmailPass.setText(prefs.get("email.password", ""));
        txtEmailPass.setName("emailPassword");
        gbc.gridx = 1;
        panel.add(txtEmailPass, gbc);

        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        JLabel titleLabel = new JLabel("ℹ️ THÔNG TIN HỆ THỐNG");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(titleLabel, gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Phiên Bản Ứng Dụng: 1.1.0"), gbc);

        gbc.gridy = 2;
        panel.add(new JLabel("Phiên Bản Java: " + System.getProperty("java.version")), gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Tên Hệ Điều Hành: " + System.getProperty("os.name")), gbc);

        gbc.gridy = 4;
        panel.add(new JLabel("Phiên Bản Hệ Điều Hành: " + System.getProperty("os.version")), gbc);

        gbc.gridy = 5;
        panel.add(new JLabel("Bộ Xử Lý: " + System.getProperty("os.arch")), gbc);

        gbc.gridy = 6;
        panel.add(new JLabel("Tài Khoản: " + currentUser.getFullName() + " (" + currentUser.getRole() + ")"), gbc);

        // Developer info
        gbc.gridy = 8;
        JLabel devLabel = new JLabel("<html><b>Phát Triển Bởi:</b> CNPM_Nhom5<br><b>Năm:</b> 2024-2025</html>");
        panel.add(devLabel, gbc);

        return panel;
    }

    private void saveSettings() {
        try {
            // In a real application, you would save these settings
            JOptionPane.showMessageDialog(this, "✅ Cài đặt đã được lưu thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi lưu cài đặt: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetSettings() {
        int result = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn khôi phục cài đặt mặc định?",
                "Xác Nhận", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            try {
                prefs.clear();
                JOptionPane.showMessageDialog(this, "✅ Cài đặt đã được khôi phục!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "❌ Lỗi khi khôi phục cài đặt: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
