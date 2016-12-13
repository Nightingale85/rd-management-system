package com.epam.rd.backend.core.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "lecture")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_start")
    private LocalDateTime dateStart;
    @Column(name = "type_of_place")
    private String typeOfPlace;
    @Column(name = "device")
    private String device;
    @Column(name = "link_topic_epam")
    private String linkTopicEpam;
    @Column(name = "link_youtube")
    private String linkYoutube;
    @Column(name = "link_video_portal_epam")
    private String linkVideoPortalEpam;
    @Column(name = "agenda")
    private String agenda;
    @Column(name = "description")
    private String description;
    @OneToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public Lecture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public String getTypeOfPlace() {
        return typeOfPlace;
    }

    public void setTypeOfPlace(String typeOfPlace) {
        this.typeOfPlace = typeOfPlace;
    }

    public String getDevice() {
        return device;
    }

    public void setDeviceList(String device) {
        this.device = device;
    }

    public String getLinkTopicEpam() {
        return linkTopicEpam;
    }

    public void setLinkTopicEpam(String linkTopicEpam) {
        this.linkTopicEpam = linkTopicEpam;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public String getLinkVideoPortalEpam() {
        return linkVideoPortalEpam;
    }

    public void setLinkVideoPortalEpam(String linkVideoPortalEpam) {
        this.linkVideoPortalEpam = linkVideoPortalEpam;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lecture lecture = (Lecture) o;
        return Objects.equals(id, lecture.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
