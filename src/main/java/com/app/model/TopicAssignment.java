package com.app.model;

import java.time.LocalDateTime;

public class TopicAssignment {
    private int assignmentId;
    private int topicId;
    private int studentId;
    private LocalDateTime assignedAt;
    private String status; // PENDING, ACCEPTED, REJECTED

    public TopicAssignment() {}

    public TopicAssignment(int topicId, int studentId) {
        this.topicId = topicId;
        this.studentId = studentId;
        this.status = "PENDING";
    }

    // Getters and Setters
    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }

    public int getTopicId() { return topicId; }
    public void setTopicId(int topicId) { this.topicId = topicId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public LocalDateTime getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDateTime assignedAt) { this.assignedAt = assignedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "TopicAssignment{" + "assignmentId=" + assignmentId + 
               ", topicId=" + topicId + ", studentId=" + studentId + 
               ", status='" + status + '\'' + '}';
    }
}
