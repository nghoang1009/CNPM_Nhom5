# Hướng Dẫn Cài Đặt Hệ Thống Quản Lý Nghiên Cứu Khoa Học

## Bước 1: Chuẩn Bị Môi Trường

### 1.1 Kiểm Tra Java
```bash
java -version
javac -version
```

Yêu cầu: Java 11 trở lên

### 1.2 Kiểm Tra Maven
```bash
mvn -v
```

Yêu cầu: Maven 3.6 trở lên

### 1.3 Cài Đặt MySQL
- Tải MySQL từ https://www.mysql.com/downloads/
- Cài đặt MySQL Server
- Khởi động MySQL Service
- Ghi nhớ tài khoản root và mật khẩu

### 1.4 Cài Đặt MySQL Workbench (Tùy Chọn)
- Cài từ https://www.mysql.com/products/workbench/
- Để quản lý database dễ dàng

## Bước 2: Tạo Database

### Cách 1: Sử dụng Command Line

1. Mở Command Prompt (Windows) hoặc Terminal (Linux/Mac)

2. Đăng nhập vào MySQL:
```bash
mysql -u root -p
```

3. Nhập mật khẩu MySQL khi được yêu cầu

4. Chạy script khởi tạo database:
```bash
source C:\Download\CNPM_Nhom5\database\init_database.sql
```

Hoặc từ thư mục database:
```bash
cd C:\Download\CNPM_Nhom5\database
mysql -u root -p < init_database.sql
```

5. Kiểm tra database được tạo:
```sql
SHOW DATABASES;
USE research_db;
SHOW TABLES;
```

### Cách 2: Sử dụng MySQL Workbench

1. Mở MySQL Workbench

2. Kết nối đến MySQL:
   - Host: localhost
   - Port: 3306
   - Username: root
   - Password: (mật khẩu của bạn)

3. File → Open SQL Script

4. Chọn file: `C:\Download\CNPM_Nhom5\database\init_database.sql`

5. Nhấn ⚡ (Execute) hoặc Ctrl+Enter

6. Kiểm tra database trong bên trái panel

## Bước 3: Cấu Hình Kết Nối Database

1. Mở file: `src/main/java/com/app/util/DatabaseConnection.java`

2. Kiểm tra/sửa thông tin kết nối:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/research_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "root"; // Thay bằng mật khẩu của bạn
```

**Ghi chú:** Nếu MySQL server của bạn chạy trên port khác (mặc định 3306), hãy thay đổi port này.

## Bước 4: Cài Đặt Dependencies

1. Mở Command Prompt

2. Chuyển đến thư mục project:
```bash
cd C:\Download\CNPM_Nhom5
```

3. Chạy Maven:
```bash
mvn clean install
```

Chờ Maven tải xuống tất cả dependencies (có thể mất vài phút lần đầu).

**Nếu gặp lỗi**, thử:
```bash
mvn clean
mvn install -DskipTests
```

## Bước 5: Chạy Ứng Dụng

### Cách 1: Sử dụng IDE (IntelliJ IDEA hoặc Eclipse)

**IntelliJ IDEA:**
1. File → Open → Chọn thư mục `CNPM_Nhom5`
2. Chờ project load
3. Mở file `src/main/java/com/app/Main.java`
4. Nhấn chuột phải → Run 'Main.main()'

**Eclipse:**
1. File → Import → Existing Maven Projects
2. Browse → Chọn thư mục `CNPM_Nhom5`
3. Finish
4. Chuột phải project → Maven → Update Project
5. Mở Main.java → Right Click → Run As → Java Application

### Cách 2: Sử dụng Maven Command

```bash
mvn compile exec:java -Dexec.mainClass="com.app.Main"
```

### Cách 3: Build JAR và Chạy

```bash
# Build
mvn clean package

# Run
java -jar target/research-management.jar
```

## Bước 6: Đăng Nhập

Khi ứng dụng khởi động, bạn thấy màn hình đăng nhập.

Hãy đăng nhập bằng tài khoản mặc định:

**Admin:**
- Username: `admin`
- Password: `admin123`

**Lecturer:**
- Username: `lecturer1`
- Password: `pass123`

**Student:**
- Username: `student1`
- Password: `pass123`

## Xử Lý Vấn Đề Thường Gặp

### Vấn Đề 1: "Cannot connect to database"
```
Nguyên nhân: MySQL server không chạy hoặc tài khoản sai
Giải pháp:
1. Kiểm tra MySQL đang chạy:
   - Windows: Services.msc tìm MySQL
   - Linux: sudo service mysql status
2. Kiểm tra tài khoản root
3. Kiểm tra cấu hình DatabaseConnection.java
```

### Vấn Đề 2: "Table doesn't exist"
```
Nguyên nhân: Script khởi tạo database chưa chạy
Giải pháp:
Chạy lại script init_database.sql theo Bước 2
```

### Vấn Đề 3: "Maven build failed"
```
Giải pháp:
mvn clean
mvn install -DskipTests
mvn -U clean install
```

### Vấn Đề 4: "Class not found: Main"
```
Giải pháp:
1. Rebuild project
2. Chạy mvn compile trước
3. Kiểm tra Java version
```

### Vấn Đề 5: "Driver not found"
```
Nguyên nhân: MySQL JDBC driver không được load
Giải pháp:
mvn clean install
```

## Kiểm Tra Cài Đặt

### 1. Kiểm Tra Database

```sql
USE research_db;
SELECT COUNT(*) FROM users;
SELECT * FROM users LIMIT 1;
```

Bạn nên thấy 9 users và dữ liệu đã được insert.

### 2. Kiểm Tra Connection

Từ command line:
```bash
cd C:\Download\CNPM_Nhom5
mvn compile
```

Nếu thành công, project compile xong.

### 3. Chạy Test

```bash
mvn test
```

## Cấu Trúc Thư Mục Sau Khi Cài Đặt

```
CNPM_Nhom5/
├── pom.xml
├── README.md
├── SETUP.md (file này)
├── database/
│   └── init_database.sql
├── src/
│   └── main/
│       ├── java/com/app/
│       │   ├── Main.java
│       │   ├── model/
│       │   ├── dao/
│       │   ├── view/
│       │   └── util/
│       └── resources/
├── target/ (được tạo sau khi build)
└── .m2/ (Maven cache, tự động tạo)
```

## Tối Ưu Hóa & Tuning

### Tăng Hiệu Suất Database

1. Thêm indexes:
```sql
-- Đã có trong scriptl init_database.sql
```

2. Tối ưu MySQL my.ini:
```ini
[mysqld]
max_connections=1000
query_cache_size=32M
```

### JVM Optimization

Nếu dùng JAR:
```bash
java -Xms256m -Xmx512m -jar target/research-management.jar
```

## Backup Database

```bash
mysqldump -u root -p research_db > backup_research_db.sql
```

Restore:
```bash
mysql -u root -p research_db < backup_research_db.sql
```

## Cập Nhật Nâng Cao

### Thay Đổi Port MySQL
Tìm trong `my.ini`:
```ini
[mysqld]
port=3307
```

Sau đó sửa DatabaseConnection.java:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3307/research_db";
```

### Sử Dụng Tài Khoản MySQL Khác
Tạo user riêng:
```sql
CREATE USER 'app'@'localhost' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON research_db.* TO 'app'@'localhost';
FLUSH PRIVILEGES;
```

Sửa DatabaseConnection.java:
```java
private static final String DB_USER = "app";
private static final String DB_PASSWORD = "password123";
```

## Uninstall / Xóa

```bash
# Xóa database
mysql -u root -p -e "DROP DATABASE research_db;"

# Xóa Maven cache (tùy chọn)
rm -rf .m2

# Xóa build directory
rm -rf target

# Xóa project
rm -rf CNPM_Nhom5
```

## Hỗ Trợ Thêm

- Tài liệu MySQL: https://dev.mysql.com/doc/
- Tài liệu Maven: https://maven.apache.org/guides/
- Tài liệu Java Swing: https://docs.oracle.com/javase/tutorial/uiswing/

---

**Chúc bạn cài đặt thành công!**

Nếu gặp vấn đề, liên hệ nhóm phát triển.
