package com.epam.rd.dto;

public class TopicDtoCreate {
    private String title;
    private String description;
    private Long moduleId;
    private Long lectureId;
    private Long practicalTaskId;

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

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    public Long getPracticalTaskId() {
        return practicalTaskId;
    }

    public void setPracticalTaskId(Long practicalTaskId) {
        this.practicalTaskId = practicalTaskId;
    }
}
