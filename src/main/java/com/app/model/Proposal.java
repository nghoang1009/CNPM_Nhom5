package com.app.model;

import java.time.LocalDateTime;

public class Proposal {
    private int proposalId;
    private int topicId;
    private int studentId;
    private String content;
    private LocalDateTime submittedAt;
    private String status; // DRAFT, SUBMITTED, APPROVED, REJECTED

    public Proposal() {}

    public Proposal(int topicId, int studentId, String content) {
        this.topicId = topicId;
        this.studentId = studentId;
        this.content = content;
        this.status = "DRAFT";
    }

    // Getters and Setters
    public int getProposalId() { return proposalId; }
    public void setProposalId(int proposalId) { this.proposalId = proposalId; }

    public int getTopicId() { return topicId; }
    public void setTopicId(int topicId) { this.topicId = topicId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Proposal{" + "proposalId=" + proposalId + ", topicId=" + topicId +
               ", studentId=" + studentId + ", status='" + status + '\'' + '}';
    }
}
