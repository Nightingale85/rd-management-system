package com.epam.rd.frontend.component;

import com.epam.rd.dto.ModuleDto;
import com.epam.rd.dto.TopicDto;

import java.util.List;
import java.util.Objects;

/**
 * Created by Iurii_Kryshtop on 9/22/2016.
 */

public class ProgramInfo {
    private String programTitle;
    private ModuleDto currentModule;
    private List<ModuleDto> modules;
    private List<TopicDto> topics;

    public ProgramInfo() {
    }

    public ProgramInfo(String programTitle, List<ModuleDto> modules, ModuleDto currentModule, List<TopicDto> topics) {
        this.programTitle = programTitle;
        this.currentModule = currentModule;
        this.modules = modules;
        this.topics = topics;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public ModuleDto getCurrentModule() {
        return currentModule;
    }

    public void setCurrentModule(ModuleDto currentModule) {
        this.currentModule = currentModule;
    }

    public List<ModuleDto> getModules() {
        return modules;
    }

    public void setModules(List<ModuleDto> modules) {
        this.modules = modules;
    }

    public List<TopicDto> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDto> topics) {
        this.topics = topics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramInfo)) {
            return false;
        }

        ProgramInfo programInfo = (ProgramInfo) o;

        return Objects.equals(programTitle, programInfo.getProgramTitle()) &&
                Objects.equals(currentModule, programInfo.getCurrentModule()) &&
                Objects.equals(modules, programInfo.getModules()) &&
                Objects.equals(topics, programInfo.getTopics());
    }

    @Override
    public int hashCode() {
        return Objects.hash(programTitle, currentModule, modules, topics);
    }
}
