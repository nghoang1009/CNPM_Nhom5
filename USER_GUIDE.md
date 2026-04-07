# 📖 Hướng Dẫn Sử Dụng Hệ Thống

## 🔐 1. Đăng Nhập (FrmDangNhap)

### Bước 1: Khởi Động Ứng Dụng
- Chạy Main.java
- Cửa sổ Đăng Nhập xuất hiện

### Bước 2: Nhập Thông Tin
1. Nhập **Tên đăng nhập** (Username)
2. Nhập **Mật khẩu** (Password)
3. Nhấn nút **"Đăng Nhập"**

### Tài Khoản Mẫu:
```
Vai Trò      | Username   | Password
-------------|-----------|----------
Admin        | admin      | admin123
Lecturer 1   | lecturer1  | pass123
Lecturer 2   | lecturer2  | pass123
Student 1    | student1   | pass123
Student 2    | student2   | pass123
Student 3    | student3   | pass123
Student 4    | student4   | pass123
Reviewer 1   | reviewer1  | pass123
Reviewer 2   | reviewer2  | pass123
```

### Các Nút:
- **Đăng Nhập:** Xác thực tài khoản
- **Đăng Ký:** Tạo tài khoản mới
- **Thoát:** Đóng ứng dụng

---

## 📝 2. Đăng Ký Tài Khoản (FrmDangKi)

### Bước 1: Nhấn "Đăng Ký" trên form Đăng Nhập

### Bước 2: Điền Thông Tin
- **Tên đăng nhập:** Phải duy nhất
- **Mật khẩu:** Nhập ít nhất 6 ký tự
- **Xác nhận mật khẩu:** Phải trùng mật khẩu
- **Họ và tên:** Tên đầy đủ
- **Email:** Địa chỉ email
- **Số điện thoại:** SĐT liên lạc (tùy chọn)
- **Khoa/Bộ môn:** Khoa học công tệ, Công nghệ thông tin, v.v.
- **Vai trò:** Chọn STUDENT hoặc LECTURER

### Bước 3: Nhấn "Đăng Ký"
- Nếu thành công → Tự động quay lại Đăng Nhập
- Nếu lỗi → Xem thông báo lỗi (username trùng, mật khẩu không match, v.v.)

### Các Nút:
- **Đăng Ký:** Tạo tài khoản
- **Xóa:** Xóa hết nội dung form
- **Quay Lại:** Quay lại form Đăng Nhập

---

## 🏠 3. Trang Chủ (FrmTrangChu)

Sau khi đăng nhập thành công, bạn vào Trang Chủ.

### Thông Tin Hiển Thị
- Tên đầu đủ của người dùng
- Vai trò của người dùng (ADMIN/LECTURER/STUDENT/REVIEWER)

### Các Nút Điều Hướng (Khác nhau theo Vai Trò):

#### 👨‍🎓 Nếu Là STUDENT:
1. **Thông Tin Cá Nhân** → FrmThongTinCaNhan
2. **Đăng Ký Đề Tài** → FrmDangKiDeTai
3. **Xem Đề Tài** → FrmChiTietDeTai
4. **Đề Cương Của Tôi** → FrmChamDeCuong (xem feedback)
5. **Đăng Xuất** → Quay lại Đăng Nhập

#### 👨‍🏫 Nếu Là LECTURER:
1. **Thông Tin Cá Nhân** → FrmThongTinCaNhan
2. **Quản Lý Đề Tài** → Tạo/sửa đề tài
3. **Xem Đề Tài** → FrmChiTietDeTai
4. **Chấm Đề Cương** → FrmChamDeCuong
5. **Đăng Xuất**

#### 🔑 Nếu Là ADMIN:
1. **Quản Lý Người Dùng** → Quản lý tài khoản
2. **Quản Lý Đề Tài** → FrmDangKiDeTai
3. **Quản Lý Hội Đồng** → FrmPhanCongHoiDong
4. **Đăng Xuất**

#### 👔 Nếu Là REVIEWER:
1. **Thông Tin Cá Nhân** → FrmThongTinCaNhan
2. **Chấm Đề Cương** → FrmChamDeCuong
3. **Đăng Xuất**

---

## 👤 4. Thông Tin Cá Nhân (FrmThongTinCaNhan)

### Xem Thông Tin
- **Tên đăng nhập:** Không thể sửa
- **Họ và tên:** Có thể cập nhật
- **Email:** Có thể cập nhật
- **Số điện thoại:** Có thể cập nhật
- **Khoa/Bộ môn:** Có thể cập nhật

### Bước Cập Nhật:
1. Sửa chỗ muốn thay đổi
2. Nhấn **"Cập Nhật"**
3. Nhận thông báo thành công

### Các Nút:
- **Cập Nhật:** Lưu thay đổi
- **Hủy:** Đóng cửa sổ

---

## 📋 5. Đăng Ký Đề Tài (FrmDangKiDeTai)

### Cho STUDENT:
#### Bước 1: Xem Danh Sách Đề Tài
- Danh sách hiển thị tất cả đề tài được **APPROVED**
- Cột: ID, Tiêu Đề, Mô Tả, Giảng Viên, Lĩnh Vực, Trạng Thái, Max Thành Viên

#### Bước 2: Chọn Đề Tài
- Click vào hàng muốn chọn
- Hàng sẽ được highlight

#### Bước 3: Đăng Ký
1. Nhấn **"Đăng Ký"**
2. Nhận thông báo "Đăng ký đề tài thành công!"
3. Trạng thái: PENDING → Chờ giảng viên duyệt

#### Các Nút:
- **Đăng Ký:** Đăng ký đề tài đã chọn
- **Làm Mới:** Refresh danh sách
- **Đóng:** Đóng cửa sổ

### Cho LECTURER:
- Có thể tạo đề tài mới tại đây (nếu có chức năng tạo)
- Xem các đề tài của mình

---

## 📄 6. Chi Tiết Đề Tài (FrmChiTietDeTai)

### Giao Diện Hai Phần:

#### Phần Trên: Danh Sách Đề Tài
- Bảng hiển thị: ID, Tiêu Đề, Trạng Thái, Lĩnh Vực
- Tất cả đề tài trong hệ thống

#### Phần Dưới: Chi Tiết
- Click vào đề tài hoặc nhấn **"Xem Chi Tiết"**
- Hiển thị toàn bộ thông tin:
  - ID, Tiêu Đề, Lĩnh Vực, Trạng Thái
  - Số thành viên tối đa
  - Giảng viên hướng dẫn
  - **Mô Tả** (phần chính, chi tiết về đề tài)

### Các Nút:
- **Xem Chi Tiết:** Hiển thị chi tiết đề tài được chọn
- **Đóng:** Đóng cửa sổ

---

## 👥 7. Phân Công Đề Tài (FrmPhanCongDeTai)

**Dành cho: ADMIN, LECTURER**

### Bảng Danh Sách:
| Cột | Ý Nghĩa |
|-----|---------|
| ID | ID phân công |
| Đề Tài ID | ID đề tài |
| Sinh Viên | Tên sinh viên đăng ký |
| Trạng Thái | PENDING, ACCEPTED, REJECTED |
| Ngày Phân Công | Thời gian tạo phân công |

### Cách Sử Dụng:

#### Bước 1: Xem Danh Sách
- Tất cả yêu cầu đăng ký đề tài từ sinh viên

#### Bước 2: Chọn Phân Công
- Click vào hàng muốn xử lý

#### Bước 3: Quyết Định
1. Nhấn **"Duyệt"** → Trạng thái = ACCEPTED
2. Hoặc nhấn **"Từ Chối"** → Trạng thái = REJECTED

#### Sinh Viên Sẽ Thấy:
- ACCEPTED: Đề tài của tôi đã được duyệt
- REJECTED: Đề tài đã bị từ chối (có thể đăng ký khác)

### Các Nút:
- **Duyệt:** Chấp nhận phân công
- **Từ Chối:** Từ chối phân công
- **Đóng:** Đóng cửa sổ

---

## 👔 8. Phân Công Hội Đồng (FrmPhanCongHoiDong)

**Dành cho: ADMIN**

### Phần Trên: Nhập Liệu
- **Hội Đồng:** Chọn hội đồng từ dropdown
- **Thành Viên:** Chọn giảng viên/reviewer
- **Vai Trò:** MEMBER hoặc CHAIRMAN
- **Nút "Thêm":** Thêm phân công

### Phần Giữa: Bảng Danh Sách
| Cột | Ý Nghĩa |
|-----|---------|
| ID | ID phân công |
| Hội Đồng | Tên hội đồng |
| Thành Viên | Tên thành viên |
| Vai Trò | CHAIRMAN hoặc MEMBER |
| Ngày Phân Công | Thời gian tạo |

### Cách Sử Dụng:

#### Thêm Thành Viên:
1. Chọn Hội Đồng
2. Chọn Thành Viên
3. Chọn Vai Trò
4. Nhấn **"Thêm"** → Nhập thành công

#### Xóa Thành Viên:
1. Click vào hàng muốn xóa
2. Nhấn **"Xóa"** → Xóa thành công

### Ghi Chú:
- Mỗi hội đồng cần 1 Chủ tịch (CHAIRMAN)
- Các thành viên khác là MEMBER
- Có thể thay đổi vai trò sau

---

## ⭐ 9. Chấm Đề Cương (FrmChamDeCuong)

**Dành cho: LECTURER, REVIEWER**

### Giao Diện Chia Thành 2 Phần:

#### Bên Trái: Danh Sách Đề Cương
| Cột | Ý Nghĩa |
|-----|---------|
| ID | ID proposal |
| Đề Tài ID | Đề tài mà proposal thuộc về |
| Sinh Viên | Tên sinh viên |
| Trạng Thái | DRAFT, SUBMITTED, APPROVED, REJECTED |

#### Bên Phải: 2 Tab

**Tab 1: Nội Dung**
- Hiển thị nội dung đề cương của sinh viên
- Read-only (chỉ xem, không sửa)

**Tab 2: Chấm Điểm**
- **Điểm (0-10):** Spinner để chọn điểm
- **Nhận Xét:** Text area nhập feedback
- **Nút "Chấm Điểm":** Lưu điểm

### Cách Sử Dụng:

#### Bước 1: Xem Danh Sách Đề Cương
- Click vào hàng để xem nội dung

#### Bước 2: Xem Nội Dung (Tab 1)
- Tự động hiển thị khi click
- Đọc nội dung đề cương

#### Bước 3: Chấm Điểm (Tab 2)
1. Chọn Điểm (0-10)
   - Ví dụ: 8.5, 9.0, 10.0
2. Nhập Nhận Xét
   - Ví dụ: "Rất tốt, nên bổ sung phần..."
3. Nhấn **"Chấm Điểm"**
4. Nhận thông báo "Chấm điểm thành công!"

#### Bước 4: Xem Điểm Từ Reviewer Khác
1. Chọn proposal
2. Nhấn **"Xem Điểm"**
3. Xem tất cả điểm và nhận xét từ các reviewer

### Các Nút:
- **Xem Điểm:** Hiển thị tất cả điểm
- **Đóng:** Đóng cửa sổ

### Ghi Chú:
- Có thể chấm lại nếu cần (ghi đè điểm cũ)
- Mỗi reviewer có thể chấm 1 lần
- Sinh viên có thể xem tất cả feedback

---

## 💡 Tips & Tricks

### Tip 1: Kiểm Tra Cơ Sở Dữ Liệu
```sql
-- Xem tất cả users
SELECT * FROM users;

-- Xem tất cả đề tài
SELECT * FROM research_topics;

-- Xem tất cả điểm
SELECT * FROM grades;
```

### Tip 2: Reset Database
```bash
mysql -u root -p < database/init_database.sql
```

### Tip 3: Thay Đổi Tài Khoản Mặc Định
Chỉnh sửa file `database/init_database.sql` trước khi chạy script.

### Tip 4: Tìm Kiếm
- Dùng Ctrl+F trên bảng để tìm nhanh

### Tip 5: Export Data
- Chế độ clipboard: Copy từ bảng để paste sang Excel

---

## 🐛 Xử Lý Lỗi Thường Gặp

### Lỗi 1: "Cannot connect to database"
```
Giải pháp:
1. Kiểm tra MySQL đang chạy
2. Kiểm tra tài khoản root/mật khẩu
3. Kiểm tra DatabaseConnection.java
```

### Lỗi 2: "Username already exists"
```
Giải pháp:
- Chọn username khác (unique)
- Hoặc reset database
```

### Lỗi 3: "Password doesn't match in confirmation"
```
Giải pháp:
- Gõ mật khẩu giống nhau ở 2 ô
- Hoặc nhấn Xóa để reset form
```

### Lỗi 4: "No data to display"
```
Giải pháp:
- Tạo dữ liệu mới
- Hoặc reset database với dữ liệu mẫu
```

---

## 📊 Ví Dụ Kịch Bản Thực Tế

### Kịch Bản 1: Sinh Viên Đăng Ký Đề Tài
1. Đăng nhập: student1/pass123
2. Click "Đăng Ký Đề Tài"
3. Chọn "Hệ thống quản lý học tập trực tuyến"
4. Click "Đăng Ký"
5. Trạng thái: PENDING
6. Chờ giảng viên duyệt

### Kịch Bản 2: Giảng Viên Duyệt Đề Tài
1. Đăng nhập: lecturer1/pass123
2. Click "Phân Công Đề Tài"
3. Chọn phân công của student1
4. Click "Duyệt"
5. Trạng thái: ACCEPTED

### Kịch Bản 3: Reviewer Chấm Đề Cương
1. Đăng nhập: reviewer1/pass123
2. Click "Chấm Đề Cương"
3. Chọn proposal của student1
4. Xem Tab "Nội Dung"
5. Click Tab "Chấm Điểm"
6. Nhập Điểm: 8.5
7. Nhập Nhận Xét: "Tốt"
8. Click "Chấm Điểm"

---

## 🎓 Bài Tập Thực Hành

### Bài 1: Làm Quen Với Hệ Thống
- [ ] Đăng nhập với 4 vai trò khác nhau
- [ ] Xem giao diện cho mỗi vai trò

### Bài 2: Quản Lý Dữ Liệu
- [ ] Tạo sinh viên mới
- [ ] Tạo giảng viên mới
- [ ] Tạo đề tài mới (nếu có chức năng)

### Bài 3: Quy Trình Đầy Đủ
- [ ] Student đăng ký đề tài
- [ ] Lecturer duyệt
- [ ] Student submit proposal
- [ ] Reviewer chấm

### Bài 4: Báo Cáo
- [ ] Xem tất cả users trong database
- [ ] Xem tất cả topics
- [ ] Xem tất cả grades

---

**🎉 Chúc bạn sử dụng hệ thống thành công!**

Nếu có câu hỏi, vui lòng tham khảo README.md hoặc liên hệ nhóm phát triển.

