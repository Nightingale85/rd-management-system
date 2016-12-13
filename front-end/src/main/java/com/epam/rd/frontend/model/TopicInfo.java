package com.epam.rd.frontend.model;

import java.util.Objects;

public class TopicInfo {
    private Long topicId;
    private String topicTitle;
    private String lectureTitle;
    private String taskTitle;

    public TopicInfo(Long topicId, String topicTitle, String lectureTitle, String taskTitle) {
        this.topicId = Objects.requireNonNull(topicId, "Topic ID cannot be NULL");
        this.topicTitle = topicTitle;
        this.lectureTitle = lectureTitle;
        this.taskTitle = taskTitle;
    }

    public Long getTopicId() {
        return topicId;
    }

    public String getTopicTitle() {
        return topicTitle;
    }

    public String getLectureTitle() {
        return lectureTitle;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TopicInfo topicInfo = (TopicInfo) o;
        return Objects.equals(topicId, topicInfo.topicId) &&
                Objects.equals(topicTitle, topicInfo.topicTitle) &&
                Objects.equals(lectureTitle, topicInfo.lectureTitle) &&
                Objects.equals(taskTitle, topicInfo.taskTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicId, topicTitle, lectureTitle, taskTitle);
    }
}