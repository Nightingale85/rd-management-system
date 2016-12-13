package com.epam.rd.dto;

import java.util.ArrayList;
import java.util.List;

public class ModuleDtoCreate {
    private String title;
    private String description;
    private Long programId;
    private List<Long> topicsId;

    public ModuleDtoCreate() {
        topicsId = new ArrayList<>();
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

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public List<Long> getTopicsId() {
        return topicsId;
    }

    public void setTopicsId(List<Long> topicsId) {
        this.topicsId = topicsId;
    }
}
