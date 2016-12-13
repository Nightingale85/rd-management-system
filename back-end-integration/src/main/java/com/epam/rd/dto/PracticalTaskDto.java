package com.epam.rd.dto;

import java.time.LocalDate;

public class PracticalTaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private Long topicId;
    private String functionalRequirements;
    private String nonFunctionalRequirements;
    private String scoreSystem;
    private String additionalMaterials;
    private String guide;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getFunctionalRequirements() {
        return functionalRequirements;
    }

    public void setFunctionalRequirements(String functionalRequirements) {
        this.functionalRequirements = functionalRequirements;
    }

    public String getNonFunctionalRequirements() {
        return nonFunctionalRequirements;
    }

    public void setNonFunctionalRequirements(String nonFunctionalRequirements) {
        this.nonFunctionalRequirements = nonFunctionalRequirements;
    }

    public String getScoreSystem() {
        return scoreSystem;
    }

    public void setScoreSystem(String scoreSystem) {
        this.scoreSystem = scoreSystem;
    }

    public String getAdditionalMaterials() {
        return additionalMaterials;
    }

    public void setAdditionalMaterials(String additionalMaterials) {
        this.additionalMaterials = additionalMaterials;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }
}
