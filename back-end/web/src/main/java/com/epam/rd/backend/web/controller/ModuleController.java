package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.service.ModuleService;
import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ModuleDtoCreate;
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
public class ModuleController {
    private ModuleService service;

    @Autowired
    public ModuleController(ModuleService service) {
        this.service = service;
    }

    @RequestMapping(value = "/modules", method = GET)
    public ResponseEntity<List<ModuleDto>> getAllModule() {
        List<ModuleDto> moduleDtoList = service.getAllModule();
        HttpStatus status = OK;
        if (moduleDtoList.isEmpty()) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(moduleDtoList);
    }

    @RequestMapping(value = "/program/{programId}/modules", method = GET)
    public ResponseEntity<List<ModuleDto>> getModulesByProgramId(@PathVariable("programId") Long programId) {
        List<ModuleDto> moduleDtoList = service.getModulesByProgramId(programId);
        HttpStatus status = OK;
        if (moduleDtoList.isEmpty()) {
            status = NO_CONTENT;
        }
        return ResponseEntity.status(status).body(moduleDtoList);
    }

    @RequestMapping(value = "/module/{id}", method = GET)
    public ResponseEntity<ModuleDto> getModuleById(@PathVariable("id") Long id) {
        ModuleDto dto = service.getModuleById(id);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(value = "/module", method = POST)
    public ResponseEntity<ModuleDto> addModule(@RequestBody ModuleDtoCreate dtoCreate,
                                               UriComponentsBuilder builder) {
        ModuleDto dto = service.createModule(dtoCreate);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/api/module/{id}").buildAndExpand(dto.getId()).toUri());
        headers.add(HEADER_MODEL_ID, dto.getId().toString());
        return ResponseEntity.status(CREATED).headers(headers).body(dto);
    }

    @RequestMapping(value = "/module/{id}", method = PUT)
    public ResponseEntity<ModuleDto> updateModuleById(@PathVariable("id") Long id, @RequestBody ModuleDto moduleDto) {
        moduleDto.setId(id);
        ModuleDto dtoUpdate = service.updateModule(moduleDto);
        return ResponseEntity.ok(dtoUpdate);
    }

    @RequestMapping(value = "/module/{id}", method = DELETE)
    public ResponseEntity<Void> deleteModuleById(@PathVariable("id") Long id) {
        service.deleteModuleById(id);
        return ResponseEntity.noContent().build();
    }
}
