package com.epam.rd.frontend.service.info;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.frontend.converter.ProgramInfoConverter;
import com.epam.rd.frontend.model.ProgramInfo;
import com.epam.rd.frontend.model.ProgramTitles;
import com.epam.rd.frontend.service.ModuleService;
import com.epam.rd.frontend.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProgramInfoFacade {
    private ProgramService programService;
    private ModuleService moduleService;
    private ProgramInfoConverter converter;

    @Autowired
    public ProgramInfoFacade(ProgramService programService,
                             ModuleService moduleService,
                             ProgramInfoConverter converter) {
        this.programService = programService;
        this.moduleService = moduleService;
        this.converter = converter;
    }

    public ProgramTitles getProgramsInfo() {
        List<ProgramDto> programDtoList = programService.getAllPrograms();
        return converter.convert(programDtoList);
    }

    public ProgramInfo getProgramInfoByProgramId(Long programId) {
        ProgramDto programDto = programService.getProgramById(programId);
        List<ModuleDto> moduleDtoList = moduleService.getModulesByProgramId(programId);
        return converter.convert(programDto, moduleDtoList);
    }

    public ProgramInfo getProgramInfoByProgramIdModuleId(Long programId, Long moduleId) {
        ProgramDto programDto = programService.getProgramById(programId);
        List<ModuleDto> moduleDtoList = moduleService.getModulesByProgramId(programId);
        return converter.convert(programDto, moduleDtoList, moduleId);
    }


}
