package com.epam.rd.backend.web.controller;


import com.epam.rd.backend.core.service.ProgramService;
import com.epam.rd.dto.ProgramDto;
import com.epam.rd.dto.ProgramDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.epam.rd.HeaderConstant.HEADER_MODEL_ID;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProgramController {
    private ProgramService service;

    @Autowired
    public ProgramController(ProgramService service) {
        this.service = service;
    }

    @RequestMapping(value = "/programs", method = GET)
    public ResponseEntity<List<ProgramDto>> getAllProgram() {
        List<ProgramDto> programDtoList = service.getAllProgram();
        HttpStatus status = OK;
        if (programDtoList.isEmpty()) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(programDtoList);
    }

    @RequestMapping(value = "/program/{id}", method = GET)
    public ResponseEntity<ProgramDto> getProgramById(@PathVariable("id") Long id) {
        ProgramDto dto = service.getProgramById(id);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/program", method = POST)
    public ResponseEntity<ProgramDto> addProgram(@RequestBody ProgramDtoCreate dtoCreate,
                                                 UriComponentsBuilder builder) {
        ProgramDto dto = service.createProgram(dtoCreate);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/program/{id}").buildAndExpand(dto.getId()).toUri());
        headers.add(HEADER_MODEL_ID, dto.getId().toString());
        return ResponseEntity.status(CREATED).headers(headers).body(dto);
    }

    @RequestMapping(value = "/program/{id}", method = PUT)
    public ResponseEntity<ProgramDto> updateProgramById(@PathVariable("id") Long id,
                                                        @RequestBody ProgramDto programDto) {
        programDto.setId(id);
        ProgramDto updateProgram = service.updateProgram(programDto);
        return ResponseEntity.ok(updateProgram);
    }

    @RequestMapping(value = "/program/{id}",
            method = DELETE)
    public ResponseEntity<Void> deleteProgramById(@PathVariable("id") Long id) {
        service.deleteProgramById(id);
        return ResponseEntity.noContent().build();
    }
}
