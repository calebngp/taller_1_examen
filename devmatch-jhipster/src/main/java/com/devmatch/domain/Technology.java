package com.devmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Technology.
 */
@Entity
@Table(name = "technology")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Technology implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Size(max = 50)
    @Column(name = "category", length = 50)
    private String category;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "skills")
    @JsonIgnoreProperties(value = { "experiences", "skills" }, allowSetters = true)
    private Set<Developer> developers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "requiredTechnologies")
    @JsonIgnoreProperties(value = { "requiredTechnologies" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Technology id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Technology name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public Technology category(String category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Developer> getDevelopers() {
        return this.developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        if (this.developers != null) {
            this.developers.forEach(i -> i.removeSkills(this));
        }
        if (developers != null) {
            developers.forEach(i -> i.addSkills(this));
        }
        this.developers = developers;
    }

    public Technology developers(Set<Developer> developers) {
        this.setDevelopers(developers);
        return this;
    }

    public Technology addDevelopers(Developer developer) {
        this.developers.add(developer);
        developer.getSkills().add(this);
        return this;
    }

    public Technology removeDevelopers(Developer developer) {
        this.developers.remove(developer);
        developer.getSkills().remove(this);
        return this;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.removeRequiredTechnologies(this));
        }
        if (projects != null) {
            projects.forEach(i -> i.addRequiredTechnologies(this));
        }
        this.projects = projects;
    }

    public Technology projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public Technology addProjects(Project project) {
        this.projects.add(project);
        project.getRequiredTechnologies().add(this);
        return this;
    }

    public Technology removeProjects(Project project) {
        this.projects.remove(project);
        project.getRequiredTechnologies().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Technology)) {
            return false;
        }
        return getId() != null && getId().equals(((Technology) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Technology{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
