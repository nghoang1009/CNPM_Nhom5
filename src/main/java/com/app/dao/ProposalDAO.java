package com.app.dao;

import com.app.model.Proposal;
import com.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProposalDAO {

    public boolean addProposal(Proposal proposal) {
        String sql = "INSERT INTO proposals (topic_id, student_id, content, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, proposal.getTopicId());
            stmt.setInt(2, proposal.getStudentId());
            stmt.setString(3, proposal.getContent());
            stmt.setString(4, proposal.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding proposal: " + e.getMessage());
            return false;
        }
    }

    public Proposal getProposalById(int proposalId) {
        String sql = "SELECT * FROM proposals WHERE proposal_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, proposalId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToProposal(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting proposal: " + e.getMessage());
        }
        return null;
    }

    public List<Proposal> getProposalsByStudent(int studentId) {
        List<Proposal> proposals = new ArrayList<>();
        String sql = "SELECT * FROM proposals WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                proposals.add(mapRowToProposal(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting proposals: " + e.getMessage());
        }
        return proposals;
    }

    public List<Proposal> getProposalsByTopic(int topicId) {
        List<Proposal> proposals = new ArrayList<>();
        String sql = "SELECT * FROM proposals WHERE topic_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, topicId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                proposals.add(mapRowToProposal(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting proposals by topic: " + e.getMessage());
        }
        return proposals;
    }

    public List<Proposal> getAllProposals() {
        List<Proposal> proposals = new ArrayList<>();
        String sql = "SELECT * FROM proposals";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                proposals.add(mapRowToProposal(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all proposals: " + e.getMessage());
        }
        return proposals;
    }

    public boolean updateProposal(Proposal proposal) {
        String sql = "UPDATE proposals SET content = ?, status = ? WHERE proposal_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proposal.getContent());
            stmt.setString(2, proposal.getStatus());
            stmt.setInt(3, proposal.getProposalId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating proposal: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteProposal(int proposalId) {
        String sql = "DELETE FROM proposals WHERE proposal_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, proposalId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting proposal: " + e.getMessage());
            return false;
        }
    }

    private Proposal mapRowToProposal(ResultSet rs) throws SQLException {
        Proposal proposal = new Proposal();
        proposal.setProposalId(rs.getInt("proposal_id"));
        proposal.setTopicId(rs.getInt("topic_id"));
        proposal.setStudentId(rs.getInt("student_id"));
        proposal.setContent(rs.getString("content"));
        proposal.setStatus(rs.getString("status"));
        Timestamp ts = rs.getTimestamp("submitted_at");
        if (ts != null) {
            proposal.setSubmittedAt(ts.toLocalDateTime());
        }
        return proposal;
    }
}
