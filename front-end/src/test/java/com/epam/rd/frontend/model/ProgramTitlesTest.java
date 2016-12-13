package com.epam.rd.frontend.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class ProgramTitlesTest {
    private Map<Long, String> programInfo;

    @Before
    public void set_up() throws Exception {
        programInfo = new HashMap<>();
        programInfo.put(1L, "Test");
    }

    @Test(expected = NullPointerException.class)
    public void should_throw_null_pointer_exception_if_you_create_programs_info_with_null() {
        //WHEN
        new ProgramTitles(null);
    }

    @Test
    public void should_return_programs_info() {
        //GIVEN
        Map<Long, String> expectedProgramsInfo;
        ProgramTitles actualPrograms = new ProgramTitles(programInfo);

        //WHEN
        expectedProgramsInfo = actualPrograms.getTitles();

        //THEN
        assertEquals(expectedProgramsInfo, programInfo);
    }
}