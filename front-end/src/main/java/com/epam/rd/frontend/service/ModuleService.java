package com.epam.rd.frontend.service;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ModuleDtoCreate;
import com.epam.rd.frontend.component.ModuleWithTopics;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface ModuleService {

    ModuleDto getModuleById(Long moduleId);

    List<ModuleDto> getModulesByProgramId(Long programId);

    List<ModuleWithTopics> getModulesWithTopics(Long programId);

    @Secured("ROLE_MANAGER")
    Long addModule(ModuleDtoCreate dtoCreate);

    @Secured("ROLE_MANAGER")
    void deleteModuleById(Long moduleId);

    @Secured("ROLE_MANAGER")
    ModuleDto updateModule(ModuleDto moduleDto);
}
