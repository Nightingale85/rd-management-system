package com.epam.rd.frontend.service;

import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface ProgramService {

    ProgramDto getProgramById(Long id);

    List<ProgramDto> getAllPrograms();

    @Secured("ROLE_MANAGER")
    Long addProgram(ProgramDtoCreate dtoCreate);

    @Secured("ROLE_MANAGER")
    ProgramDto updateProgram(ProgramDto programDto);
}
