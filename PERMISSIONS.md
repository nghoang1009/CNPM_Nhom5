# 🔒 Hệ Thống Phân Quyền (Permission Management)

## 📌 Tổng Quan

Hệ thống phân quyền quản lý các hành động mà mỗi người dùng (role) có thể thực hiện trong hệ thống.

---

## 🎯 Các Vai Trò (Roles)

### 1. 👑 ADMIN (Quản Trị Viên)
**Quyền Hạn Tối Cao:**
- ✅ Quản lý tất cả người dùng (xem, thêm, sửa, xóa)
- ✅ Quản lý tất cả đề tài (xem, thêm, sửa, xóa)
- ✅ Duyệt/từ chối đăng ký đề tài
- ✅ Quản lý hội đồng chấm
- ✅ Thêm/xóa thành viên hội đồng
- ✅ Xem tất cả đề cương và điểm số
- ✅ Xuất báo cáo
- ✅ Cài đặt hệ thống

**Danh Sách Quyền:**
```
VIEW_USERS, ADD_USER, EDIT_USER, DELETE_USER
VIEW_TOPICS, ADD_TOPIC, EDIT_TOPIC, DELETE_TOPIC
MANAGE_ASSIGNMENTS, APPROVE_ASSIGNMENT, REJECT_ASSIGNMENT
MANAGE_BOARDS, ADD_BOARD, EDIT_BOARD, DELETE_BOARD
MANAGE_BOARD_MEMBERS
VIEW_ALL_PROPOSALS, VIEW_ALL_GRADES
EXPORT_REPORTS, SYSTEM_SETTINGS
```

---

### 2. 👨‍🏫 LECTURER (Giảng Viên)
**Quyền Hạn Vừa Phải:**
- ✅ Xem/sửa thông tin cá nhân
- ✅ Tạo đề tài mới
- ✅ Sửa đề tài của mình
- ✅ Xem danh sách những sinh viên đăng ký
- ✅ Duyệt/từ chối đăng ký sinh viên
- ✅ Chấm đề cương của sinh viên
- ✅ Xem phản hồi từ reviewers
- ✅ Xuất báo cáo của riêng mình

**Danh Sách Quyền:**
```
VIEW_PROFILE, EDIT_PROFILE
CREATE_TOPIC, EDIT_OWN_TOPIC, VIEW_OWN_TOPICS
VIEW_TOPIC_ASSIGNMENTS
APPROVE_ASSIGNMENT, REJECT_ASSIGNMENT
GRADE_PROPOSALS, VIEW_STUDENT_PROPOSALS
EXPORT_REPORT
```

---

### 3. 👨‍🎓 STUDENT (Sinh Viên)
**Quyền Hạn Cơ Bản:**
- ✅ Xem/sửa thông tin cá nhân
- ✅ Xem tất cả đề tài đã được phê duyệt
- ✅ Đăng ký 1-2 đề tài
- ✅ Xem trạng thái đăng ký (pending, accepted, rejected)
- ✅ Tạo & sửa đề cương
- ✅ Submit đề cương
- ✅ Xem điểm số từ reviewers
- ✅ Xem nhận xét chi tiết

**Danh Sách Quyền:**
```
VIEW_PROFILE, EDIT_PROFILE
VIEW_ALL_TOPICS, REGISTER_TOPIC
VIEW_OWN_ASSIGNMENTS
VIEW_OWN_PROPOSALS, CREATE_PROPOSAL, EDIT_PROPOSAL, SUBMIT_PROPOSAL
VIEW_GRADES
```

---

### 4. 📋 REVIEWER (Hội Đồng Chấm)
**Quyền Hạn Giới Hạn:**
- ✅ Xem/sửa thông tin cá nhân
- ✅ Xem danh sách đề cương được giao
- ✅ Chấm điểm & nhập nhận xét
- ✅ Xem lịch sử chấm của mình

**Danh Sách Quyền:**
```
VIEW_PROFILE, EDIT_PROFILE
VIEW_ASSIGNED_PROPOSALS, GRADE_PROPOSALS
VIEW_OWN_GRADES
```

---

## 💻 Cách Sử Dụng PermissionManager

### Kiểm Tra Quyền

#### 1. Kiểm Tra Một Quyền Cụ Thể
```java
if (PermissionManager.hasPermission(currentUser, "ADD_TOPIC")) {
    // Cho phép thêm đề tài
}
```

#### 2. Kiểm Tra Một Trong Các Quyền
```java
if (PermissionManager.hasAnyPermission(currentUser, 
    "VIEW_TOPICS", "EDIT_TOPIC")) {
    // Cho phép xem hoặc sửa đề tài
}
```

#### 3. Kiểm Tra Tất Cả Các Quyền
```java
if (PermissionManager.hasAllPermissions(currentUser, 
    "ADD_TOPIC", "DELETE_TOPIC")) {
    // Chỉ thực hiện khi có CẢ hai quyền
}
```

#### 4. Kiểm Tra Nhanh Theo Role
```java
if (PermissionManager.isAdmin(currentUser)) {
    // Admin code
}

if (PermissionManager.isLecturer(currentUser)) {
    // Lecturer code
}

if (PermissionManager.isStudent(currentUser)) {
    // Student code
}

if (PermissionManager.isReviewer(currentUser)) {
    // Reviewer code
}
```

---

## 🎨 Giao Diện Menu Bar

### Menu Structure:

```
📁 File
├── 👤 Thông Tin Cá Nhân
├── ────────────────
├── 🚪 Đăng Xuất
├── ────────────────
└── ⏻ Thoát Ứng Dụng

📋 Chức Năng (Thay đổi theo Role)
├── [Tùy role...]
└── ...

🛠️ Công Cụ
├── 🔄 Làm Mới
└── ⚙️ Cài Đặt

❓ Trợ Giúp
├── 📖 Hướng Dẫn Sử Dụng
├── ────────────────
├── ⌨️ Phím Tắt
├── ────────────────
└── ℹ️ Về Chương Trình
```

---

## 📊 Bảng So Sánh Quyền

| Quyền | ADMIN | LECTURER | STUDENT | REVIEWER |
|-------|-------|----------|---------|----------|
| Quản lý người dùng | ✅ | ❌ | ❌ | ❌ |
| Tạo đề tài | ✅ | ✅ | ❌ | ❌ |
| Sửa đề tài | ✅ | ✅* | ❌ | ❌ |
| Xóa đề tài | ✅ | ❌ | ❌ | ❌ |
| Đăng ký đề tài | ❌ | ❌ | ✅ | ❌ |
| Duyệt đăng ký | ✅ | ✅* | ❌ | ❌ |
| Chấm đề cương | ✅ | ✅ | ❌ | ✅ |
| Xem mọi điểm | ✅ | ❌ | ❌ | ❌ |
| Sửa định nghĩa quyền | ✅ | ❌ | ❌ | ❌ |

*\* = Chỉ được sửa những item của chính mình*

---

## 🔐 Bảo Mật

### Nguyên Tắc:
1. **Principle of Least Privilege** - Mỗi người dùng chỉ có quyền tối thiểu cần thiết
2. **Centralized Permission Check** - Kiểm tra quyền từ một nơi (PermissionManager)
3. **Fail Secure** - Nếu không chắc, từ chối
4. **Audit Trail** - Log các hành động quan trọng

### Thực Thi:
```java
// Mọi hành động quan trọng phải kiểm tra quyền
if (!PermissionManager.hasPermission(user, "DELETE_TOPIC")) {
    throw new SecurityException("Không có quyền xóa đề tài");
}
```

---

## 🛠️ Mở Rộng Hệ Thống Phân Quyền

### Thêm Quyền Mới:
1. Mở file `PermissionManager.java`
2. Thêm quyền vào `rolePermissions` set:

```java
// Ví dụ: Thêm quyền "SEND_EMAIL"
Set<String> adminPerms = new HashSet<>(Arrays.asList(
    // ... quyền cũ ...
    "SEND_EMAIL"  // ← Thêm dòng này
));
```

### Tạo Role Mới:
```java
// Ví dụ: Tạo role mới "DEPARTMENT_HEAD"
Set<String> depHeadPerms = new HashSet<>(Arrays.asList(
    "VIEW_ALL_TOPICS",
    "VIEW_ALL_PROPOSALS",
    "EXPORT_REPORTS",
    "MANAGE_ASSIGNMENTS"
));
rolePermissions.put("DEPARTMENT_HEAD", depHeadPerms);
```

### Ghi Đè Quyền:
```java
// Cập nhật quyền của role
Set<String> lecturerNew = PermissionManager.getPermissions("LECTURER");
lecturerNew.add("NEW_PERMISSION");
```

---

## 📝 Ví Dụ Thực Tế

### Ví Dụ 1: Giảng Viên Muốn Xóa Đề Tài

```java
User lecturer = userDAO.getUserById(2);

// Kiểm tra quyền
if (!PermissionManager.hasPermission(lecturer, "DELETE_TOPIC")) {
    JOptionPane.showMessageDialog(this, 
        "Bạn không có quyền xóa đề tài. " +
        "Chỉ Admin mới có quyền này.");
    return;
}

// Nếu có quyền, thực hiện xóa
topicDAO.deleteTopic(topicId);
```

### Ví Dụ 2: Xây Dựng Menu Động

```java
if (PermissionManager.isAdmin(currentUser)) {
    JMenuItem itemManageUsers = new JMenuItem("Quản Lý Người Dùng");
    mnuFeatures.add(itemManageUsers);
}

if (PermissionManager.isLecturer(currentUser)) {
    JMenuItem itemCreateTopic = new JMenuItem("Tạo Đề Tài");
    mnuFeatures.add(itemCreateTopic);
}
```

### Ví Dụ 3: Kiểm Tra Nhiều Quyền

```java
// Sinh viên cần cả hai quyền để submit proposal
if (PermissionManager.hasAllPermissions(currentUser,
    "EDIT_PROPOSAL", "SUBMIT_PROPOSAL")) {
    // Cho phép submit
    submitProposal();
} else {
    // Từ chối
    showError("Cần có quyền để submit proposal");
}
```

---

## 📚 Tài Liệu Thêm

- [README.md](README.md) - Hướng dẫn chính
- [USER_GUIDE.md](USER_GUIDE.md) - Hướng dẫn sử dụng
- [SETUP.md](SETUP.md) - Hướng dẫn cài đặt

---

## ❓ FAQ

### Q: Làm sao thêm quyền mới cho một role?
**A:** Chỉnh sửa `PermissionManager.java`, thêm quyền vào set tương ứng.

### Q: Có thể gán quyền từng người không?
**A:** Hiện tại là theo role. Để gán từng người, cần thêm database field `custom_permissions`.

### Q: What if password là trống?
**A:** Nó sẽ kết nối được vì DB_PASSWORD = "" (trống). OK nhé!

### Q: Làm sao reset quyền về mặc định?
**A:** Restart ứng dụng hoặc gọi `initializePermissions()` lại.

---

**✅ Hệ Thống Phân Quyền Đã Sẵn Sàng!**

