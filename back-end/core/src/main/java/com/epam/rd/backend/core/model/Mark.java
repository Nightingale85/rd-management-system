package com.epam.rd.backend.core.model;

import javax.persistence.*;

@Entity
@Table(name = "mark")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "practical_task_id", nullable = false)
    private Long practicalTaskId;
    @Column(name = "mentee_email", nullable = false)
    private String menteeEmail;
    @Column(name = "estimator_email", nullable = false)
    private String estimatorEmail;
    @Column(name = "estimator_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole estimatorRole;
    @Column(name = "mark", nullable = false)
    private Integer mark;
    @Column(name = "feedback")
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

    public UserRole getEstimatorRole() {
        return estimatorRole;
    }

    public void setEstimatorRole(UserRole estimatorRole) {
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
