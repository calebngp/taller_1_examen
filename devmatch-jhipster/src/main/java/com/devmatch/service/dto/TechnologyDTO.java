package com.devmatch.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.devmatch.domain.Technology} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TechnologyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 50)
    private String category;

    private Set<DeveloperDTO> developers = new HashSet<>();

    private Set<ProjectDTO> projects = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<DeveloperDTO> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<DeveloperDTO> developers) {
        this.developers = developers;
    }

    public Set<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectDTO> projects) {
        this.projects = projects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TechnologyDTO)) {
            return false;
        }

        TechnologyDTO technologyDTO = (TechnologyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, technologyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TechnologyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", developers=" + getDevelopers() +
            ", projects=" + getProjects() +
            "}";
    }
}
