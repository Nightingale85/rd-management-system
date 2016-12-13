package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Program;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ModuleDtoCreate;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class ModuleDtoConverterTest {
    private static final long ID = 1L;
    private static final String DESCRIPTION = "Description";
    private static final String TITLE = "Module";
    private ModuleDtoConverter converter = new ModuleDtoConverter();

    @Test
    public void should_convert_module_to_dto() {
        //GIVEN
        Module module = new Module();
        module.setId(ID);
        module.setName(TITLE);
        module.setLinkToDescription(DESCRIPTION);
        Program program = new Program();
        program.setId(ID);
        module.setProgram(program);
        Topic topic = new Topic();
        topic.setId(ID);
        module.setTopics(Collections.singletonList(topic));
        //WHEN
        ModuleDto dto = converter.convert(module);
        //THEN
        assertEquals(module.getId(), dto.getId());
        assertEquals(module.getName(), dto.getTitle());
        assertEquals(module.getLinkToDescription(), dto.getDescription());
        assertEquals(module.getProgram().getId(), dto.getProgramId());
        assertEquals(module.getTopics().get(0).getId(), dto.getTopicsId().get(0));
    }

    @Test
    public void should_convert_dto_to_module() {
        //GIVEN
        ModuleDto dto = new ModuleDto();
        dto.setId(ID);
        //WHEN
        Module module = converter.convert(dto);
        //THEN
        assertEquals(dto.getId(), module.getId());
    }

    @Test(expected = NullPointerException.class)
    public void should_trow_npe_if_dto_as_null_converting_to_module() {
        //GIVEN
        ModuleDto dto = null;
        //WHEN
        converter.convert(dto);
        //THEN
        fail();
    }

    @Test
    public void should_convert_dto_for_create_to_module() {
        //GIVEN
        ModuleDtoCreate dto = new ModuleDtoCreate();
        dto.setTitle("Program");
        //WHEN
        Module module = converter.convert(dto);
        //THEN
        assertEquals(dto.getTitle(), module.getName());
    }

    @Test(expected = NullPointerException.class)
    public void should_trow_npe_if_dto_for_create_as_null_converting_to_module() {
        //GIVEN
        ModuleDtoCreate dto = null;
        //WHEN
        converter.convert(dto);
        //THEN
        fail();
    }

    @Test
    public void should_return_module_with_program_as_null_from_dto() {
        //GIVEN
        ModuleDto dto = new ModuleDto();
        dto.setProgramId(ID);
        //WHEN
        Module module = converter.convert(dto);
        //THEN
        assertNull(module.getProgram());
    }

    @Test
    public void should_return_module_with_topics_as_empty_list_from_dto() {
        //GIVEN
        ModuleDto dto = new ModuleDto();
        dto.setTopicsId(Collections.singletonList(ID));
        //WHEN
        Module module = converter.convert(dto);
        //THEN
        assertTrue(module.getTopics().isEmpty());
    }

    @Test
    public void should_return_module_with_program_as_null_from_dto_for_craete() {
        //GIVEN
        ModuleDtoCreate dto = new ModuleDtoCreate();
        dto.setProgramId(ID);
        //WHEN
        Module module = converter.convert(dto);
        //THEN
        assertNull(module.getProgram());
    }

    @Test
    public void should_return_module_with_topics_as_empty_list_from_dto_for_craete() {
        //GIVEN
        ModuleDtoCreate dto = new ModuleDtoCreate();
        dto.setTopicsId(Collections.singletonList(ID));
        //WHEN
        Module module = converter.convert(dto);
        //THEN
        assertTrue(module.getTopics().isEmpty());
    }
}