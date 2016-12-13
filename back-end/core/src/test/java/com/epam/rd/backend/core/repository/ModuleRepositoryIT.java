package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.TempDataFactory;
import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Program;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;


@ContextConfiguration(classes = SpringCoreConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(value = "classpath:app-config-test.properties")
@Transactional
public class ModuleRepositoryIT {

    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ProgramRepository programRepository;
    private Module module;

    @Before
    public void init() {
        module = TempDataFactory.getModule();
    }

    @Test
    public void should_create_and_read_module() {
        //GIVEN
        moduleRepository.save(module);
        //WHEN
        Module moduleFromDB = moduleRepository.findOne(module.getId());
        //THEN
        assertEquals(module.getId(), moduleFromDB.getId());
        assertEquals(module.getName(), moduleFromDB.getName());
        assertEquals(module.getLinkToDescription(), moduleFromDB.getLinkToDescription());
    }

    @Test
    public void should_update_module() {
        //GIVEN
        String newName = "New Name";
        module.setName(newName);
        //WHEN
        moduleRepository.save(module);
        //THEN
        assertEquals(newName, moduleRepository.findOne(module.getId()).getName());
    }

    @Test
    public void should_delete_module() {
        //GIVEN
        moduleRepository.save(module);
        //WHEN
        moduleRepository.delete(module.getId());
        //THEN
        assertNull(moduleRepository.findOne(module.getId()));
    }

    @Test
    public void should_return_list_of_modules_by_program_id() {
        //GIVEN
        Program program = TempDataFactory.getProgram();
        module.setProgram(program);
        List<Module> givenModules = new ArrayList<>();
        givenModules.add(module);
        moduleRepository.save(module);
        programRepository.save(program);
        long programId = 1L;
        //WHEN
        List<Module> modules = moduleRepository.findModulesByProgramId(programId);
        //THEN
        assertFalse(modules.isEmpty());
        assertTrue(modules.size() == 1);
    }
}