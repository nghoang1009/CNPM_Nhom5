package com.app.model;

public class ReviewBoard {
    private int boardId;
    private String name;
    private String description;
    private int chairman; // Chủ tịch hội đồng
    private String status; // ACTIVE, INACTIVE

    public ReviewBoard() {}

    public ReviewBoard(String name, String description, int chairman) {
        this.name = name;
        this.description = description;
        this.chairman = chairman;
        this.status = "ACTIVE";
    }

    // Getters and Setters
    public int getBoardId() { return boardId; }
    public void setBoardId(int boardId) { this.boardId = boardId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getChairman() { return chairman; }
    public void setChairman(int chairman) { this.chairman = chairman; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "ReviewBoard{" + "boardId=" + boardId + ", name='" + name + '\'' +
               ", status='" + status + '\'' + '}';
    }
}
