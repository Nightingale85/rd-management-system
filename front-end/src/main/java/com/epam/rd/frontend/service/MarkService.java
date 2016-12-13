package com.epam.rd.frontend.service;

import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.MarkDtoCreate;
import org.springframework.security.access.annotation.Secured;

import java.util.List;


public interface MarkService {

    MarkDto getMarkById(Long markId);

    @Secured({"ROLE_MENTOR", "ROLE_LECTURER"})
    Long addMark(MarkDtoCreate dtoCreate);

    @Secured({"ROLE_MENTOR", "ROLE_LECTURER"})
    MarkDto updateMark(MarkDto markDto);

    @Secured({"ROLE_MENTOR", "ROLE_LECTURER"})
    void deleteMarkById(Long markId);

    List<MarkDto> getMarksByMenteeEmail(String email);
}
