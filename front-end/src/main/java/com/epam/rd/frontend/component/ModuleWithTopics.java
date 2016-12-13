package com.epam.rd.frontend.component;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.TopicDto;

import java.util.List;
import java.util.Objects;


public class ModuleWithTopics {

    private ModuleDto moduleDto;
    private List<TopicDto> topicDtoList;

    public ModuleDto getModuleDto() {
        return moduleDto;
    }

    public void setModuleDto(ModuleDto moduleDto) {
        this.moduleDto = moduleDto;
    }

    public List<TopicDto> getTopicDtoList() {
        return topicDtoList;
    }

    public void setTopicDtoList(List<TopicDto> topicDtoList) {
        this.topicDtoList = topicDtoList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleWithTopics that = (ModuleWithTopics) o;
        return Objects.equals(moduleDto, that.moduleDto) &&
                Objects.equals(topicDtoList, that.topicDtoList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleDto, topicDtoList);
    }

}
