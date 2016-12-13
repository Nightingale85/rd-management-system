package com.epam.rd.frontend.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ProgramInfo {
    private Long programId;
    private String title;
    private LocalDate start;
    private LocalDate end;
    private Map<Long, String> modules;
    private List<TopicInfo> topicsByModuleId;

    public ProgramInfo() {
    }

    public ProgramInfo(Long programId, String title, LocalDate start, LocalDate end, Map<Long, String> modules,
                       List<TopicInfo> topicsByModuleId) {
        this.programId = programId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.modules = modules;
        this.topicsByModuleId = topicsByModuleId;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public Map<Long, String> getModules() {
        return modules;
    }

    public void setModules(Map<Long, String> modules) {
        this.modules = modules;
    }

    public List<TopicInfo> getTopicsByModuleId() {
        return topicsByModuleId;
    }

    public void setTopicsByModuleId(List<TopicInfo> topicsByModuleId) {
        this.topicsByModuleId = topicsByModuleId;
    }
}
