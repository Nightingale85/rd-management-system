package com.epam.rd.frontend.converter;

import com.epam.rd.dto.MarkDto;
import com.epam.rd.dto.RoleDto;
import com.epam.rd.frontend.model.MarkInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MarkInfoConverter {

    public MarkInfo convert(List<MarkDto> markDtoList) {
        MarkInfo markInfo = new MarkInfo();
        markDtoList.forEach(m -> copyOf(markInfo, m));
        return markInfo;
    }

    private void copyOf(MarkInfo markInfo, MarkDto markDto) {
        RoleDto role = markDto.getEstimatorRole();
        switch (role) {
            case LECTURER:
                markInfo.setLecturerFeedback(markDto.getFeedback());
                markInfo.setLecturerMark(markDto.getMark());
                break;
            case MENTOR:
                markInfo.setMentorFeedback(markDto.getFeedback());
                markInfo.setMentorMark(markDto.getMark());
                break;
            default:
                throw new IllegalStateException(String.format("User role '%s' does not support", role));
        }
    }

}

