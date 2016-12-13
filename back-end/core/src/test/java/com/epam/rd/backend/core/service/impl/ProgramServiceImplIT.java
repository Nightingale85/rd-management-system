package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.TempDataFactory;
import com.epam.rd.backend.core.config.SpringCoreConfig;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.model.Program;
import com.epam.rd.backend.core.repository.ProgramRepository;
import com.epam.rd.backend.core.service.ProgramService;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.util.StringUtils.isEmpty;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringCoreConfig.class)
@TestPropertySource(value = "classpath:app-config-test.properties")
public class ProgramServiceImplIT {

    @Autowired
    private ProgramRepository repository;

    @Autowired
    private ProgramService service;

    @Test
    @Transactional
    public void should_create_program() {
        //GIVEN
        ProgramDtoCreate givenProgram = TempDataFactory.getProgramDtoCreate();
        //WHEN
        ProgramDto expectedProgram = service.createProgram(givenProgram);
        Program actualProgram = repository.findOne(expectedProgram.getId());
        //THEN
        assertTrue(repository.findAll().size() >= 1);
        assert_program_equals(actualProgram, expectedProgram);
    }

    @Test
    @Transactional
    public void should_return_program_by_id() {
        //GIVEN
        Program givenProgram = TempDataFactory.getProgram();
        Program actualProgram = repository.save(givenProgram);
        //WHEN
        ProgramDto expectedProgram = service.getProgramById(actualProgram.getId());
        //THEN
        assert_program_equals(actualProgram, expectedProgram);
    }

    @Test
    @Transactional
    public void should_return_programs_list() {
        //GIVEN
        Program givenProgram = TempDataFactory.getProgram();
        repository.save(givenProgram);
        //WHEN
        List<ProgramDto> expectedProgramList = service.getAllProgram();
        //THEN
        assertFalse(isEmpty(expectedProgramList));
        assertTrue(expectedProgramList.size() >= 1);
    }

    @Test
    @Transactional
    public void should_update_program() {
        //GIVEN
        Program givenProgram = repository.save(TempDataFactory.getProgram());
        ProgramDto actualProgram = TempDataFactory.getProgramDto();
        //WHEN
        actualProgram.setTitle("New Program Name");
        actualProgram.setId(givenProgram.getId());
        ProgramDto expectedProgram = service.updateProgram(actualProgram);
        //THEN
        assert_program_equals(actualProgram, expectedProgram);
    }

    @Test(expected = EntityDoesNotExistException.class)
    @Transactional
    public void should_delete_program_by_id() {
        //GIVEN
        Program givenProgram = TempDataFactory.getProgram();
        Program actualProgram = repository.save(givenProgram);
        //WHEN
        service.deleteProgramById(actualProgram.getId());
        service.deleteProgramById(actualProgram.getId());
    }

    private void assert_program_equals(Program actualProgram, ProgramDto expectedProgram) {
        assertEquals(actualProgram.getId(), expectedProgram.getId());
        assertEquals(actualProgram.getName(), expectedProgram.getTitle());
        assertEquals(actualProgram.getLinkToDescription(), expectedProgram.getDescription());
        assertEquals(actualProgram.getDateStart(), expectedProgram.getStartDate());
        assertEquals(actualProgram.getDateEnd(), expectedProgram.getEndDate());
    }

    private void assert_program_equals(ProgramDto actualProgram, ProgramDto expectedProgram) {
        assertEquals(actualProgram.getId(), expectedProgram.getId());
        assertEquals(actualProgram.getTitle(), expectedProgram.getTitle());
        assertEquals(actualProgram.getDescription(), expectedProgram.getDescription());
        assertEquals(actualProgram.getStartDate(), expectedProgram.getStartDate());
        assertEquals(actualProgram.getEndDate(), expectedProgram.getEndDate());
    }
}