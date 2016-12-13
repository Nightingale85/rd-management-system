package com.epam.rd.backend.core.converter;

import com.epam.rd.backend.core.model.Mark;
import com.epam.rd.backend.core.model.UserRole;
import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.MarkDtoCreate;
import com.epam.rd.dto.RoleDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MarkDtoConverter extends AbstractConverter<Mark, MarkDto> {

    public MarkDto convert(Mark mark) {
        if (Objects.isNull(mark)) {
            throw new IllegalArgumentException("Can't convert mark to markDto, mark is  null");
        }
        if (Objects.isNull(mark.getEstimatorRole())) {
            throw new IllegalArgumentException("Can't convert mark to markDto, estimatorRole is  null");
        }
        MarkDto dto = new MarkDto();
        dto.setId(mark.getId());
        dto.setPracticalTaskId(mark.getPracticalTaskId());
        dto.setMenteeEmail(mark.getMenteeEmail());
        dto.setEstimatorEmail(mark.getEstimatorEmail());
        dto.setEstimatorRole(RoleDto.valueOf(mark.getEstimatorRole().name()));
        dto.setMark(mark.getMark());
        dto.setFeedback(mark.getFeedback());
        return dto;
    }

    public Mark convert(MarkDto markDto) {
        if (Objects.isNull(markDto)) {
            throw new IllegalArgumentException("Can't convert markDto to mark, markDto is  null");
        }
        if (Objects.isNull(markDto.getEstimatorRole())) {
            throw new IllegalArgumentException("Can't convert markDto to mark, estimatorRole is  null");
        }
        if (Objects.isNull(markDto.getPracticalTaskId())) {
            throw new IllegalArgumentException("Can't convert markDto to mark, practicalTaskId is  null");
        }
        Mark mark = new Mark();
        mark.setId(markDto.getId());
        mark.setPracticalTaskId(markDto.getPracticalTaskId());
        mark.setMenteeEmail(markDto.getMenteeEmail());
        mark.setEstimatorEmail(markDto.getEstimatorEmail());
        mark.setEstimatorRole(UserRole.valueOf(markDto.getEstimatorRole().name()));
        mark.setMark(markDto.getMark());
        mark.setFeedback(markDto.getFeedback());
        return mark;
    }

    public Mark convert(MarkDtoCreate markDtoCreate) {
        if (Objects.isNull(markDtoCreate)) {
            throw new IllegalArgumentException("Can't convert markDtoCreate to mark, markDtoCreate is  null");
        }
        if (Objects.isNull(markDtoCreate.getEstimatorRole())) {
            throw new IllegalArgumentException("Can't convert markDtoCreate to mark, estimatorRole is  null");
        }
        Mark mark = new Mark();
        mark.setPracticalTaskId(markDtoCreate.getPracticalTaskId());
        mark.setMenteeEmail(markDtoCreate.getMenteeEmail());
        mark.setEstimatorEmail(markDtoCreate.getEstimatorEmail());
        mark.setEstimatorRole(UserRole.valueOf(markDtoCreate.getEstimatorRole().name()));
        mark.setMark(markDtoCreate.getMark());
        mark.setFeedback(markDtoCreate.getFeedback());
        return mark;
    }

}
