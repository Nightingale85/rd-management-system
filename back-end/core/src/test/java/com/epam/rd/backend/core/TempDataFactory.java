package com.epam.rd.backend.core;

import com.epam.rd.backend.core.model.*;
import com.epam.rd.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class TempDataFactory {
    private TempDataFactory() {
    }

    public static Lecture getLecture() {
        return getLecture(null);
    }

    public static Lecture getLecture(Long id) {
        Lecture lecture = new Lecture();
        lecture.setId(id);
        lecture.setAgenda("Agenda");
        lecture.setDateStart(LocalDateTime.now());
        lecture.setDescription("Description");
        lecture.setDeviceList("Device");
        lecture.setLinkTopicEpam("LinkTopicEpam");
        lecture.setLinkVideoPortalEpam("LinkVideoPortalEpam");
        lecture.setLinkYoutube("LinkYoutube");
        lecture.setName("Name");
        lecture.setTypeOfPlace("TypeOfPlace");
        return lecture;
    }

    public static PracticalTask getPracticalTask() {
        return getPracticalTask(null);
    }

    public static PracticalTask getPracticalTask(Long id) {
        PracticalTask task = new PracticalTask();
        task.setId(id);
        task.setFunctionalRequirements("FunctionalRequirements");
        task.setDateEnd(LocalDate.now());
        task.setLinkToDescription("Description");
        task.setNonFunctionalRequirements("NonFunctionalRequirements");
        task.setAdditionalMaterials("AdditionalMaterials");
        task.setScoreSystem("ScoreSystem");
        task.setGuide("Guide");
        task.setName("Name");
        return task;
    }

    public static Topic getTopic() {
        return getTopic(null, null, null);
    }

    public static Topic getTopic(Long id) {
        return getTopic(id, null, null);
    }

    public static Topic getTopic(PracticalTask task) {
        return getTopic(null, null, task);
    }

    public static Topic getTopic(Lecture lecture) {
        return getTopic(null, lecture, null);
    }

    public static Topic getTopic(Long id, PracticalTask task) {
        return getTopic(id, null, task);
    }

    public static Topic getTopic(Long id, Lecture lecture) {
        return getTopic(id, lecture, null);
    }

    public static Topic getTopic(Long id, Lecture lecture, PracticalTask task) {
        Topic topic = new Topic();
        topic.setId(id);
        topic.setName("Name");
        topic.setLinkToDescription("Description");
        if (lecture != null) {
            topic.setLecture(lecture);
        }
        if (task != null) {
            topic.setPracticalTask(task);
        }
        return topic;
    }

    public static Module getModule() {
        return getModule(null, Collections.emptyList());
    }

    public static Module getModule(Long id) {
        return getModule(id, Collections.emptyList());
    }

    public static Module getModule(List<Topic> topics) {
        return getModule(null, topics);
    }

    public static Module getModule(Long id, List<Topic> topics) {
        Module module = new Module();
        module.setId(id);
        module.setName("Name");
        module.setLinkToDescription("Description");
        module.setTopics(topics);
        return module;
    }

    public static Program getProgram() {
        return getProgram(null, Collections.emptyList());
    }

    public static Program getProgram(Long id) {
        return getProgram(id, Collections.emptyList());
    }

    public static Program getProgram(List<Module> modules) {
        return getProgram(null, modules);
    }

    public static Program getProgram(Long id, List<Module> modules) {
        Program program = new Program();
        program.setId(id);
        program.setName("Name");
        program.setDateStart(LocalDate.now());
        program.setDateEnd(LocalDate.now());
        program.setLinkToDescription("Description");
        modules.forEach(program::addModule);
        return program;
    }

    public static User getUser(String email) {
        return getUser(email, Collections.emptySet());
    }

    public static User getUser(String email, Set<UserRole> roles) {
        User user = new User();
        user.setEmail(email);
        user.setName("Name");
        user.setPassword("password");
        user.setRoles(roles);
        return user;
    }

    public static Mark getMark() {
        Mark mark = new Mark();
        mark.setId(1L);
        mark.setPracticalTaskId(1L);
        mark.setMenteeEmail("mentee@email.com");
        mark.setEstimatorEmail("mentor@email.com");
        mark.setEstimatorRole(UserRole.valueOf("MENTOR"));
        mark.setMark(10);
        mark.setFeedback("OK");
        return mark;
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
        dto.setTitle("Title");
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
        dto.setTitle("Title");
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
        dto.setTitle("Title");
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
        dto.setTitle("Title");
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
        dto.setTitle("Title");
        dto.setTopicId(topicId);
        return dto;
    }

    public static MarkDto getMarkDto() {
        MarkDto dto = new MarkDto();
        dto.setId(1L);
        dto.setPracticalTaskId(1L);
        dto.setMenteeEmail("mentee@email.com");
        dto.setEstimatorEmail("mentor@email.com");
        dto.setEstimatorRole(RoleDto.valueOf("MENTOR"));
        dto.setMark(10);
        dto.setFeedback("OK");
        return dto;
    }

    public static ProgramDtoCreate getProgramDtoCreate() {
        return getProgramDtoCreate(Collections.emptyList());
    }

    public static ProgramDtoCreate getProgramDtoCreate(List<Long> modules) {
        ProgramDtoCreate dtoCreate = new ProgramDtoCreate();
        dtoCreate.setTitle("Title");
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
        dtoCreate.setTitle("Title");
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
        dto.setTitle("Title");
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
        dtoCreate.setTitle("Title");
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
        dto.setTitle("Title");
        dto.setTopicId(topicId);
        return dto;
    }

    public static MarkDtoCreate getMarkDtoCreate() {
        MarkDtoCreate dto = new MarkDtoCreate();
        dto.setPracticalTaskId(1L);
        dto.setMenteeEmail("mentee@email.com");
        dto.setEstimatorEmail("mentor@email.com");
        dto.setEstimatorRole(RoleDto.valueOf("MENTOR"));
        dto.setMark(10);
        dto.setFeedback("OK");
        return dto;
    }
}
