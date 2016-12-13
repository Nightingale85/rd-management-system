package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.TempDataFactory;
import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Program;
import com.epam.rd.backend.core.repository.ModuleRepository;
import com.epam.rd.backend.core.repository.ProgramRepository;
import com.epam.rd.backend.core.service.ModuleService;
import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ModuleDtoCreate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;
import static org.springframework.util.StringUtils.isEmpty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreConfig.class)
@TestPropertySource(value = "classpath:app-config-test.properties")
public class ModuleServiceImplIT {
    @Autowired
    private ModuleService service;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ModuleRepository repository;

    private Long programID = 1L;

    @Test
    @Transactional
    public void should_create_module() throws Exception {
        //GIVEN
        Program givenProgram = TempDataFactory.getProgram();
        Program actualProgram = programRepository.save(givenProgram);
        ModuleDtoCreate givenModule = TempDataFactory.getModuleDtoCreate(actualProgram.getId());
        //WHEN
        ModuleDto expectedModule = service.createModule(givenModule);
        Module actualModule = repository.findOne(expectedModule.getId());
        //THEN
        assertNotNull(expectedModule.getId());
        assertNotNull(expectedModule.getProgramId());
        assert_module_equals(actualModule, expectedModule);
    }

    @Test
    @Transactional
    public void should_return_modules_list() {
        //GIVEN
        Module givenModule = TempDataFactory.getModule();
        repository.save(givenModule);
        //WHEN
        List<Module> expectedModuleList = repository.findAll();
        //THEN
        assertFalse(isEmpty(expectedModuleList));
        assertTrue(expectedModuleList.size() >= 1);
    }

    @Test
    @Transactional
    public void should_update_module() {
        //GIVEN
        Program givenProgram = programRepository.save(TempDataFactory.getProgram());
        Module givenModule = TempDataFactory.getModule();
        givenModule.setProgram(givenProgram);
        Module savedModule = repository.save(givenModule);
        ModuleDto actualModule = TempDataFactory.getModuleDto(givenProgram.getId());
        //WHEN
        actualModule.setTitle("New Module Name");
        actualModule.setId(savedModule.getId());
        actualModule.setProgramId(givenProgram.getId());
        actualModule.setDescription(savedModule.getLinkToDescription());
        ModuleDto expectedModule = service.updateModule(actualModule);
        //THEN
        assert_module_equals(actualModule, expectedModule);
    }

    @Test
    @Transactional
    public void should_return_module_by_id() {
        //GIVEN
        Program givenProgram = programRepository.save(TempDataFactory.getProgram());
        Module givenModule = TempDataFactory.getModule();
        givenModule.setProgram(givenProgram);
        Module savedModule = repository.save(givenModule);
        //WHEN
        ModuleDto expectedModule = service.getModuleById(savedModule.getId());
        Module actualModule = repository.findOne(savedModule.getId());
        //THEN
        assert_module_equals(actualModule, expectedModule);
    }

    @Test
    @Transactional
    public void should_return_module_by_program_id() {
        //GIVEN
        Program givenProgram = programRepository.save(TempDataFactory.getProgram());
        Long givenProgramId = givenProgram.getId();
        Module givenModule = TempDataFactory.getModule();
        givenModule.setProgram(givenProgram);
        givenProgram.addModule(givenModule);
        repository.save(givenModule);
        //WHEN
        List<ModuleDto> expectedModuleList = service.getModulesByProgramId(givenProgramId);
        //THEN
        assertFalse(isEmpty(expectedModuleList));
        assertEquals(expectedModuleList.size(), 1);
        assertEquals(expectedModuleList.get(0).getProgramId(), givenProgramId);
    }

    @Test(expected = EntityDoesNotExistException.class)
    @Transactional
    public void should_delete_module_by_id() {
        //GIVEN
        Module givenModule = TempDataFactory.getModule();
        Module actualModule = repository.save(givenModule);
        //WHEN
        service.deleteModuleById(actualModule.getId());
        service.deleteModuleById(actualModule.getId());
    }

    private void assert_module_equals(Module actualModule, ModuleDto expectedModule) {
        assertEquals(actualModule.getId(), expectedModule.getId());
        assertEquals(actualModule.getName(), expectedModule.getTitle());
        assertEquals(actualModule.getProgram().getId(), expectedModule.getProgramId());
        assertEquals(actualModule.getLinkToDescription(), expectedModule.getDescription());
    }

    private void assert_module_equals(ModuleDto actualModule, ModuleDto expectedModule) {
        assertEquals(actualModule.getId(), expectedModule.getId());
        assertEquals(actualModule.getTitle(), expectedModule.getTitle());
        assertEquals(actualModule.getProgramId(), expectedModule.getProgramId());
        assertEquals(actualModule.getDescription(), expectedModule.getDescription());
    }
}


