package com.epam.rd.backend.web.controller;

import com.epam.rd.dto.ExceptionDto;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class ExceptionHandlerControllerAdviceTest {

    private ExceptionHandlerControllerAdvice controller = new ExceptionHandlerControllerAdvice();

    private Exception e = new NotFoundException("My Exception");

    private ExceptionDto exceptionDto = new ExceptionDto(e.getMessage(), e.getClass());

    private ResponseEntity<ExceptionDto> entity = new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);

    @Test
    public void should_handle_exception() throws Exception {
        ResponseEntity<ExceptionDto> responseEntity = controller.handleException(e);
        assertEquals(responseEntity, entity);
    }

}
