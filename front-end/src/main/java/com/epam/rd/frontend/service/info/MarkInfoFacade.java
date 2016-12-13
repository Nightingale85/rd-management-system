package com.epam.rd.frontend.service.info;

import com.epam.rd.dto.MarkDto;
import com.epam.rd.frontend.converter.MarkInfoConverter;
import com.epam.rd.frontend.model.MarkInfo;
import com.epam.rd.frontend.service.MarkService;
import com.epam.rd.frontend.service.PracticalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Component
public class MarkInfoFacade {
    private MarkService markService;
    private PracticalTaskService practicalTaskService;
    private MarkInfoConverter markInfoConverter;

    @Autowired
    public MarkInfoFacade(MarkService markService,
                          PracticalTaskService practicalTaskService,
                          MarkInfoConverter markInfoConverter) {
        this.markService = markService;
        this.practicalTaskService = practicalTaskService;
        this.markInfoConverter = markInfoConverter;
    }

    public List<MarkInfo> getMarkInfoListByMentee(String email) {
        Map<Long, List<MarkDto>> markDtoGroupingByTaskId = markService.getMarksByMenteeEmail(email)
                                                                      .stream()
                                                                      .collect(groupingBy(MarkDto::getPracticalTaskId,
                                                                              HashMap::new,
                                                                              mapping(identity(), toList())));

        return markDtoGroupingByTaskId.entrySet()
                                      .stream()
                                      .map(Function.identity())
                                      .map(this::getMarkInfo)
                                      .collect(toList());
    }

    private MarkInfo getMarkInfo(Map.Entry<Long, List<MarkDto>> entry) {
        MarkInfo markInfo = markInfoConverter.convert(entry.getValue());
        markInfo.setPracticalTaskTitle(practicalTaskService.getPracticalTaskDtoById(entry.getKey()).getTitle());
        return markInfo;
    }

}
