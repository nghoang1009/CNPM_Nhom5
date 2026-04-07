# 🔐 Bản Đồ Phân Quyền - UseCase Diagram

## 📊 Tổng Quan Hệ Thống

Hệ thống có **4 roles** chính:
1. **STUDENT (Sinh Viên)** - Người học
2. **LECTURER (Giảng Viên)** - Người dạy  
3. **MANAGER (Người Quản Lý)** - Người quản trị hệ thống
4. **ADMIN (Quản Trị Viên)** - Quản trị toàn hệ

---

## 📋 UseCase Mapping

### Use Case: Đăng Nhập (Login)
- **STUDENT** ✅ - Đăng nhập vào hệ thống
- **LECTURER** ✅ - Đăng nhập vào hệ thống
- **MANAGER** ✅ - Đăng nhập vào hệ thống
- **ADMIN** ✅ - Đăng nhập vào hệ thống

### Use Case: Đăng Xuất (Logout)
- **STUDENT** ✅ - Đăng xuất khỏi hệ thống
- **LECTURER** ✅ - Đăng xuất khỏi hệ thống
- **MANAGER** ✅ - Đăng xuất khỏi hệ thống
- **ADMIN** ✅ - Đăng xuất khỏi hệ thống

### Use Case: Đăng Ký Đề Tài (Register Topic)
- **STUDENT** ✅ - Đăng ký một đề tài để nghiên cứu
- **LECTURER** ❌ - Không ngoài quản lý chính
- **MANAGER** ❌ - Không đăng ký cá nhân
- **ADMIN** ❌ - Không đăng ký cá nhân

### Use Case: Quản Lý Đề Tài (Manage Topics)
- **STUDENT** ✅ - Xem danh sách đề tài
- **LECTURER** ✅ - Tạo, chỉnh sửa, xóa đề tài của mình
- **MANAGER** ✅ - Quản lý tất cả đề tài
- **ADMIN** ✅ - Quản lý tất cả đề tài

**Permissions:**
- `VIEW_ALL_TOPICS` - Xem tất cả đề tài
- `CREATE_TOPIC` - Tạo đề tài mới
- `EDIT_TOPIC` - Chỉnh sửa đề tài
- `EDIT_OWN_TOPIC` - Chỉnh sửa đề tài của mình
- `DELETE_TOPIC` - Xóa đề tài
- `MANAGE_TOPICS` - Toàn quyền quản lý

### Use Case: Quản Lý Hội Đồng (Manage Board)
- **STUDENT** ❌ - Không quản lý
- **LECTURER** ✅ - Thấy hội đồng chấm
- **MANAGER** ✅ - Quản lý hội đồng chấm điểm
- **ADMIN** ✅ - Quản lý hội đồng chấm điểm

**Permissions:**
- `MANAGE_BOARDS` - Quản lý hội đồng
- `ADD_BOARD` - Thêm hội đồng mới
- `EDIT_BOARD` - Chỉnh sửa hội đồng
- `DELETE_BOARD` - Xóa hội đồng
- `MANAGE_BOARD_MEMBERS` - Quản lý thành viên hội đồng

### Use Case: Quản Lý Tài Khoản (Manage Accounts / Users)
- **STUDENT** ❌ - Chỉ xem hồ sơ cá nhân
- **LECTURER** ❌ - Chỉ xem hồ sơ cá nhân
- **MANAGER** ✅ - Quản lý tài khoản người dùng
- **ADMIN** ✅ - Quản lý tài khoản người dùng

**Permissions:**
- `VIEW_USERS` - Xem danh sách người dùng
- `ADD_USER` - Thêm người dùng mới
- `EDIT_USER` - Chỉnh sửa thông tin người dùng
- `DELETE_USER` - Xóa người dùng
- `MANAGE_USERS` - Toàn quyền quản lý người dùng

### Use Case: Quản Lý Sinh Viên (Manage Students)
- **STUDENT** ❌ - Không quản lý
- **LECTURER** ❌ - Không quản lý
- **MANAGER** ✅ - Quản lý học sinh của mình
- **ADMIN** ✅ - Quản lý tất cả học sinh

**Permissions:**
- `MANAGE_STUDENTS` - Quản lý sinh viên

### Use Case: Quản Lý Giảng Viên (Manage Lecturers)
- **STUDENT** ❌ - Không quản lý
- **LECTURER** ❌ - Không quản lý
- **MANAGER** ✅ - Quản lý giảng viên
- **ADMIN** ✅ - Quản lý giảng viên

**Permissions:**
- `MANAGE_LECTURERS` - Quản lý giảng viên

### Use Case: Chấm Điểm Đề Tài (Grade Topics)
- **STUDENT** ❌ - Không chấm
- **LECTURER** ✅ - Chấm đề cương của sinh viên
- **MANAGER** ✅ - Chấm đề cương
- **ADMIN** ✅ - Chấm đề cương

**Permissions:**
- `GRADE_TOPICS` - Quyền chấm điểm

### Use Case: Báo Cáo & Thống Kê (Reports & Statistics)
- **STUDENT** ❌ - Chỉ xem thống kê cá nhân
- **LECTURER** ❌ - Chỉ xem thống kê cá nhân
- **MANAGER** ✅ - Xem báo cáo và thống kê toàn hệ
- **ADMIN** ✅ - Xem báo cáo và thống kê toàn hệ

**Permissions:**
- `VIEW_STATISTICS` - Xem thống kê
- `EXPORT_REPORTS` - Xuất báo cáo
- `VIEW_ALL_GRADES` - Xem tất cả điểm
- `VIEW_ALL_PROPOSALS` - Xem tất cả đề cương

---

## 🔑 Danh Sách Quyền Hoàn Chỉnh

### STUDENT (Sinh Viên) - 9 Quyền
```
✅ LOGIN                    - Đăng nhập
✅ LOGOUT                   - Đăng xuất
✅ VIEW_PROFILE             - Xem hồ sơ
✅ EDIT_PROFILE             - Chỉnh sửa hồ sơ
✅ VIEW_ALL_TOPICS          - Xem tất cả đề tài
✅ REGISTER_TOPIC           - Đăng ký đề tài
✅ VIEW_OWN_TOPICS          - Xem đề tài của tôi
✅ VIEW_OWN_ASSIGNMENTS     - Xem bài tập của tôi
✅ VIEW_GRADES              - Xem điểm
```

### LECTURER (Giảng Viên) - 11 Quyền  
```
✅ LOGIN                    - Đăng nhập
✅ LOGOUT                   - Đăng xuất
✅ VIEW_PROFILE             - Xem hồ sơ
✅ EDIT_PROFILE             - Chỉnh sửa hồ sơ
✅ CREATE_TOPIC             - Tạo đề tài
✅ EDIT_OWN_TOPIC           - Chỉnh sửa đề tài của tôi
✅ VIEW_OWN_TOPICS          - Xem đề tài của tôi
✅ VIEW_TOPIC_ASSIGNMENTS  - Xem phân công của đề tài
✅ GRADE_TOPICS             - Chấm đề tài
✅ VIEW_STUDENT_PROPOSALS   - Xem đề cương sinh viên
✅ VIEW_GRADES              - Xem điểm
```

### MANAGER (Người Quản Lý) - 21 Quyền
```
✅ LOGIN                    - Đăng nhập
✅ LOGOUT                   - Đăng xuất
✅ VIEW_PROFILE             - Xem hồ sơ
✅ EDIT_PROFILE             - Chỉnh sửa hồ sơ
✅ MANAGE_TOPICS            - Quản lý đề tài (tất cả)
✅ VIEW_ALL_TOPICS          - Xem tất cả đề tài
✅ EDIT_TOPIC               - Chỉnh sửa đề tài
✅ DELETE_TOPIC             - Xóa đề tài
✅ MANAGE_BOARDS            - Quản lý hội đồng
✅ ADD_BOARD                - Thêm hội đồng
✅ EDIT_BOARD               - Chỉnh sửa hội đồng
✅ DELETE_BOARD             - Xóa hội đồng
✅ MANAGE_BOARD_MEMBERS     - Quản lý thành viên hội đồng
✅ MANAGE_STUDENTS          - Quản lý sinh viên
✅ MANAGE_LECTURERS         - Quản lý giảng viên
✅ VIEW_USERS               - Xem danh sách người dùng
✅ EDIT_USER                - Chỉnh sửa người dùng
✅ MANAGE_USERS             - Quản lý người dùng
✅ GRADE_TOPICS             - Chấm đề tài
✅ VIEW_ALL_GRADES          - Xem tất cả điểm
✅ VIEW_ALL_PROPOSALS       - Xem tất cả đề cương
✅ EXPORT_REPORTS           - Xuất báo cáo
✅ VIEW_STATISTICS          - Xem thống kê
```

### ADMIN (Quản Trị Viên) - 31 Quyền
```
✅ LOGIN                    - Đăng nhập
✅ LOGOUT                   - Đăng xuất
✅ VIEW_PROFILE             - Xem hồ sơ
✅ EDIT_PROFILE             - Chỉnh sửa hồ sơ
✅ MANAGE_USERS             - Quản lý người dùng (toàn quyền)
✅ VIEW_USERS               - Xem danh sách người dùng
✅ ADD_USER                 - Thêm người dùng
✅ EDIT_USER                - Chỉnh sửa người dùng
✅ DELETE_USER              - Xóa người dùng
✅ MANAGE_STUDENTS          - Quản lý sinh viên
✅ MANAGE_LECTURERS         - Quản lý giảng viên
✅ MANAGE_TOPICS            - Quản lý đề tài (tất cả)
✅ VIEW_ALL_TOPICS          - Xem tất cả đề tài
✅ ADD_TOPIC                - Thêm đề tài
✅ EDIT_TOPIC               - Chỉnh sửa đề tài
✅ DELETE_TOPIC             - Xóa đề tài
✅ MANAGE_BOARDS            - Quản lý hội đồng
✅ ADD_BOARD                - Thêm hội đồng
✅ EDIT_BOARD               - Chỉnh sửa hội đồng
✅ DELETE_BOARD             - Xóa hội đồng
✅ MANAGE_BOARD_MEMBERS     - Quản lý thành viên hội đồng
✅ MANAGE_ASSIGNMENTS       - Quản lý phân công
✅ APPROVE_ASSIGNMENT       - Duyệt phân công
✅ REJECT_ASSIGNMENT        - Từ chối phân công
✅ GRADE_TOPICS             - Chấm đề tài
✅ VIEW_ALL_PROPOSALS       - Xem tất cả đề cương
✅ VIEW_ALL_GRADES          - Xem tất cả điểm
✅ EXPORT_REPORTS           - Xuất báo cáo
✅ VIEW_STATISTICS          - Xem thống kê
✅ SYSTEM_SETTINGS          - Cài đặt hệ thống
✅ MANAGE_PERMISSIONS       - Quản lý quyền hạn
```

---

## 💻 Cách Sử Dụng Trong Code

### 1. Kiểm Tra Quyền Cụ Thể
```java
if (PermissionManager.hasPermission(user, "MANAGE_TOPICS")) {
    // Cho phép quản lý đề tài
}
```

### 2. Kiểm Tra Một Trong Các Quyền
```java
if (PermissionManager.hasAnyPermission(user, 
    "GRADE_TOPICS", "VIEW_ALL_GRADES")) {
    // Cho phép xem điểm
}
```

### 3. Kiểm Tra Tất Cả Quyền
```java
if (PermissionManager.hasAllPermissions(user, 
    "MANAGE_USERS", "MANAGE_TOPICS")) {
    // Cần có cả 2 quyền
}
```

### 4. Kiểm Tra Nhanh Theo Role
```java
if (PermissionManager.isAdmin(user)) {
    // Là admin
}
if (PermissionManager.isManager(user)) {
    // Là quản lý
}
if (PermissionManager.isLecturer(user)) {
    // Là giảng viên
}
if (PermissionManager.isStudent(user)) {
    // Là sinh viên
}
```

### 5. Lấy Tất Cả Quyền Của Role
```java
Set<String> permissions = PermissionManager.getPermissions("MANAGER");
for (String perm : permissions) {
    System.out.println(perm);
}
```

---

## 🧪 Test Accounts

| Username  | Password | Role    | Ghi Chú |
|-----------|----------|---------|--------|
| admin     | admin123 | ADMIN   | Toàn quyền |
| manager1  | pass123  | MANAGER | Quản lý hệ thống |
| manager2  | pass123  | MANAGER | Quản lý hệ thống |
| lecturer1 | pass123  | LECTURER| Giảng viên |
| lecturer2 | pass123  | LECTURER| Giảng viên |
| student1  | pass123  | STUDENT | Sinh viên |
| student2  | pass123  | STUDENT | Sinh viên |

---

## 📊 Ma Trận Quyền

```
                    STUDENT | LECTURER | MANAGER | ADMIN
────────────────────────────┼──────────┼─────────┼───────
Đăng nhập              ✅   |    ✅    |   ✅    |  ✅
Quản lý người dùng     ❌   |    ❌    |   ✅    |  ✅
Quản lý đề tài         ✅*  |    ✅**  |   ✅    |  ✅
Quản lý hội đồng       ❌   |    ❌    |   ✅    |  ✅
Quản lý sinh viên      ❌   |    ❌    |   ✅    |  ✅
Quản lý giảng viên     ❌   |    ❌    |   ✅    |  ✅
Chấm điểm              ❌   |    ✅    |   ✅    |  ✅
Báo cáo & thống kê     ❌   |    ❌    |   ✅    |  ✅
Cài đặt hệ thống       ❌   |    ❌    |   ❌    |  ✅

* Sinh viên chỉ xem danh sách
** Giảng viên quản lý đề tài của mình
```

---

## 🔄 Cập Nhật / Mở Rộng

Để cập nhật quyền hạn:

1. **Thêm quyền mới:**
   ```java
   // Trong PermissionManager.java
   Set<String> newPermissions = new HashSet<>(Arrays.asList(
       "LOGIN",
       "NEW_PERMISSION",  // Thêm đây
       // ...
   ));
   ```

2. **Gán quyền cho role:**
   ```java
   role.add("NEW_PERMISSION");
   ```

3. **Kiểm tra trong code:**
   ```java
   if (PermissionManager.hasPermission(user, "NEW_PERMISSION")) {
       // Thực thi logic
   }
   ```

---

*Tài liệu này được cập nhật dựa trên UseCase Diagram tổng quát - Phiên bản V1.1*
