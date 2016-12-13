package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.service.MarkService;
import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.MarkDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class MarkController {

    private MarkService service;

    @Autowired
    public MarkController(MarkService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/mark", method = POST)
    public MarkDto addMark(@RequestBody MarkDtoCreate dtoCreate) {
        return service.createMark(dtoCreate);
    }

    @RequestMapping(value = "/mark/{id}", method = GET)
    public MarkDto getMarkById(@PathVariable("id") Long id) {
        return service.getMarkById(id);
    }

    @RequestMapping(value = "/mark/{id}", method = PUT)
    public MarkDto updateMarkById(@PathVariable("id") Long id,
                                  @RequestBody MarkDto markDto) {
        markDto.setId(id);
        return service.updateMark(markDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/mark/{id}", method = DELETE)
    public void deleteMarkById(@PathVariable("id") Long id) {
        service.deleteMarkById(id);
    }

    @RequestMapping(value = "/marks/{email:.+}", method = GET)
    public ResponseEntity<List<MarkDto>> getMarksByMenteeEmail(@PathVariable("email") String email) {
        List<MarkDto> markDtoList = service.getMarksByMenteeEmail(email);
        HttpStatus status = OK;
        if (markDtoList.isEmpty()) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(markDtoList);
    }

    @RequestMapping(value = "/task/{id}/marks/{email:.+}", method = GET)
    public ResponseEntity<List<MarkDto>> getMarksByPracticalTaskIdAndEstimatorEmail(
            @PathVariable("id") Long id, @PathVariable("email") String email) {
        List<MarkDto> markDtoList = service.getMarksByPracticalTaskIdAndEstimatorEmail(id, email);
        HttpStatus status = OK;
        if (markDtoList.isEmpty()) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(markDtoList);
    }
}
