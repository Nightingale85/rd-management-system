package com.epam.rd.backend.core.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "practical_task")
public class PracticalTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_end")
    private LocalDate dateEnd;
    @Column(name = "link_to_description")
    private String linkToDescription;
    @Column(name = "functional_requirements")
    private String functionalRequirements;
    @Column(name = "non_functional_requirements")
    private String nonFunctionalRequirements;
    @Column(name = "score_system")
    private String scoreSystem;
    @Column(name = "additional_materials")
    private String additionalMaterials;
    @Column(name = "guide")
    private String guide;
    @OneToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public PracticalTask() {
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

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getLinkToDescription() {
        return linkToDescription;
    }

    public void setLinkToDescription(String linkToDescription) {
        this.linkToDescription = linkToDescription;
    }

    public String getFunctionalRequirements() {
        return functionalRequirements;
    }

    public void setFunctionalRequirements(String functionalRequirements) {
        this.functionalRequirements = functionalRequirements;
    }

    public String getNonFunctionalRequirements() {
        return nonFunctionalRequirements;
    }

    public void setNonFunctionalRequirements(String nonFunctionalRequirements) {
        this.nonFunctionalRequirements = nonFunctionalRequirements;
    }

    public String getScoreSystem() {
        return scoreSystem;
    }

    public void setScoreSystem(String scoreSystem) {
        this.scoreSystem = scoreSystem;
    }

    public String getAdditionalMaterials() {
        return additionalMaterials;
    }

    public void setAdditionalMaterials(String additionalMaterials) {
        this.additionalMaterials = additionalMaterials;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PracticalTask practicalTask = (PracticalTask) o;
        return Objects.equals(id, practicalTask.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
