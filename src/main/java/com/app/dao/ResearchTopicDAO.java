package com.app.dao;

import com.app.model.ResearchTopic;
import com.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResearchTopicDAO {

    public boolean addTopic(ResearchTopic topic) {
        String sql = "INSERT INTO research_topics (title, description, lecturer_id, status, max_members, field) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, topic.getTitle());
            stmt.setString(2, topic.getDescription());
            stmt.setInt(3, topic.getLecturerId());
            stmt.setString(4, topic.getStatus());
            stmt.setInt(5, topic.getMaxMembers());
            stmt.setString(6, topic.getField());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding topic: " + e.getMessage());
            return false;
        }
    }

    public ResearchTopic getTopicById(int topicId) {
        String sql = "SELECT * FROM research_topics WHERE topic_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, topicId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToTopic(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting topic: " + e.getMessage());
        }
        return null;
    }

    public List<ResearchTopic> getAllTopics() {
        List<ResearchTopic> topics = new ArrayList<>();
        String sql = "SELECT * FROM research_topics ORDER BY created_at DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                topics.add(mapRowToTopic(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all topics: " + e.getMessage());
        }
        return topics;
    }

    public List<ResearchTopic> getTopicsByLecturer(int lecturerId) {
        List<ResearchTopic> topics = new ArrayList<>();
        String sql = "SELECT * FROM research_topics WHERE lecturer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lecturerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                topics.add(mapRowToTopic(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting topics by lecturer: " + e.getMessage());
        }
        return topics;
    }

    public List<ResearchTopic> getTopicsByStatus(String status) {
        List<ResearchTopic> topics = new ArrayList<>();
        String sql = "SELECT * FROM research_topics WHERE status = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                topics.add(mapRowToTopic(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting topics by status: " + e.getMessage());
        }
        return topics;
    }

    public boolean updateTopic(ResearchTopic topic) {
        String sql = "UPDATE research_topics SET title = ?, description = ?, status = ?, " +
                     "max_members = ?, field = ? WHERE topic_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, topic.getTitle());
            stmt.setString(2, topic.getDescription());
            stmt.setString(3, topic.getStatus());
            stmt.setInt(4, topic.getMaxMembers());
            stmt.setString(5, topic.getField());
            stmt.setInt(6, topic.getTopicId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating topic: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTopic(int topicId) {
        String sql = "DELETE FROM research_topics WHERE topic_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, topicId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting topic: " + e.getMessage());
            return false;
        }
    }

    private ResearchTopic mapRowToTopic(ResultSet rs) throws SQLException {
        ResearchTopic topic = new ResearchTopic();
        topic.setTopicId(rs.getInt("topic_id"));
        topic.setTitle(rs.getString("title"));
        topic.setDescription(rs.getString("description"));
        topic.setLecturerId(rs.getInt("lecturer_id"));
        topic.setStatus(rs.getString("status"));
        topic.setMaxMembers(rs.getInt("max_members"));
        topic.setField(rs.getString("field"));
        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            topic.setCreatedAt(ts.toLocalDateTime());
        }
        return topic;
    }
}
