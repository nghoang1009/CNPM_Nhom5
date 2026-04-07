# Hệ Thống Quản Lý Nghiên Cứu Khoa Học

Ứng dụng quản lý nghiên cứu khoa học của trường đại học sử dụng Java Swing và mô hình MVC.

## Yêu Cầu Hệ Thống

- Java 11+
- MySQL 5.7+
- Maven 3.6+

## Cài Đặt

### 1. Tạo Database

**Cách 1: Sử dụng MySQL Command Line**
```bash
mysql -u root -p < database/init_database.sql
```

**Cách 2: Sử dụng MySQL Workbench**
- Mở MySQL Workbench
- Kết nối đến MySQL server
- File > Open SQL Script > Chọn file `database/init_database.sql`
- Nhấn Execute

### 2. Cấu Hình Kết Nối Database

Sửa file `src/main/java/com/app/util/DatabaseConnection.java`:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/research_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "root";
```

Thay đổi `DB_USER`, `DB_PASSWORD` và `DB_URL` nếu cần thiết.

### 3. Cài Đặt Dependencies

```bash
mvn clean install
```

### 4. Chạy Ứng Dụng

**Cách 1: Sử dụng IDE (IntelliJ, Eclipse, NetBeans)**
- Mở project
- Chạy class `com.app.Main`

**Cách 2: Sử dụng Maven**
```bash
mvn compile exec:java -Dexec.mainClass="com.app.Main"
```

**Cách 3: Build và chạy JAR**
```bash
mvn clean package
java -jar target/research-management.jar
```

## Tài Khoản Mặc Định

### Admin
- **Username:** admin
- **Password:** admin123
- **Vai Trò:** ADMIN

### Giảng Viên
- **Username:** lecturer1
- **Password:** pass123
- **Vai Trò:** LECTURER

- **Username:** lecturer2
- **Password:** pass123
- **Vai Trò:** LECTURER

### Sinh Viên
- **Username:** student1
- **Password:** pass123
- **Vai Trò:** STUDENT

- **Username:** student2
- **Password:** pass123
- **Vai Trò:** STUDENT

- **Username:** student3
- **Password:** pass123
- **Vai Trò:** STUDENT

- **Username:** student4
- **Password:** pass123
- **Vai Trò:** STUDENT

### Reviewer (Hội Đồng Chấm)
- **Username:** reviewer1
- **Password:** pass123
- **Vai Trò:** REVIEWER

- **Username:** reviewer2
- **Password:** pass123
- **Vai Trò:** REVIEWER

## Các Tính Năng Chính

### 1. Đăng Nhập / Đăng Ký (frmDangNhap, frmDangKi)
- Đăng nhập với tên đăng nhập và mật khẩu
- Đăng ký tài khoản mới
- Xác thực mật khẩu

### 2. Trang Chủ (frmTrangChu)
- Giao diện chính với menu điều hướng
- Hiển thị thông tin người dùng
- Điều hướng theo vai trò (Sinh viên, Giảng viên, Admin, Reviewer)

### 3. Thông Tin Cá Nhân (frmThongTinCaNhan)
- Xem thông tin cá nhân
- Cập nhật email, số điện thoại, khoa/bộ môn

### 4. Đăng Ký Đề Tài (frmDangKiDeTai)
- Xem danh sách đề tài đã được phê duyệt
- Sinh viên đăng ký đề tài
- Giảng viên tạo đề tài mới

### 5. Chi Tiết Đề Tài (frmChiTietDeTai)
- Xem chi tiết từng đề tài
- Xem mô tả, lĩnh vực, số lượng thành viên tối đa

### 6. Phân Công Đề Tài (frmPhanCongDeTai)
- Duyệt hoặc từ chối đăng ký đề tài của sinh viên
- Cập nhật trạng thái phân công

### 7. Phân Công Hội Đồng (frmPhanCongHoiDong)
- Tạo hội đồng chấm
- Thêm/xóa thành viên hội đồng
- Xác định vai trò (Chủ tịch, Thành viên)

### 8. Chấm Đề Cương (frmChamDeCuong)
- Xem danh sách đề cương cần chấm
- Xem nội dung đề cương
- Chấm điểm và nhập nhận xét
- Xem chi tiết điểm từ các reviewer khác

## Cấu Trúc Project

```
CNPM_Nhom5/
├── pom.xml                          # Maven configuration
├── database/
│   └── init_database.sql           # Script khởi tạo database
├── src/
│   └── main/
│       ├── java/
│       │   └── com/app/
│       │       ├── Main.java                # Class khởi động
│       │       ├── model/                  # Model classes
│       │       │   ├── User.java
│       │       │   ├── ResearchTopic.java
│       │       │   ├── TopicAssignment.java
│       │       │   ├── ReviewBoard.java
│       │       │   ├── BoardAssignment.java
│       │       │   ├── Proposal.java
│       │       │   └── Grade.java
│       │       ├── dao/                    # Database Access Objects
│       │       │   ├── UserDAO.java
│       │       │   ├── ResearchTopicDAO.java
│       │       │   ├── TopicAssignmentDAO.java
│       │       │   ├── ReviewBoardDAO.java
│       │       │   ├── BoardAssignmentDAO.java
│       │       │   ├── ProposalDAO.java
│       │       │   └── GradeDAO.java
│       │       ├── view/                   # Swing Forms
│       │       │   ├── FrmDangNhap.java
│       │       │   ├── FrmDangKi.java
│       │       │   ├── FrmTrangChu.java
│       │       │   ├── FrmThongTinCaNhan.java
│       │       │   ├── FrmDangKiDeTai.java
│       │       │   ├── FrmChiTietDeTai.java
│       │       │   ├── FrmPhanCongDeTai.java
│       │       │   ├── FrmPhanCongHoiDong.java
│       │       │   └── FrmChamDeCuong.java
│       │       └── util/                   # Utility classes
│       │           └── DatabaseConnection.java
│       └── resources/                      # Resources
└── README.md                        # This file
```

## Mô Hình MVC

**Model:** Các lớp entity (User, ResearchTopic, Proposal, Grade, etc.)

**View:** Các form Swing (Frm*.java)

**Controller:** Logic xử lý trong các DAO classes

**Database:** MySQL (research_db)

## Hỗ Trợ CRUD

Tất cả các entities đều có đầy đủ các chức năng CRUD:

- **Create (C):** Thêm mới dữ liệu
- **Read (R):** Lấy dữ liệu
- **Update (U):** Cập nhật dữ liệu
- **Delete (D):** Xóa dữ liệu

## Các Bảng Database

### users
- Lưu thông tin người dùng (Admin, Lecturer, Student, Reviewer)

### research_topics
- Lưu thông tin đề tài nghiên cứu

### topic_assignments
- Lưu phân công đề tài để sinh viên

### review_boards
- Lưu thông tin hội đồng chấm

### board_assignments
- Lưu phân công thành viên vào hội đồng

### proposals
- Lưu đề cương/đề tài của sinh viên

### grades
- Lưu điểm số chấm từ các reviewer

## Xử Lý Lỗi

- Kiểm tra kết nối database
- Xác thực dữ liệu đầu vào
- Thông báo lỗi chi tiết cho người dùng

## Tính Năng Nâng Cao (Có Thể Mở Rộng)

1. Xuất báo cáo PDF
2. Gửi email thông báo
3. Tìm kiếm và lọc nâng cao
4. Phân quyền chi tiết hơn
5. Lịch sử thay đổi dữ liệu
6. Backup database tự động
7. Giao diện web (JSP/Spring)
8. Mobile app
9. Dashboard thống kê
10. API REST

## Troubleshooting

### Lỗi Kết Nối Database
- Kiểm tra MySQL server đang chạy
- Kiểm tra tài khoản MySQL
- Kiểm tra cấu hình trong DatabaseConnection.java

### Lỗi Import Class
- Chạy `mvn clean install` để tải dependencies
- Rebuild project

### Lỗi Giao Diện
- Cập nhật Look and Feel nếu cần
- Kiểm tra phiên bản Java

## Liên Hệ & Hỗ Trợ

Liên hệ nhóm phát triển để được hỗ trợ thêm.

## License

Dự án này được phát triển cho mục đích giáo dục.

---

**Version:** 1.0.0  
**Last Updated:** 2024  
**Java Version:** 11+  
**MySQL Version:** 5.7+
