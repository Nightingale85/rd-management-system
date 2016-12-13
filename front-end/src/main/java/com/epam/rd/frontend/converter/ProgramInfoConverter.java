package com.epam.rd.frontend.converter;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.frontend.model.ProgramInfo;
import com.epam.rd.frontend.model.ProgramTitles;
import com.epam.rd.frontend.model.TopicInfo;
import com.epam.rd.frontend.service.info.TopicInfoFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProgramInfoConverter {
    private TopicInfoFacade topicInfoFacade;

    @Autowired
    public ProgramInfoConverter(TopicInfoFacade topicInfoFacade) {
        this.topicInfoFacade = topicInfoFacade;
    }

    public ProgramTitles convert(List<ProgramDto> programDtoList) {
        Map<Long, String> programTitles = new HashMap<>();
        for (ProgramDto programDto : programDtoList) {
            programTitles.put(programDto.getId(), programDto.getTitle());
        }
        return new ProgramTitles(programTitles);
    }

    public ProgramInfo convert(ProgramDto programDto, List<ModuleDto> moduleDtoList) {
        Map<Long, String> moduleTitles = getModules(moduleDtoList);
        List<TopicInfo> topicInfoList = null;
        if (!moduleDtoList.isEmpty()) {
            Long id = moduleDtoList.get(0).getId();
            topicInfoList = topicInfoFacade.getTopicInfoListByModuleId(id);
        }
        return new ProgramInfo(programDto.getId(), programDto.getTitle(), programDto.getStartDate(), programDto
                .getEndDate(), moduleTitles, topicInfoList);
    }

    public ProgramInfo convert(ProgramDto programDto, List<ModuleDto> moduleDtoList, Long moduleId) {
        Map<Long, String> moduleTitles = getModules(moduleDtoList);
        List<TopicInfo> topicInfoList = null;
        if (!moduleDtoList.isEmpty()) {
            topicInfoList = topicInfoFacade.getTopicInfoListByModuleId(moduleId);
        }
        return new ProgramInfo(programDto.getId(), programDto.getTitle(), programDto.getStartDate(), programDto
                .getEndDate(), moduleTitles, topicInfoList);
    }

    private Map<Long, String> getModules(List<ModuleDto> moduleDtoList) {
        Map<Long, String> moduleTitles = new HashMap<>();
        for (ModuleDto moduleDto : moduleDtoList) {
            moduleTitles.put(moduleDto.getId(), moduleDto.getTitle());
        }
        return moduleTitles;
    }
}
