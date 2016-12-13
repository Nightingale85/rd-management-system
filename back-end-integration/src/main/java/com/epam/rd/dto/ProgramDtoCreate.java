package com.epam.rd.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgramDtoCreate {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Long> modulesId;

    public ProgramDtoCreate() {
        modulesId = new ArrayList<>();
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Long> getModulesId() {
        return modulesId;
    }

    public void setModulesId(List<Long> modulesId) {
        this.modulesId = modulesId;
    }
}
