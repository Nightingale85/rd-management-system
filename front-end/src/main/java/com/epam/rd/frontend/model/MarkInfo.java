package com.epam.rd.frontend.model;

public class MarkInfo {
    private String practicalTaskTitle;
    private Integer lecturerMark;
    private Integer mentorMark;
    private String mentorFeedback;
    private String lecturerFeedback;

    public String getLecturerFeedback() {
        return lecturerFeedback;
    }

    public void setLecturerFeedback(String lecturerFeedback) {
        this.lecturerFeedback = lecturerFeedback;
    }

    public Integer getLecturerMark() {
        return lecturerMark;
    }

    public void setLecturerMark(Integer lecturerMark) {
        this.lecturerMark = lecturerMark;
    }

    public Integer getMentorMark() {
        return mentorMark;
    }

    public void setMentorMark(Integer mentorMark) {
        this.mentorMark = mentorMark;
    }

    public String getMentorFeedback() {
        return mentorFeedback;
    }

    public void setMentorFeedback(String mentorFeedback) {
        this.mentorFeedback = mentorFeedback;
    }

    public String getPracticalTaskTitle() {
        return practicalTaskTitle;
    }

    public void setPracticalTaskTitle(String practicalTaskTitle) {
        this.practicalTaskTitle = practicalTaskTitle;
    }

}
