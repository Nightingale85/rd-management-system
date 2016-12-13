package com.epam.rd.backend.core.service;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ModuleDtoCreate;

import java.util.List;

public interface ModuleService {
    ModuleDto createModule(ModuleDtoCreate module);

    ModuleDto updateModule(ModuleDto module);

    List<ModuleDto> getAllModule();

    ModuleDto getModuleById(Long id);

    List<ModuleDto> getModulesByProgramId(Long programId);

    void deleteModuleById(Long iD);
}
