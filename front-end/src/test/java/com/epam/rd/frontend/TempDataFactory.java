package com.epam.rd.frontend;

import com.epam.rd.dto.*;
import com.epam.rd.frontend.model.MarkInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public final class TempDataFactory {
    private TempDataFactory() {
    }

    public static ProgramDto getProgramDto() {
        return getProgramDto(null);
    }

    public static ProgramDto getProgramDto(Long id) {
        return getProgramDto(id, Collections.emptyList());
    }

    public static ProgramDto getProgramDto(Long id, List<Long> modules) {
        ProgramDto dto = new ProgramDto();
        dto.setId(id);
        dto.setTitle(String.format("Program #%d", id));
        dto.setDescription("Description");
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now());
        dto.setModulesId(modules);
        return dto;
    }

    public static ModuleDto getModuleDto(Long programId) {
        return getModuleDto(null, programId);
    }

    public static ModuleDto getModuleDto(Long id, Long programId) {
        return getModuleDto(id, programId, Collections.emptyList());
    }

    public static ModuleDto getModuleDto(Long id, Long programId, List<Long> topics) {
        ModuleDto dto = new ModuleDto();
        dto.setId(id);
        dto.setTitle(String.format("Module #%d", id));
        dto.setDescription("Description");
        dto.setProgramId(programId);
        dto.setTopicsId(topics);
        return dto;
    }

    public static TopicDto getTopicDto(Long moduleId) {
        return getTopicDto(null, moduleId);
    }

    public static TopicDto getTopicDto(Long id, Long moduleId) {
        return getTopicDto(id, moduleId, null, null);
    }

    public static TopicDto getTopicDto(Long id, Long moduleId, Long lectureId, Long taskId) {
        TopicDto dto = new TopicDto();
        dto.setId(id);
        dto.setTitle(String.format("Topic #%d", id));
        dto.setDescription("Description");
        dto.setModuleId(moduleId);
        dto.setLectureId(lectureId);
        dto.setPracticalTaskId(taskId);
        return dto;
    }

    public static LectureDto getLectureDto(Long topicId) {
        return getLectureDto(null, topicId);
    }

    public static LectureDto getLectureDto(Long id, Long topicId) {
        LectureDto dto = new LectureDto();
        dto.setId(id);
        dto.setAgenda("Agenda");
        dto.setDateOfLecture(LocalDateTime.now());
        dto.setDescription("Description");
        dto.setDevice("Device");
        dto.setLinkTopicEpam("LinkTopicEpam");
        dto.setLinkVideoPortalEpam("LinkVideoPortalEpam");
        dto.setLinkYoutube("LinkYoutube");
        dto.setTitle(String.format("Lecture #%d", id));
        dto.setTypeOfPlace("TypeOfPlace");
        dto.setTopicId(topicId);
        return dto;
    }

    public static PracticalTaskDto getPracticalTaskDto(Long topicId) {
        return getPracticalTaskDto(null, topicId);
    }

    public static PracticalTaskDto getPracticalTaskDto(Long id, Long topicId) {
        PracticalTaskDto dto = new PracticalTaskDto();
        dto.setId(id);
        dto.setFunctionalRequirements("FunctionalRequirements");
        dto.setDeadline(LocalDate.now());
        dto.setDescription("Description");
        dto.setNonFunctionalRequirements("NonFunctionalRequirements");
        dto.setAdditionalMaterials("AdditionalMaterials");
        dto.setScoreSystem("ScoreSystem");
        dto.setGuide("Guide");
        dto.setTitle(String.format("Practical Task #%d", id));
        dto.setTopicId(topicId);
        return dto;
    }

    public static ProgramDtoCreate getProgramDtoCreate() {
        return getProgramDtoCreate(Collections.emptyList());
    }

    public static ProgramDtoCreate getProgramDtoCreate(List<Long> modules) {
        ProgramDtoCreate dtoCreate = new ProgramDtoCreate();
        dtoCreate.setTitle("Program");
        dtoCreate.setDescription("Description");
        dtoCreate.setStartDate(LocalDate.now());
        dtoCreate.setEndDate(LocalDate.now());
        dtoCreate.setModulesId(modules);
        return dtoCreate;
    }

    public static ModuleDtoCreate getModuleDtoCreate(Long programId) {
        return getModuleDtoCreate(programId, Collections.emptyList());
    }

    public static ModuleDtoCreate getModuleDtoCreate(Long programId, List<Long> topics) {
        ModuleDtoCreate dtoCreate = new ModuleDtoCreate();
        dtoCreate.setTitle("Module");
        dtoCreate.setDescription("Description");
        dtoCreate.setProgramId(programId);
        dtoCreate.setTopicsId(topics);
        return dtoCreate;
    }

    public static TopicDtoCreate getTopicDtoCreate(Long moduleId) {
        return getTopicDtoCreate(moduleId, null, null);
    }

    public static TopicDtoCreate getTopicDtoCreate(Long moduleId, Long lectureId, Long taskId) {
        TopicDtoCreate dto = new TopicDtoCreate();
        dto.setTitle("Topic");
        dto.setDescription("Description");
        dto.setModuleId(moduleId);
        dto.setLectureId(lectureId);
        dto.setPracticalTaskId(taskId);
        return dto;
    }

    public static LectureDtoCreate getLectureDtoCreate(Long topicId) {
        LectureDtoCreate dtoCreate = new LectureDtoCreate();
        dtoCreate.setAgenda("Agenda");
        dtoCreate.setDateOfLecture(LocalDateTime.now());
        dtoCreate.setDescription("Description");
        dtoCreate.setDevice("Device");
        dtoCreate.setLinkTopicEpam("LinkTopicEpam");
        dtoCreate.setLinkVideoPortalEpam("LinkVideoPortalEpam");
        dtoCreate.setLinkYoutube("LinkYoutube");
        dtoCreate.setTitle("Lecture");
        dtoCreate.setTypeOfPlace("TypeOfPlace");
        dtoCreate.setTopicId(topicId);
        return dtoCreate;
    }

    public static PracticalTaskDtoCreate getPracticalTaskDtoCreate(Long topicId) {
        PracticalTaskDtoCreate dto = new PracticalTaskDtoCreate();
        dto.setFunctionalRequirements("FunctionalRequirements");
        dto.setDeadline(LocalDate.now());
        dto.setDescription("Description");
        dto.setNonFunctionalRequirements("NonFunctionalRequirements");
        dto.setAdditionalMaterials("AdditionalMaterials");
        dto.setScoreSystem("ScoreSystem");
        dto.setGuide("Guide");
        dto.setTitle("Practical task");
        dto.setTopicId(topicId);
        return dto;
    }

    public static MarkDto markDto(RoleDto roleDto, String estimatorEmail) {
        MarkDto dto = new MarkDto();
        dto.setId(1L);
        dto.setPracticalTaskId(1L);
        dto.setMenteeEmail("mentee@email.com");
        dto.setEstimatorEmail(estimatorEmail);
        dto.setEstimatorRole(roleDto);
        dto.setMark(10);
        dto.setFeedback("feedback");
        return dto;
    }

    public static MarkInfo markInfo() {
        MarkInfo markInfo = new MarkInfo();
        markInfo.setPracticalTaskTitle("Practical task 1");
        markInfo.setMentorMark(10);
        markInfo.setLecturerMark(10);
        markInfo.setMentorFeedback("mentor feedback");
        markInfo.setLecturerFeedback("lecturer feedback");
        return markInfo;
    }
}
