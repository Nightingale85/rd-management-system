package com.epam.rd.frontend.service.info;

import com.epam.rd.dto.LectureDto;
import com.epam.rd.dto.PracticalTaskDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.frontend.converter.TopicInfoConverter;
import com.epam.rd.frontend.model.TopicInfo;
import com.epam.rd.frontend.service.LectureService;
import com.epam.rd.frontend.service.PracticalTaskService;
import com.epam.rd.frontend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TopicInfoFacade {
    private TopicService topicService;
    private LectureService lectureService;
    private PracticalTaskService taskService;
    private TopicInfoConverter converter;

    @Autowired
    public TopicInfoFacade(TopicService topicService, LectureService lectureService,
                           PracticalTaskService taskService, TopicInfoConverter converter) {
        this.topicService = topicService;
        this.lectureService = lectureService;
        this.taskService = taskService;
        this.converter = converter;
    }

    public List<TopicInfo> getTopicInfoListByModuleId(Long moduleId) {
        List<TopicInfo> topicInfoList = new ArrayList<>();
        List<TopicDto> topicDtoList = topicService.getTopicsByModuleId(moduleId);
        for (TopicDto topicDto : topicDtoList) {
            topicInfoList.add(getTopicInfo(topicDto));
        }
        return topicInfoList;
    }

    private TopicInfo getTopicInfo(TopicDto topicDto) {
        LectureDto lectureDto = lectureService.getLectureDtoByTopicId(topicDto.getId());
        PracticalTaskDto taskDto = taskService.getPracticalTaskDtoByTopicId(topicDto.getId());
        return converter.convert(topicDto, lectureDto, taskDto);
    }
}
