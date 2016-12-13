package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;
import com.epam.rd.frontend.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Service
public class ProgramServiceImpl implements ProgramService {
    private RestOperations restOperations;

    @Autowired
    public ProgramServiceImpl(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public ProgramDto getProgramById(Long id) {
        return restOperations.getForObject("/program/{id}", ProgramDto.class, id);
    }

    @Override
    public List<ProgramDto> getAllPrograms() {

        ResponseEntity<List<ProgramDto>> responseEntity = restOperations
                .exchange("/programs", GET, null, new ParameterizedTypeReference<List<ProgramDto>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public Long addProgram(ProgramDtoCreate dtoCreate) {
        ResponseEntity<ProgramDto> responseEntity = restOperations
                .exchange("/program", POST, new HttpEntity<>(dtoCreate), ProgramDto.class);
        return responseEntity.getBody().getId();
    }

    @Override
    public ProgramDto updateProgram(ProgramDto programDto) {
        HttpEntity<ProgramDto> httpEntity = new HttpEntity<>(programDto);
        ResponseEntity<ProgramDto> responseEntity = restOperations
                .exchange("/program/{id}", PUT, httpEntity, ProgramDto.class, programDto.getId());
        return responseEntity.getBody();
    }
}
