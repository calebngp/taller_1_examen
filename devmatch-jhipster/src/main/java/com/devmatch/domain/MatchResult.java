package com.devmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A MatchResult.
 */
@Entity
@Table(name = "match_result")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "technical_match", precision = 21, scale = 2, nullable = false)
    private BigDecimal technicalMatch;

    @Column(name = "ai_technical_affinity")
    private Integer aiTechnicalAffinity;

    @Column(name = "ai_motivational_affinity")
    private Integer aiMotivationalAffinity;

    @Column(name = "ai_experience_relevance")
    private Integer aiExperienceRelevance;

    @Lob
    @Column(name = "ai_comment")
    private String aiComment;

    @NotNull
    @Size(max = 50)
    @Column(name = "created_at", length = 50, nullable = false)
    private String createdAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "requiredTechnologies" }, allowSetters = true)
    private Project project;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "experiences", "skills" }, allowSetters = true)
    private Developer developer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MatchResult id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTechnicalMatch() {
        return this.technicalMatch;
    }

    public MatchResult technicalMatch(BigDecimal technicalMatch) {
        this.setTechnicalMatch(technicalMatch);
        return this;
    }

    public void setTechnicalMatch(BigDecimal technicalMatch) {
        this.technicalMatch = technicalMatch;
    }

    public Integer getAiTechnicalAffinity() {
        return this.aiTechnicalAffinity;
    }

    public MatchResult aiTechnicalAffinity(Integer aiTechnicalAffinity) {
        this.setAiTechnicalAffinity(aiTechnicalAffinity);
        return this;
    }

    public void setAiTechnicalAffinity(Integer aiTechnicalAffinity) {
        this.aiTechnicalAffinity = aiTechnicalAffinity;
    }

    public Integer getAiMotivationalAffinity() {
        return this.aiMotivationalAffinity;
    }

    public MatchResult aiMotivationalAffinity(Integer aiMotivationalAffinity) {
        this.setAiMotivationalAffinity(aiMotivationalAffinity);
        return this;
    }

    public void setAiMotivationalAffinity(Integer aiMotivationalAffinity) {
        this.aiMotivationalAffinity = aiMotivationalAffinity;
    }

    public Integer getAiExperienceRelevance() {
        return this.aiExperienceRelevance;
    }

    public MatchResult aiExperienceRelevance(Integer aiExperienceRelevance) {
        this.setAiExperienceRelevance(aiExperienceRelevance);
        return this;
    }

    public void setAiExperienceRelevance(Integer aiExperienceRelevance) {
        this.aiExperienceRelevance = aiExperienceRelevance;
    }

    public String getAiComment() {
        return this.aiComment;
    }

    public MatchResult aiComment(String aiComment) {
        this.setAiComment(aiComment);
        return this;
    }

    public void setAiComment(String aiComment) {
        this.aiComment = aiComment;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public MatchResult createdAt(String createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public MatchResult project(Project project) {
        this.setProject(project);
        return this;
    }

    public Developer getDeveloper() {
        return this.developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public MatchResult developer(Developer developer) {
        this.setDeveloper(developer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchResult)) {
            return false;
        }
        return getId() != null && getId().equals(((MatchResult) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchResult{" +
            "id=" + getId() +
            ", technicalMatch=" + getTechnicalMatch() +
            ", aiTechnicalAffinity=" + getAiTechnicalAffinity() +
            ", aiMotivationalAffinity=" + getAiMotivationalAffinity() +
            ", aiExperienceRelevance=" + getAiExperienceRelevance() +
            ", aiComment='" + getAiComment() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
