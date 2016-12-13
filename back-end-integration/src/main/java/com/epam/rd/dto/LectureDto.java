package com.epam.rd.dto;

import java.time.LocalDateTime;

public class LectureDto {
    private Long id;
    private String title;
    private LocalDateTime dateOfLecture;
    private String typeOfPlace;
    private String device;
    private String linkTopicEpam;
    private String linkYoutube;
    private String linkVideoPortalEpam;
    private String agenda;
    private String description;
    private Long topicId;

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

    public LocalDateTime getDateOfLecture() {
        return dateOfLecture;
    }

    public void setDateOfLecture(LocalDateTime dateOfLecture) {
        this.dateOfLecture = dateOfLecture;
    }

    public String getTypeOfPlace() {
        return typeOfPlace;
    }

    public void setTypeOfPlace(String typeOfPlace) {
        this.typeOfPlace = typeOfPlace;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLinkTopicEpam() {
        return linkTopicEpam;
    }

    public void setLinkTopicEpam(String linkTopicEpam) {
        this.linkTopicEpam = linkTopicEpam;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public String getLinkVideoPortalEpam() {
        return linkVideoPortalEpam;
    }

    public void setLinkVideoPortalEpam(String linkVideoPortalEpam) {
        this.linkVideoPortalEpam = linkVideoPortalEpam;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
}
