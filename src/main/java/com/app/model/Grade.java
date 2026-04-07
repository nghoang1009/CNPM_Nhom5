package com.app.model;

import java.time.LocalDateTime;

public class Grade {
    private int gradeId;
    private int proposalId;
    private int reviewerId;
    private double score;
    private String feedback;
    private LocalDateTime gradedAt;

    public Grade() {}

    public Grade(int proposalId, int reviewerId, double score, String feedback) {
        this.proposalId = proposalId;
        this.reviewerId = reviewerId;
        this.score = score;
        this.feedback = feedback;
    }

    // Getters and Setters
    public int getGradeId() { return gradeId; }
    public void setGradeId(int gradeId) { this.gradeId = gradeId; }

    public int getProposalId() { return proposalId; }
    public void setProposalId(int proposalId) { this.proposalId = proposalId; }

    public int getReviewerId() { return reviewerId; }
    public void setReviewerId(int reviewerId) { this.reviewerId = reviewerId; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }

    public LocalDateTime getGradedAt() { return gradedAt; }
    public void setGradedAt(LocalDateTime gradedAt) { this.gradedAt = gradedAt; }

    @Override
    public String toString() {
        return "Grade{" + "gradeId=" + gradeId + ", proposalId=" + proposalId +
               ", score=" + score + ", gradedAt=" + gradedAt + '}';
    }
}
