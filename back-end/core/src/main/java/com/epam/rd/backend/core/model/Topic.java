package com.epam.rd.backend.core.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "link_to_description")
    private String linkToDescription;
    @ManyToOne()
    @JoinColumn(name = "module_id")
    private Module module;
    @OneToOne(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Lecture lecture;
    @OneToOne(mappedBy = "topic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PracticalTask practicalTask;

    public Topic() {
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
        lecture.setTopic(this);
    }

    public PracticalTask getPracticalTask() {
        return practicalTask;

    }

    public void setPracticalTask(PracticalTask practicalTask) {
        this.practicalTask = practicalTask;
        practicalTask.setTopic(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkToDescription() {
        return linkToDescription;
    }

    public void setLinkToDescription(String linkToDescription) {
        this.linkToDescription = linkToDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Topic topic = (Topic) o;
        return Objects.equals(id, topic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
