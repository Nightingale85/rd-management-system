package com.epam.rd.backend.web.controller.populator;

import com.epam.rd.backend.core.model.Program;
import com.epam.rd.dto.ProgramDtoCreate;

import java.time.LocalDate;

public final class TempDataFactory {
    private TempDataFactory() {
    }

    public static Program createProgram()
    {
        Program program = new Program();
        program.setId(1L);
        program.setName("Name 1");
        program.setLinkToDescription("Description 1");
        program.setDateStart(LocalDate.parse("2016-01-01"));
        program.setDateEnd(LocalDate.parse("2016-06-01"));
        return program;
    }

    public static ProgramDtoCreate createProgramDtoCreate()
    {
        ProgramDtoCreate programDtoCreate = new ProgramDtoCreate();
        programDtoCreate.setTitle("Name 2");
        programDtoCreate.setDescription("Description 2");
        programDtoCreate.setStartDate(LocalDate.parse("2016-01-01"));
        programDtoCreate.setEndDate(LocalDate.parse("2016-12-01"));
        return programDtoCreate;
    }
}
