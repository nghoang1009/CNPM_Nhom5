package com.app.model;

import java.time.LocalDateTime;

public class ResearchTopic {
    private int topicId;
    private String title;
    private String description;
    private int lecturerId; // Người hướng dẫn
    private LocalDateTime createdAt;
    private String status; // DRAFT, APPROVED, IN_PROGRESS, COMPLETED
    private int maxMembers;
    private String field; // Lĩnh vực

    public ResearchTopic() {}

    public ResearchTopic(String title, String description, int lecturerId, 
                        String field, int maxMembers) {
        this.title = title;
        this.description = description;
        this.lecturerId = lecturerId;
        this.field = field;
        this.maxMembers = maxMembers;
        this.status = "DRAFT";
    }

    // Getters and Setters
    public int getTopicId() { return topicId; }
    public void setTopicId(int topicId) { this.topicId = topicId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getLecturerId() { return lecturerId; }
    public void setLecturerId(int lecturerId) { this.lecturerId = lecturerId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getMaxMembers() { return maxMembers; }
    public void setMaxMembers(int maxMembers) { this.maxMembers = maxMembers; }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    @Override
    public String toString() {
        return "ResearchTopic{" + "topicId=" + topicId + ", title='" + title + '\'' +
               ", status='" + status + '\'' + ", field='" + field + '\'' + '}';
    }
}
