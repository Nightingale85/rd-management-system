package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.TopicDto;
import com.epam.rd.frontend.component.ProgramInfo;
import com.epam.rd.frontend.service.ModuleService;
import com.epam.rd.frontend.service.ProgramInfoService;
import com.epam.rd.frontend.service.ProgramService;
import com.epam.rd.frontend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ProgramInfoServiceImpl implements ProgramInfoService {

    private ProgramService programService;
    private ModuleService moduleService;
    private TopicService topicService;

    @Autowired
    public ProgramInfoServiceImpl(ProgramService programService,
                                  ModuleService moduleService,
                                  TopicService topicService) {
        this.programService = programService;
        this.moduleService = moduleService;
        this.topicService = topicService;
    }

    @Override
    public ProgramInfo getProgramInfoByProgramId(Long programId) {
        ProgramDto program = programService.getProgramById(programId);
        List<ModuleDto> modules = moduleService.getModulesByProgramId(programId);
        List<TopicDto> topics;
        ModuleDto currentModule;
        if (!modules.isEmpty()) {
            currentModule = modules.get(0);
            topics = topicService.getTopicsByModuleId(currentModule.getId());
        } else {
            currentModule = new ModuleDto();
            topics = Collections.emptyList();
        }
        return new ProgramInfo(program.getTitle(), modules, currentModule, topics);
    }

    @Override
    public ProgramInfo getProgramInfoByModuleId(Long moduleId) {
        ModuleDto currentModule = moduleService.getModuleById(moduleId);
        Long programId = currentModule.getProgramId();
        List<ModuleDto> modules = moduleService.getModulesByProgramId(programId);
        ProgramDto program = programService.getProgramById(programId);
        List<TopicDto> topics = topicService.getTopicsByModuleId(currentModule.getId());

        return new ProgramInfo(program.getTitle(), modules, currentModule, topics);
    }
}
