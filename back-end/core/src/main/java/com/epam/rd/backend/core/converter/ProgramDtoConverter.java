package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Program;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ProgramDtoConverter {
    public ProgramDto convert(Program program) {
        ProgramDto dto = new ProgramDto();
        dto.setId(program.getId());
        dto.setTitle(program.getName());
        dto.setDescription(program.getLinkToDescription());
        dto.setStartDate(program.getDateStart());
        dto.setEndDate(program.getDateEnd());
        List<Long> modulesId = program.getModules()
                .stream()
                .map(Module::getId)
                .collect(Collectors.toList());
        dto.setModulesId(modulesId);
        return dto;
    }

    public Program convert(ProgramDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Program DTO cannot be NULL");
        }
        Program program = new Program();
        program.setId(dto.getId());
        program.setName(dto.getTitle());
        program.setLinkToDescription(dto.getDescription());
        program.setDateStart(dto.getStartDate());
        program.setDateEnd(dto.getEndDate());
        return program;
    }

    public Program convert(ProgramDtoCreate dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Program DTO cannot be NULL");
        }
        Program program = new Program();
        program.setName(dto.getTitle());
        program.setLinkToDescription(dto.getDescription());
        program.setDateStart(dto.getStartDate());
        program.setDateEnd(dto.getEndDate());
        return program;
    }
}
