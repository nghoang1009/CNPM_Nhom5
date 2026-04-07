package com.app.dao;

import com.app.model.ReviewBoard;
import com.app.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewBoardDAO {

    public boolean addBoard(ReviewBoard board) {
        String sql = "INSERT INTO review_boards (name, description, chairman, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, board.getName());
            stmt.setString(2, board.getDescription());
            stmt.setInt(3, board.getChairman());
            stmt.setString(4, board.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding board: " + e.getMessage());
            return false;
        }
    }

    public ReviewBoard getBoardById(int boardId) {
        String sql = "SELECT * FROM review_boards WHERE board_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, boardId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToBoard(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting board: " + e.getMessage());
        }
        return null;
    }

    public List<ReviewBoard> getAllBoards() {
        List<ReviewBoard> boards = new ArrayList<>();
        String sql = "SELECT * FROM review_boards";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                boards.add(mapRowToBoard(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all boards: " + e.getMessage());
        }
        return boards;
    }

    public List<ReviewBoard> getBoardsByStatus(String status) {
        List<ReviewBoard> boards = new ArrayList<>();
        String sql = "SELECT * FROM review_boards WHERE status = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                boards.add(mapRowToBoard(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting boards by status: " + e.getMessage());
        }
        return boards;
    }

    public boolean updateBoard(ReviewBoard board) {
        String sql = "UPDATE review_boards SET name = ?, description = ?, chairman = ?, status = ? WHERE board_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, board.getName());
            stmt.setString(2, board.getDescription());
            stmt.setInt(3, board.getChairman());
            stmt.setString(4, board.getStatus());
            stmt.setInt(5, board.getBoardId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating board: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteBoard(int boardId) {
        String sql = "DELETE FROM review_boards WHERE board_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, boardId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting board: " + e.getMessage());
            return false;
        }
    }

    private ReviewBoard mapRowToBoard(ResultSet rs) throws SQLException {
        ReviewBoard board = new ReviewBoard();
        board.setBoardId(rs.getInt("board_id"));
        board.setName(rs.getString("name"));
        board.setDescription(rs.getString("description"));
        board.setChairman(rs.getInt("chairman"));
        board.setStatus(rs.getString("status"));
        return board;
    }
}
