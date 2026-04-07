package com.app.dao;

import com.app.model.TopicAssignment;
import com.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicAssignmentDAO {

    public boolean addAssignment(TopicAssignment assignment) {
        String sql = "INSERT INTO topic_assignments (topic_id, student_id, status) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, assignment.getTopicId());
            stmt.setInt(2, assignment.getStudentId());
            stmt.setString(3, assignment.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding assignment: " + e.getMessage());
            return false;
        }
    }

    public TopicAssignment getAssignmentById(int assignmentId) {
        String sql = "SELECT * FROM topic_assignments WHERE assignment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, assignmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToAssignment(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting assignment: " + e.getMessage());
        }
        return null;
    }

    public List<TopicAssignment> getAssignmentsByStudent(int studentId) {
        List<TopicAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM topic_assignments WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                assignments.add(mapRowToAssignment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting assignments: " + e.getMessage());
        }
        return assignments;
    }

    public List<TopicAssignment> getAssignmentsByTopic(int topicId) {
        List<TopicAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM topic_assignments WHERE topic_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, topicId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                assignments.add(mapRowToAssignment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting assignments by topic: " + e.getMessage());
        }
        return assignments;
    }

    public List<TopicAssignment> getAllAssignments() {
        List<TopicAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM topic_assignments";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                assignments.add(mapRowToAssignment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all assignments: " + e.getMessage());
        }
        return assignments;
    }

    public boolean updateAssignment(TopicAssignment assignment) {
        String sql = "UPDATE topic_assignments SET status = ? WHERE assignment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, assignment.getStatus());
            stmt.setInt(2, assignment.getAssignmentId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating assignment: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAssignment(int assignmentId) {
        String sql = "DELETE FROM topic_assignments WHERE assignment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, assignmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting assignment: " + e.getMessage());
            return false;
        }
    }

    private TopicAssignment mapRowToAssignment(ResultSet rs) throws SQLException {
        TopicAssignment assignment = new TopicAssignment();
        assignment.setAssignmentId(rs.getInt("assignment_id"));
        assignment.setTopicId(rs.getInt("topic_id"));
        assignment.setStudentId(rs.getInt("student_id"));
        assignment.setStatus(rs.getString("status"));
        Timestamp ts = rs.getTimestamp("assigned_at");
        if (ts != null) {
            assignment.setAssignedAt(ts.toLocalDateTime());
        }
        return assignment;
    }
}
