package com.epam.rd.backend.core.service;

import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;

import java.util.List;

public interface ProgramService {

    ProgramDto createProgram(ProgramDtoCreate dto);

    ProgramDto updateProgram(ProgramDto dto);

    List<ProgramDto> getAllProgram();

    ProgramDto getProgramById(Long id);

    void deleteProgramById(Long id);
}
