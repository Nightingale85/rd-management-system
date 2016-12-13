package com.epam.rd.dto;

import java.util.Objects;

public class TopicDto {
    private Long id;
    private String title;
    private String description;
    private Long moduleId;
    private Long lectureId;
    private Long practicalTaskId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopicDto)) {
            return false;
        }

        TopicDto topicDto = (TopicDto) o;

        return Objects.equals(id, topicDto.getId()) &&
                Objects.equals(title, topicDto.getTitle()) &&
                Objects.equals(description, topicDto.getDescription()) &&
                Objects.equals(moduleId, topicDto.getModuleId()) &&
                Objects.equals(lectureId, topicDto.getLectureId()) &&
                Objects.equals(practicalTaskId, topicDto.getPracticalTaskId());
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, title, description, moduleId, lectureId, practicalTaskId);
    }
}
