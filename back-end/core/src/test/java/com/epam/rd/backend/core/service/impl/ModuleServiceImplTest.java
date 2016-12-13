package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.ModuleDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Program;
import com.epam.rd.backend.core.repository.ModuleRepository;
import com.epam.rd.backend.core.repository.ProgramRepository;
import com.epam.rd.dto.ModuleDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ModuleServiceImplTest {
    @Mock
    private ModuleRepository repositoryMock;

    @Mock
    private ModuleDtoConverter converterMock;

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private Program programMock;

    @InjectMocks
    private ModuleServiceImpl service;

    @Test
    public void should_find_module_by_program_id() {
        //GIVEN
        Program program = new Program();
        program.setId(1L);
        program.setName("Program1");
        Module module1 = new Module();
        module1.setId(1L);
        module1.setName("Module 1");
        module1.setProgram(program);
        Module module2 = new Module();
        module2.setId(2L);
        module2.setName("Module 2");
        module2.setProgram(program);
        List<Module> moduleList = Arrays.asList(module1, module2);
        ModuleDto moduleDto1 = new ModuleDto();
        ModuleDto moduleDto2 = new ModuleDto();
        moduleDto1.setId(1L);
        moduleDto1.setProgramId(1L);
        moduleDto1.setDescription("ModuleDTO 1 description");
        moduleDto1.setTitle("ModuleDTO 1 title");
        moduleDto2.setId(2L);
        moduleDto2.setProgramId(1L);
        moduleDto2.setDescription("ModuleDTO 2 description");
        moduleDto2.setTitle("ModuleDTO 2 title");
        List<ModuleDto> moduleDtoList = Arrays.asList(moduleDto1, moduleDto2);
        when(programRepository.findOne(anyLong())).thenReturn(programMock);
        when(programMock.getModules()).thenReturn(moduleList);
        when(converterMock.convertList(moduleList)).thenReturn(moduleDtoList);

        //WHEN
        List<ModuleDto> dtoList = service.getModulesByProgramId(1L);

        //THEN
        verify(programMock, times(1)).getModules();
        verify(programRepository, times(1)).findOne(anyLong());
        verify(converterMock, times(1)).convertList(moduleList);
        assertThat(dtoList.isEmpty(), is(false));
        assertEquals("ModuleDTO 1 description", dtoList.get(0).getDescription());
        assertEquals("ModuleDTO 1 title", dtoList.get(0).getTitle());
        assertEquals(1L, dtoList.get(0).getId().longValue());
        assertEquals(1L, dtoList.get(0).getProgramId().longValue());
    }

    @Test
    public void should_return_empty_list_when_modules_not_found_by_program_id() {
        //GIVEN
        Program program = new Program();
        program.setId(1L);
        when(programRepository.findOne(anyLong())).thenReturn(program);
        when(programMock.getModules()).thenReturn(Collections.emptyList());

        //WHEN
        List<ModuleDto> dtoList = service.getModulesByProgramId(anyLong());

        //THEN
        assertThat(dtoList, empty());
    }

    @Test(expected = EntityDoesNotExistException.class)
    public void should_trow_exception_when_program_with_id_not_found() {
        //GIVEN
        when(programRepository.findOne(anyLong())).thenReturn(null);

        //WHEN
        service.getModulesByProgramId(anyLong());

        //THEN
    }
}