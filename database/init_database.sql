-- Create Database
CREATE DATABASE IF NOT EXISTS research_db;
USE research_db;

-- Create Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    role ENUM('ADMIN', 'LECTURER', 'STUDENT', 'MANAGER') NOT NULL,
    department VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_email (email)
);

-- Create Research Topics Table
CREATE TABLE IF NOT EXISTS research_topics (
    topic_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    lecturer_id INT NOT NULL,
    status ENUM('DRAFT', 'APPROVED', 'IN_PROGRESS', 'COMPLETED') DEFAULT 'DRAFT',
    max_members INT DEFAULT 5,
    field VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (lecturer_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create Topic Assignments Table
CREATE TABLE IF NOT EXISTS topic_assignments (
    assignment_id INT PRIMARY KEY AUTO_INCREMENT,
    topic_id INT NOT NULL,
    student_id INT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('PENDING', 'ACCEPTED', 'REJECTED') DEFAULT 'PENDING',
    FOREIGN KEY (topic_id) REFERENCES research_topics(topic_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create Review Boards Table
CREATE TABLE IF NOT EXISTS review_boards (
    board_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    chairman INT,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    FOREIGN KEY (chairman) REFERENCES users(user_id) ON DELETE SET NULL
);

-- Create Board Assignments Table
CREATE TABLE IF NOT EXISTS board_assignments (
    assignment_id INT PRIMARY KEY AUTO_INCREMENT,
    board_id INT NOT NULL,
    member_id INT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role ENUM('CHAIRMAN', 'MEMBER') DEFAULT 'MEMBER',
    FOREIGN KEY (board_id) REFERENCES review_boards(board_id) ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create Proposals Table
CREATE TABLE IF NOT EXISTS proposals (
    proposal_id INT PRIMARY KEY AUTO_INCREMENT,
    topic_id INT NOT NULL,
    student_id INT NOT NULL,
    content LONGTEXT NOT NULL,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('DRAFT', 'SUBMITTED', 'APPROVED', 'REJECTED') DEFAULT 'DRAFT',
    FOREIGN KEY (topic_id) REFERENCES research_topics(topic_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create Grades Table
CREATE TABLE IF NOT EXISTS grades (
    grade_id INT PRIMARY KEY AUTO_INCREMENT,
    proposal_id INT NOT NULL,
    reviewer_id INT NOT NULL,
    score DOUBLE NOT NULL CHECK (score >= 0 AND score <= 10),
    feedback TEXT,
    graded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (proposal_id) REFERENCES proposals(proposal_id) ON DELETE CASCADE,
    FOREIGN KEY (reviewer_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Insert Sample Data

-- Insert Users (Admin, Lecturers, Students, Managers)
INSERT INTO users (username, password, full_name, email, phone, role, department) VALUES
('admin', 'admin123', 'Quản trị viên', 'admin@university.edu', '0912345678', 'ADMIN', 'IT'),
('lecturer1', 'pass123', 'Thầy Nguyễn Văn A', 'lecturer1@university.edu', '0987654321', 'LECTURER', 'Computer Science'),
('lecturer2', 'pass123', 'Thầy Trần Văn B', 'lecturer2@university.edu', '0976543210', 'LECTURER', 'Computer Science'),
('manager1', 'pass123', 'PGS.TS Hồ Xuân C', 'manager1@university.edu', '0965432109', 'MANAGER', 'Computer Science'),
('manager2', 'pass123', 'Tiến sĩ Lê Thị D', 'manager2@university.edu', '0954321098', 'MANAGER', 'IT'),
('student1', 'pass123', 'Phạm Quốc An', 'student1@student.edu', '0943210987', 'STUDENT', 'Computer Science'),
('student2', 'pass123', 'Võ Thị Bảo', 'student2@student.edu', '0932109876', 'STUDENT', 'Computer Science'),
('student3', 'pass123', 'Đỗ Minh Cường', 'student3@student.edu', '0921098765', 'STUDENT', 'IT'),
('student4', 'pass123', 'Cao Thanh Dương', 'student4@student.edu', '0910987654', 'STUDENT', 'Computer Science');

-- Insert Research Topics
INSERT INTO research_topics (title, description, lecturer_id, status, max_members, field) VALUES
('Hệ thống quản lý học tập trực tuyến', 'Phát triển hệ thống quản lý học tập với các tính năng: quản lý khoá học, giao bài tập, chấm điểm tự động.', 2, 'APPROVED', 5, 'Web Development'),
('Ứng dụng AI dự đoán điểm sinh viên', 'Xây dựng mô hình AI để dự đoán điểm số sinh viên dựa trên các dữ liệu quá khứ.', 2, 'APPROVED', 4, 'Machine Learning'),
('Chatbot hỗ trợ khách hàng thông minh', 'Xây dựng chatbot sử dụng NLP để thao tác khách hàng tự động.', 3, 'APPROVED', 3, 'AI/NLP'),
('Ứng dụng quản lý bán hàng di động', 'Phát triển ứng dụng Android quản lý bán hàng cho cửa hàng bán lẻ.', 2, 'DRAFT', 5, 'Mobile Development'),
('Hệ thống an niệm mạng dùng Blockchain', 'Nghiên cứu ứng dụng Blockchain trong bảo mật dữ liệu trên mạng.', 3, 'IN_PROGRESS', 4, 'Cybersecurity');

-- Insert Topic Assignments
INSERT INTO topic_assignments (topic_id, student_id, status) VALUES
(1, 6, 'ACCEPTED'),
(1, 7, 'ACCEPTED'),
(2, 6, 'PENDING'),
(2, 8, 'ACCEPTED'),
(3, 7, 'ACCEPTED'),
(4, 9, 'ACCEPTED'),
(5, 8, 'PENDING');

-- Insert Review Boards
INSERT INTO review_boards (name, description, chairman, status) VALUES
('Hội đồng chấm khoá 2024 - Lần 1', 'Hội đồng chấm các đề tài nghiên cứu khóa 2024 lần thứ nhất', 4, 'ACTIVE'),
('Hội đồng chấm khoá 2024 - Lần 2', 'Hội đồng chấm các đề tài nghiên cứu khóa 2024 lần thứ hai', 5, 'ACTIVE');

-- Insert Board Assignments
INSERT INTO board_assignments (board_id, member_id, role) VALUES
(1, 4, 'CHAIRMAN'),
(1, 5, 'MEMBER'),
(1, 2, 'MEMBER'),
(2, 5, 'CHAIRMAN'),
(2, 4, 'MEMBER'),
(2, 3, 'MEMBER');

-- Insert Proposals
INSERT INTO proposals (topic_id, student_id, content, status) VALUES
(1, 6, 'Đề cương hoàn chỉnh về hệ thống quản lý học tập trực tuyến với các module chính: quản lý user, quản lý khoá học, giao bài tập...', 'SUBMITTED'),
(1, 7, 'Đề cương chi tiết với công nghệ sử dụng: Spring Boot, React, MySQL, Docker...', 'SUBMITTED'),
(2, 8, 'Đề cương về mô hình AI với TensorFlow, phân tích dữ liệu lịch sử của hơn 10,000 sinh viên...', 'DRAFT'),
(3, 7, 'Đề cương chi tiết về Chatbot sử dụng Google Dialogflow và Python Backend...', 'SUBMITTED'),
(4, 9, 'Đề cương phát triển ứng dụng Android với SQLite, giao diện Material Design...', 'DRAFT');

-- Insert Grades
INSERT INTO grades (proposal_id, reviewer_id, score, feedback) VALUES
(1, 4, 8.5, 'Đề cương tốt, cần bổ sung thêm phần kỹ thuật chi tiết'),
(1, 5, 8.0, 'Nội dung rõ ràng, yêu cầu chức năng đầy đủ'),
(2, 4, 7.5, 'Khá tốt nhưng cần mở rộng phạm vi'),
(3, 5, 8.8, 'Xuất sắc, công nghệ phù hợp, kế hoạch rõ ràng'),
(4, 4, 7.0, 'Tốt nhưng cần làm rõ hơn về lịch trình thực hiện');

-- Create Indexes for better performance
CREATE INDEX idx_user_role ON users(role);
CREATE INDEX idx_topic_lecturer ON research_topics(lecturer_id);
CREATE INDEX idx_topic_status ON research_topics(status);
CREATE INDEX idx_assignment_student ON topic_assignments(student_id);
CREATE INDEX idx_assignment_topic ON topic_assignments(topic_id);
CREATE INDEX idx_proposal_student ON proposals(student_id);
CREATE INDEX idx_proposal_topic ON proposals(topic_id);
CREATE INDEX idx_grade_proposal ON grades(proposal_id);
CREATE INDEX idx_grade_reviewer ON grades(reviewer_id);
CREATE INDEX idx_board_chairman ON review_boards(chairman);
CREATE INDEX idx_board_assignment_board ON board_assignments(board_id);
