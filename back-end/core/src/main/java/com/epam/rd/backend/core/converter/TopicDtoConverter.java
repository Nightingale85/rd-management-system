package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.dto.TopicDtoCreate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TopicDtoConverter extends AbstractConverter<Topic, TopicDto> {
    public TopicDto convert(Topic topic) {
        TopicDto dto = new TopicDto();
        dto.setId(topic.getId());
        dto.setTitle(topic.getName());
        dto.setDescription(topic.getLinkToDescription());
        dto.setModuleId(getModuleId(topic));
        dto.setLectureId(getLectureId(topic));
        dto.setPracticalTaskId(getPracticalTaskId(topic));
        return dto;
    }

    public Topic convert(TopicDto topicDto) {
        if (Objects.isNull(topicDto)) {
            throw new IllegalArgumentException("Topic DTO cannot be NULL");
        }
        Topic topic = new Topic();
        topic.setId(topicDto.getId());
        topic.setName(topicDto.getTitle());
        topic.setLinkToDescription(topicDto.getDescription());
        return topic;
    }

    public Topic convert(TopicDtoCreate topicDto) {
        if (Objects.isNull(topicDto)) {
            throw new IllegalArgumentException("Topic DTO cannot be NULL");
        }
        Topic topic = new Topic();
        topic.setName(topicDto.getTitle());
        topic.setLinkToDescription(topicDto.getDescription());
        return topic;
    }

    private Long getModuleId(Topic topic) {
        if (topic.getModule() != null) {
            return topic.getModule().getId();
        } else {
            return null;
        }
    }

    private Long getLectureId(Topic topic) {
        if (topic.getLecture() != null) {
            return topic.getLecture().getId();
        } else {
            return null;
        }
    }

    private Long getPracticalTaskId(Topic topic) {
        if (topic.getPracticalTask() != null) {
            return topic.getPracticalTask().getId();
        } else {
            return null;
        }
    }
}