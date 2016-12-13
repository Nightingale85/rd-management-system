package com.epam.rd.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleDto {
    private Long id;
    private String title;
    private String description;
    private Long programId;
    private List<Long> topicsId;

    public ModuleDto() {
        topicsId = new ArrayList<>();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModuleDto)) {
            return false;
        }

        ModuleDto moduleDto = (ModuleDto) o;

        return Objects.equals(id, moduleDto.getId()) &&
                Objects.equals(title, moduleDto.getTitle()) &&
                Objects.equals(description, moduleDto.getDescription()) &&
                Objects.equals(programId, moduleDto.getProgramId()) &&
                Objects.equals(topicsId, moduleDto.getTopicsId());

    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, title, description, programId, topicsId);
    }
}
