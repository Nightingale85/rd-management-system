package com.epam.rd.frontend.service.impl;

import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.MarkDtoCreate;
import com.epam.rd.frontend.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@Service
public class MarkServiceImpl implements MarkService {

    private RestOperations restOperations;

    @Autowired
    public MarkServiceImpl(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public MarkDto getMarkById(Long markId) {
        return restOperations.getForObject("/mark/{id}", MarkDto.class, markId);
    }

    @Override
    public Long addMark(MarkDtoCreate dtoCreate) {
        ResponseEntity<MarkDto> responseEntity = restOperations
                .exchange("/mark", POST, new HttpEntity<>(dtoCreate), MarkDto.class);
        return responseEntity.getBody().getId();
    }

    @Override
    public MarkDto updateMark(MarkDto markDto) {
        HttpEntity<MarkDto> httpEntity = new HttpEntity<>(markDto);
        ResponseEntity<MarkDto> responseEntity = restOperations
                .exchange("/mark/{id}", PUT, httpEntity, MarkDto.class, markDto.getId());
        return responseEntity.getBody();
    }

    @Override
    public void deleteMarkById(Long markId) {
        restOperations.delete("/mark/{id}", markId);
    }

    @Override
    public List<MarkDto> getMarksByMenteeEmail(String email) {
        List<MarkDto>  markDtoList = restOperations.exchange(
                "/marks/{email}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<MarkDto>>() {
                },
                email)
                .getBody();

        return markDtoList == null ? Collections.emptyList() : markDtoList;
    }
}
