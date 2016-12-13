package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.ProgramDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.model.Program;
import com.epam.rd.backend.core.repository.ProgramRepository;
import com.epam.rd.backend.core.service.ProgramService;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramServiceImpl implements ProgramService {
    private ProgramRepository repository;
    private ProgramDtoConverter converter;

    @Autowired
    public ProgramServiceImpl(ProgramRepository repository, ProgramDtoConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public ProgramDto createProgram(ProgramDtoCreate dto) {
        Program program = converter.convert(dto);
        return converter.convert(repository.save(program));
    }

    @Override
    public ProgramDto updateProgram(ProgramDto dto) {
        Program program = converter.convert(dto);
        if (!isProgramExist(program.getId())) {
            throw new EntityDoesNotExistException(String.format("Program with ID=%s does not exist", program.getId()));
        }
        return converter.convert(repository.save(program));
    }

    private boolean isProgramExist(Long id) {
        return id != null && repository.findOne(id) != null;
    }

    @Override
    @Transactional
    public List<ProgramDto> getAllProgram() {
        List<Program> programs = repository.findAll();
        return programs.stream()
                .map(program -> converter.convert(program))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProgramDto getProgramById(Long id) {
        Program program = repository.findOne(id);
        if (program == null) {
            throw new EntityDoesNotExistException(String.format("Cannot find program by ID=%s", id));
        }
        return converter.convert(program);
    }

    @Override
    public void deleteProgramById(Long id) {
        if (!isProgramExist(id)) {
            throw new EntityDoesNotExistException(String.format("Cannot find program by ID=%s", id));
        }
        repository.delete(id);
    }
}
