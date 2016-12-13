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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ContextConfiguration(classes = SpringCoreConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(value = "classpath:app-config-test.properties")
public class ProgramRepositoryIT {

    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    private Program program;
    private Module module;


    @Before
    public void init() {
        program = TempDataFactory.getProgram();
        module =  TempDataFactory.getModule();
        program.addModule(module);
    }

    @Test
    @Transactional
    public void should_create_and_read_program() {
        //GIVEN
        programRepository.save(program);
        //WHEN
        Program programFromDB = programRepository.findOne(program.getId());
        Module moduleFromDB = moduleRepository.findOne(module.getId());
        //THEN
        assertNotNull(programFromDB.getId());
        assertEquals(program.getName(), programFromDB.getName());
        assertEquals(program.getLinkToDescription(), programFromDB.getLinkToDescription());
        assertEquals(program.getDateStart(), programFromDB.getDateStart());
        assertEquals(program.getDateEnd(), programFromDB.getDateEnd());
        assertEquals(module.getId(), moduleFromDB.getId());
        assertEquals(module.getName(), moduleFromDB.getName());
        assertEquals(module.getLinkToDescription(), moduleFromDB.getLinkToDescription());
    }
}