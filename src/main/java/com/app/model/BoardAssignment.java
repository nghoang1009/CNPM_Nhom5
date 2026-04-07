package com.app.model;

import java.time.LocalDateTime;

public class BoardAssignment {
    private int assignmentId;
    private int boardId;
    private int memberId;
    private LocalDateTime assignedAt;
    private String role; // CHAIRMAN, MEMBER

    public BoardAssignment() {}

    public BoardAssignment(int boardId, int memberId, String role) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.role = role;
    }

    // Getters and Setters
    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }

    public int getBoardId() { return boardId; }
    public void setBoardId(int boardId) { this.boardId = boardId; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public LocalDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDateTime assignedAt) { this.assignedAt = assignedAt; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "BoardAssignment{" + "assignmentId=" + assignmentId + 
               ", boardId=" + boardId + ", memberId=" + memberId + 
               ", role='" + role + '\'' + '}';
    }
}
