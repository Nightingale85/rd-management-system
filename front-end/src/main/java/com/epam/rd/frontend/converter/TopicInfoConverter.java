package com.epam.rd.frontend.converter;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.frontend.model.TopicInfo;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TopicInfoConverter {
    public TopicInfo convert(TopicDto topicDto, LectureDto lectureDto, PracticalTaskDto taskDto) {
        Objects.requireNonNull(topicDto, "Topic DTO cannot be NULL");
        Long topicId = topicDto.getId();
        String topicTitle = topicDto.getTitle();
        String lectureTitle = Objects.isNull(lectureDto) ? null : lectureDto.getTitle();
        String taskTitle = Objects.isNull(taskDto) ? null : taskDto.getTitle();
        return new TopicInfo(topicId, topicTitle, lectureTitle, taskTitle);
    }
}
