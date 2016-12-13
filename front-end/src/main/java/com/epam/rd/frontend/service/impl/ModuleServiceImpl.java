package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ModuleDtoCreate;
import com.epam.rd.frontend.component.ModuleWithTopics;
import com.epam.rd.frontend.service.ModuleService;
import com.epam.rd.frontend.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
public class ModuleServiceImpl implements ModuleService {

    @Value("/module/{moduleId}")
    private String moduleURL;

    @Value("/program/{programId}/modules")
    private String moduleListURL;

    private RestOperations restOperations;
    private TopicService topicService;

    @Autowired
    public ModuleServiceImpl(RestOperations restOperations, TopicService topicService) {
        this.restOperations = restOperations;
        this.topicService = topicService;
    }

    @Override
    public ModuleDto getModuleById(Long moduleId) {
        return restOperations
                .getForObject(moduleURL, ModuleDto.class, moduleId);
    }

    @Override
    public List<ModuleDto> getModulesByProgramId(Long programId) {
        List<ModuleDto>  moduleDtoList = restOperations.exchange(
                moduleListURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ModuleDto>>() {
                },
                programId)
                .getBody();

        return moduleDtoList == null ? Collections.emptyList() : moduleDtoList;
    }
    @Override
    public List<ModuleWithTopics> getModulesWithTopics(Long programId) {
        List<ModuleWithTopics> list = new ArrayList<>();
        for (ModuleDto moduleDto : getModulesByProgramId(programId)) {
            ModuleWithTopics module = new ModuleWithTopics();
            module.setModuleDto(moduleDto);
            module.setTopicDtoList(topicService.getTopicsByModuleId(moduleDto.getId()));
            list.add(module);
        }
        return list;
    }
    @Override
    public Long addModule(ModuleDtoCreate dtoCreate) {
        HttpEntity<ModuleDtoCreate> entity = new HttpEntity<>(dtoCreate);
        ResponseEntity<ModuleDto> module = restOperations
                .exchange("/module",
                        HttpMethod.POST,
                        entity,
                        ModuleDto.class);
        return module.getBody().getId();
    }
    @Override
    public void deleteModuleById(Long moduleId) {
        restOperations.delete("/module/{id}", moduleId);
    }

    @Override
    public ModuleDto updateModule(ModuleDto moduleDto) {
        HttpEntity<ModuleDto> entity = new HttpEntity<>(moduleDto);
        ResponseEntity<ModuleDto> responseEntity = restOperations
                .exchange("/module/{id}",
                        HttpMethod.PUT,
                        entity,
                        ModuleDto.class,
                        moduleDto.getId());
        return responseEntity.getBody();
    }
}
