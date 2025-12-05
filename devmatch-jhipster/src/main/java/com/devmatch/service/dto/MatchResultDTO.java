package com.devmatch.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.devmatch.domain.MatchResult} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchResultDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal technicalMatch;

    private Integer aiTechnicalAffinity;

    private Integer aiMotivationalAffinity;

    private Integer aiExperienceRelevance;

    @Lob
    private String aiComment;

    @NotNull
    @Size(max = 50)
    private String createdAt;

    @NotNull
    private ProjectDTO project;

    @NotNull
    private DeveloperDTO developer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTechnicalMatch() {
        return technicalMatch;
    }

    public void setTechnicalMatch(BigDecimal technicalMatch) {
        this.technicalMatch = technicalMatch;
    }

    public Integer getAiTechnicalAffinity() {
        return aiTechnicalAffinity;
    }

    public void setAiTechnicalAffinity(Integer aiTechnicalAffinity) {
        this.aiTechnicalAffinity = aiTechnicalAffinity;
    }

    public Integer getAiMotivationalAffinity() {
        return aiMotivationalAffinity;
    }

    public void setAiMotivationalAffinity(Integer aiMotivationalAffinity) {
        this.aiMotivationalAffinity = aiMotivationalAffinity;
    }

    public Integer getAiExperienceRelevance() {
        return aiExperienceRelevance;
    }

    public void setAiExperienceRelevance(Integer aiExperienceRelevance) {
        this.aiExperienceRelevance = aiExperienceRelevance;
    }

    public String getAiComment() {
        return aiComment;
    }

    public void setAiComment(String aiComment) {
        this.aiComment = aiComment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public DeveloperDTO getDeveloper() {
        return developer;
    }

    public void setDeveloper(DeveloperDTO developer) {
        this.developer = developer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchResultDTO)) {
            return false;
        }

        MatchResultDTO matchResultDTO = (MatchResultDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchResultDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchResultDTO{" +
            "id=" + getId() +
            ", technicalMatch=" + getTechnicalMatch() +
            ", aiTechnicalAffinity=" + getAiTechnicalAffinity() +
            ", aiMotivationalAffinity=" + getAiMotivationalAffinity() +
            ", aiExperienceRelevance=" + getAiExperienceRelevance() +
            ", aiComment='" + getAiComment() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", project=" + getProject() +
            ", developer=" + getDeveloper() +
            "}";
    }
}
