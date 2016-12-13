package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.ModuleDtoCreate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ModuleDtoConverter extends AbstractConverter<Module, ModuleDto> {

    public ModuleDto convert(Module module) {
        ModuleDto dto = new ModuleDto();
        dto.setId(module.getId());
        dto.setTitle(module.getName());
        dto.setDescription(module.getLinkToDescription());
        dto.setProgramId(getProgramId(module));
        List<Long> topicsId = module.getTopics()
                                    .stream()
                                    .map(Topic::getId)
                                    .collect(Collectors.toList());
        dto.setTopicsId(topicsId);
        return dto;
    }

    private Long getProgramId(Module module) {
        return module.getProgram() != null ? module.getProgram().getId() : null;
    }

    public Module convert(ModuleDtoCreate dto) {
        Objects.requireNonNull(dto, "Module DTO cannot be NULL");
        Module module = new Module();
        module.setName(dto.getTitle());
        module.setLinkToDescription(dto.getDescription());
        return module;
    }

    public Module convert(ModuleDto dto) {
        Objects.requireNonNull(dto, "Module DTO cannot be NULL");
        Module module = new Module();
        module.setId(dto.getId());
        module.setName(dto.getTitle());
        module.setLinkToDescription(dto.getDescription());
        return module;
    }
}
