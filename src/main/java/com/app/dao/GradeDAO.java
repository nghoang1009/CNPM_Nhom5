package com.app.dao;

import com.app.model.Grade;
import com.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    public boolean addGrade(Grade grade) {
        String sql = "INSERT INTO grades (proposal_id, reviewer_id, score, feedback) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, grade.getProposalId());
            stmt.setInt(2, grade.getReviewerId());
            stmt.setDouble(3, grade.getScore());
            stmt.setString(4, grade.getFeedback());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding grade: " + e.getMessage());
            return false;
        }
    }

    public Grade getGradeById(int gradeId) {
        String sql = "SELECT * FROM grades WHERE grade_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gradeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToGrade(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting grade: " + e.getMessage());
        }
        return null;
    }

    public List<Grade> getGradesByProposal(int proposalId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE proposal_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, proposalId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                grades.add(mapRowToGrade(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting grades: " + e.getMessage());
        }
        return grades;
    }

    public List<Grade> getGradesByReviewer(int reviewerId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE reviewer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reviewerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                grades.add(mapRowToGrade(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting grades by reviewer: " + e.getMessage());
        }
        return grades;
    }

    public List<Grade> getGradesByStudent(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT g.* FROM grades g " +
                    "JOIN proposals p ON g.proposal_id = p.proposal_id " +
                    "WHERE p.student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                grades.add(mapRowToGrade(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting grades by student: " + e.getMessage());
        }
        return grades;
    }

    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                grades.add(mapRowToGrade(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all grades: " + e.getMessage());
        }
        return grades;
    }

    public boolean updateGrade(Grade grade) {
        String sql = "UPDATE grades SET score = ?, feedback = ? WHERE grade_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, grade.getScore());
            stmt.setString(2, grade.getFeedback());
            stmt.setInt(3, grade.getGradeId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating grade: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteGrade(int gradeId) {
        String sql = "DELETE FROM grades WHERE grade_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gradeId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting grade: " + e.getMessage());
            return false;
        }
    }

    private Grade mapRowToGrade(ResultSet rs) throws SQLException {
        Grade grade = new Grade();
        grade.setGradeId(rs.getInt("grade_id"));
        grade.setProposalId(rs.getInt("proposal_id"));
        grade.setReviewerId(rs.getInt("reviewer_id"));
        grade.setScore(rs.getDouble("score"));
        grade.setFeedback(rs.getString("feedback"));
        Timestamp ts = rs.getTimestamp("graded_at");
        if (ts != null) {
            grade.setGradedAt(ts.toLocalDateTime());
        }
        return grade;
    }
}
