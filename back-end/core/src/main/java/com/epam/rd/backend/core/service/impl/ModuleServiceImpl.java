package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.ModuleDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Program;
import com.epam.rd.backend.core.repository.ModuleRepository;
import com.epam.rd.backend.core.repository.ProgramRepository;
import com.epam.rd.backend.core.service.ModuleService;
import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ModuleDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ModuleServiceImpl implements ModuleService {
    private ProgramRepository programRepository;
    private ModuleRepository repository;
    private ModuleDtoConverter converter;

    @Autowired
    public ModuleServiceImpl(ProgramRepository programRepository,
                             ModuleRepository repository,
                             ModuleDtoConverter converter) {
        this.programRepository = programRepository;
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public ModuleDto createModule(ModuleDtoCreate dto) {
        Module module = converter.convert(dto);
        Program program = programRepository.findOne(dto.getProgramId());
        module.setProgram(program);
        return converter.convert(repository.save(module));
    }

    @Override
    public ModuleDto updateModule(ModuleDto dto) {
        Module module = converter.convert(dto);
        if (!isModuleExist(module.getId())) {
            throw new EntityDoesNotExistException(String.format("Module with ID=%s does not exist", module.getId()));
        }
        Program program = programRepository.findOne(dto.getProgramId());
        module.setProgram(program);
        return converter.convert(repository.save(module));
    }

    @Override
    @Transactional
    public List<ModuleDto> getAllModule() {
        List<Module> modules = repository.findAll();
        return converter.convertList(modules);
    }

    @Override
    @Transactional
    public ModuleDto getModuleById(Long id) {
        Module module = repository.findOne(id);
        if (module == null) {
            throw new EntityDoesNotExistException(String.format("Cannot find module by ID=%s", id));
        }
        return converter.convert(module);
    }

    @Override
    @Transactional
    public List<ModuleDto> getModulesByProgramId(Long programId) {
        Program program = getProgramById(programId);
        final List<Module> modules = program.getModules();
        return converter.convertList(modules);
    }

    @Override
    public void deleteModuleById(Long id) {
        if (!isModuleExist(id)) {
            throw new EntityDoesNotExistException(String.format("Cannot find module by ID=%s", id));
        }
        repository.delete(id);
    }

    private boolean isModuleExist(Long id) {
        return !Objects.isNull(id) && repository.findOne(id) != null;
    }

    private Program getProgramById(Long programId) {
        Program program = programRepository.findOne(programId);
        if (program == null) {
            throw new EntityDoesNotExistException(String.format("Program with ID=%s does not exist", programId));
        }
        return program;
    }
}
