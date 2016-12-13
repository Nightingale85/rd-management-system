package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.converter.MarkDtoConverter;
import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.model.Mark;
import com.epam.rd.backend.core.repository.MarkRepository;
import com.epam.rd.backend.core.service.MarkService;
import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.MarkDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Service
@Transactional
public class MarkServiceImpl implements MarkService {
    private MarkRepository markRepository;
    private MarkDtoConverter converter;

    @Autowired
    public MarkServiceImpl(MarkRepository markRepository, MarkDtoConverter converter) {
        this.markRepository = markRepository;
        this.converter = converter;
    }

    @Override
    public MarkDto createMark(MarkDtoCreate dtoCreate) {
        Mark mark = converter.convert(dtoCreate);
        return converter.convert(markRepository.save(mark));
    }

    @Override
    public MarkDto updateMark(MarkDto dto) {
        Mark mark = converter.convert(dto);
        if (!isMarkExist(mark.getId())) {
            throw new EntityDoesNotExistException(format("Mark with id = %s does not exist", mark.getId()));
        }
        return converter.convert(markRepository.save(mark));
    }

    @Override
    public MarkDto getMarkById(Long id) {
        Mark mark = markRepository.findOne(id);
        if (isNull(mark)) {
            throw new EntityDoesNotExistException(format("Cannot find mark by id = %s", id));
        }
        return converter.convert(mark);
    }

    @Override
    public void deleteMarkById(Long id) {
        if (!isMarkExist(id)) {
            throw new EntityDoesNotExistException(format("Cannot find mark by id = %s", id));
        }
        markRepository.delete(id);
    }

    @Override
    public List<MarkDto> getMarksByMenteeEmail(String email) {
        return converter.convertList(markRepository.findMarksByMenteeEmail(email));
    }

    @Override
    public List<MarkDto> getMarksByPracticalTaskIdAndEstimatorEmail(Long practicalTaskId, String estimatorEmail) {
        return converter.convertList(markRepository
                .findMarksByPracticalTaskIdAndEstimatorEmail(practicalTaskId, estimatorEmail));
    }

    private boolean isMarkExist(Long id) {
        return !isNull(markRepository.findOne(id));
    }
}
