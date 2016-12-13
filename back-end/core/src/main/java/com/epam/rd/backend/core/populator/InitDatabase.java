package com.epam.rd.backend.core.populator;

import com.epam.rd.backend.core.model.*;
import com.epam.rd.backend.core.repository.ProgramRepository;
import com.epam.rd.backend.core.service.UserService;
import com.epam.rd.dto.RoleDto;
import com.epam.rd.dto.UserDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class InitDatabase {
    private static final int YEAR = 2015;
    private static final int DAY = 10;
    private ProgramRepository programRepository;
    private UserService userService;

    @Autowired
    public InitDatabase(ProgramRepository programRepository, UserService userService) {
        this.programRepository = programRepository;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        insertUsers();
        List<Topic> topics = new ArrayList<>();
        List<Module> modules = new ArrayList<>();
        topics.add(topic("GIT", lecture("Git basic"), task("Homework #1")));
        topics.add(topic("CI", lecture("Jenkins CI and CD"), task("Homework #2")));
        topics.add(topic("Maven", lecture("Maven + JUnit"), task("Homework #3")));
        topics.add(topic("Other Build Tools", lecture("Ant and Gradle"), task("Homework #4")));
        modules.add(module("Module 1", topics));

        topics = new ArrayList<>();

        topics.add(topic("JDP Part#1", lecture("Java Design Patterns. Part #1"), task("Homework #5")));
        topics.add(topic("JDP Part#2", lecture("Java Design Patterns. Part #2"), task("Homework #6")));
        topics.add(topic("Java Core", lecture("Collection + JDBC + SQL"), task("Homework #7")));
        topics.add(topic("Multithreading", lecture("Concurrent + Multithreading"), task("Homework #8")));
        topics.add(topic("NIO + Regex + Serializable", lecture("NIO + Regex + Serializable"), task("Homework #9")));
        modules.add(module("Module 2", topics));

        programRepository.save(program("RD Java Dnepr", modules));

    }

    private void insertUsers() {
        UserDtoCreate admin = new UserDtoCreate();
        admin.setName("Admin");
        admin.setEmail("admin@mail.com");
        admin.setPassword("$2a$10$tHN12DhKhH7zSKrFqrxMReZTOQz1rX8AcpDua4WNgBGMa3Sd8mi2K");
        admin.setRoles(Collections.singleton(RoleDto.MANAGER));
        userService.addUser(admin);

        UserDtoCreate lecturer = new UserDtoCreate();
        lecturer.setName("Lecturer");
        lecturer.setEmail("lecturer@mail.com");
        lecturer.setPassword("$2a$04$f4xefVMRHPtC8dj391Z/jeK3R/ItcnHFG/DOeADZdIzbahm2fK8t6");
        lecturer.setRoles(Collections.singleton(RoleDto.LECTURER));
        userService.addUser(lecturer);

        UserDtoCreate mentee = new UserDtoCreate();
        mentee.setName("Mentee");
        mentee.setEmail("mentee@mail.com");
        mentee.setPassword("$2a$04$2xkwbN4Cqb8sPemxYevO1uBf1qJ8uSixJOEU3IJwwINgoGL/rKgQK");
        mentee.setRoles(Collections.singleton(RoleDto.MENTEE));
        userService.addUser(mentee);
    }

    private Topic topic(final String name, final Lecture lecture, final PracticalTask task) {
        final Topic topic = new Topic();
        topic.setName(name);
        topic.setLinkToDescription("Link To Description Of Topic");
        topic.setLecture(lecture);
        topic.setPracticalTask(task);
        return topic;
    }

    private Lecture lecture(final String name) {
        final Lecture lecture = new Lecture();
        lecture.setName(name);
        lecture.setDateStart(LocalDateTime.now());
        lecture.setLinkTopicEpam("Link To Topic Epam");
        lecture.setLinkYoutube("Link youtube");
        lecture.setLinkVideoPortalEpam("Link video portal epam");
        lecture.setAgenda("Agenda");
        lecture.setDescription("Description Lecture");
        lecture.setTypeOfPlace("Meeting Room");
        return lecture;
    }

    private PracticalTask task(final String name) {
        final PracticalTask practicalTask = new PracticalTask();
        practicalTask.setName(name);
        practicalTask.setDateEnd(LocalDate.now());
        practicalTask.setLinkToDescription("Link To Description Of Topic");
        practicalTask.setFunctionalRequirements("Functional Requirements");
        practicalTask.setNonFunctionalRequirements("Non Functional Requirements");
        practicalTask.setScoreSystem("Score system");
        practicalTask.setAdditionalMaterials("Additional materials");
        practicalTask.setGuide("Guide gor Practical Task");
        return practicalTask;
    }

    private Module module(final String name, final List<Topic> topics) {
        final Module module = new Module();
        module.setName(name);
        module.setLinkToDescription("Link to Description");
        module.setTopics(topics);
        return module;
    }

    private Program program(final String name, final List<Module> modules) {
        final Program program = new Program();
        program.setName(name);
        program.setLinkToDescription("Link to description");
        program.setDateStart(LocalDate.of(YEAR, Month.AUGUST, DAY));
        program.setDateEnd(LocalDate.of(YEAR, Month.NOVEMBER, DAY));
        for (final Module module : modules) {
            program.addModule(module);
        }
        return program;
    }
}
