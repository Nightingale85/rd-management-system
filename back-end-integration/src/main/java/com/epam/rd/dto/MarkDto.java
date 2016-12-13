package com.epam.rd.dto;

public class MarkDto {
    private Long id;
    private Long practicalTaskId;
    private String menteeEmail;
    private String estimatorEmail;
    private RoleDto estimatorRole;
    private Integer mark;
    private String feedback;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPracticalTaskId() {
        return practicalTaskId;
    }

    public void setPracticalTaskId(Long practicalTaskId) {
        this.practicalTaskId = practicalTaskId;
    }

    public String getMenteeEmail() {
        return menteeEmail;
    }

    public void setMenteeEmail(String menteeEmail) {
        this.menteeEmail = menteeEmail;
    }

    public String getEstimatorEmail() {
        return estimatorEmail;
    }

    public void setEstimatorEmail(String estimatorEmail) {
        this.estimatorEmail = estimatorEmail;
    }

    public RoleDto getEstimatorRole() {
        return estimatorRole;
    }

    public void setEstimatorRole(RoleDto estimatorRole) {
        this.estimatorRole = estimatorRole;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
