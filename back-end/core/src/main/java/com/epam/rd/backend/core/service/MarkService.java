package com.epam.rd.backend.core.service;

import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.MarkDtoCreate;

import java.util.List;

public interface MarkService {

    MarkDto createMark(MarkDtoCreate dtoCreate);

    MarkDto updateMark(MarkDto dto);

    MarkDto getMarkById(Long id);

    void deleteMarkById(Long id);

    List<MarkDto> getMarksByMenteeEmail(String email);

    List<MarkDto> getMarksByPracticalTaskIdAndEstimatorEmail(Long practicalTaskId, String estimatorEmail);
}
