package com.app.dao;

import com.app.model.BoardAssignment;
import com.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardAssignmentDAO {

    public boolean addAssignment(BoardAssignment assignment) {
        String sql = "INSERT INTO board_assignments (board_id, member_id, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, assignment.getBoardId());
            stmt.setInt(2, assignment.getMemberId());
            stmt.setString(3, assignment.getRole());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding assignment: " + e.getMessage());
            return false;
        }
    }

    public BoardAssignment getAssignmentById(int assignmentId) {
        String sql = "SELECT * FROM board_assignments WHERE assignment_id = ?";
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

    public List<BoardAssignment> getAssignmentsByBoard(int boardId) {
        List<BoardAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM board_assignments WHERE board_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, boardId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                assignments.add(mapRowToAssignment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting assignments: " + e.getMessage());
        }
        return assignments;
    }

    public List<BoardAssignment> getAssignmentsByMember(int memberId) {
        List<BoardAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM board_assignments WHERE member_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                assignments.add(mapRowToAssignment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting assignments by member: " + e.getMessage());
        }
        return assignments;
    }

    public List<BoardAssignment> getAllAssignments() {
        List<BoardAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM board_assignments";
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

    public boolean updateAssignment(BoardAssignment assignment) {
        String sql = "UPDATE board_assignments SET role = ? WHERE assignment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, assignment.getRole());
            stmt.setInt(2, assignment.getAssignmentId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating assignment: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAssignment(int assignmentId) {
        String sql = "DELETE FROM board_assignments WHERE assignment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, assignmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting assignment: " + e.getMessage());
            return false;
        }
    }

    private BoardAssignment mapRowToAssignment(ResultSet rs) throws SQLException {
        BoardAssignment assignment = new BoardAssignment();
        assignment.setAssignmentId(rs.getInt("assignment_id"));
        assignment.setBoardId(rs.getInt("board_id"));
        assignment.setMemberId(rs.getInt("member_id"));
        assignment.setRole(rs.getString("role"));
        Timestamp ts = rs.getTimestamp("assigned_at");
        if (ts != null) {
            assignment.setAssignedAt(ts.toLocalDateTime());
        }
        return assignment;
    }
}
