package com.epam.rd.frontend.controller.error;

import com.epam.rd.dto.ExceptionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Autowired
    private ObjectMapper mapper;

    @ExceptionHandler(HttpStatusCodeException.class)
    public ModelAndView handleHttpServerError(HttpStatusCodeException exception) {
        MediaType contentType = exception.getResponseHeaders().getContentType();
        LOGGER.error(exception.getMessage(), exception);
        ModelAndView mav = new ModelAndView();
        if (contentType.equals(MediaType.APPLICATION_JSON_UTF8)) {
            setModelAttribute(exception, mav);
        } else {
            mav.addObject("exception", "Exception");
        }
        mav.setViewName("error/error");
        return mav;
    }

    private void setModelAttribute(HttpStatusCodeException exception, ModelAndView mav) {
        try {
            ExceptionDto exceptionDto = mapper.readValue(exception.getResponseBodyAsString(), ExceptionDto.class);
            mav.addObject("exception", exceptionDto.getClassException().getSimpleName());
            mav.addObject("message", exceptionDto.getReason());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            mav.addObject("message", "Cannot parse exception thrown during processing request");
        }
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleConflict(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception.getClass().getSimpleName());
        mav.addObject("message", exception.getMessage());
        mav.setViewName("error/error");
        return mav;
    }
}
