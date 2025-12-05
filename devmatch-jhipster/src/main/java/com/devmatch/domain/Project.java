package com.devmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Size(max = 50)
    @Column(name = "experience_level", length = 50, nullable = false)
    private String experienceLevel;

    @NotNull
    @Size(max = 50)
    @Column(name = "project_type", length = 50, nullable = false)
    private String projectType;

    @Size(max = 50)
    @Column(name = "status", length = 50)
    private String status;

    @Size(max = 100)
    @Column(name = "usuario_creacion", length = 100)
    private String usuarioCreacion;

    @Size(max = 100)
    @Column(name = "usuario_modificacion", length = 100)
    private String usuarioModificacion;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_modificacion")
    private Instant fechaModificacion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_project__required_technologies",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "required_technologies_id")
    )
    @JsonIgnoreProperties(value = { "developers", "projects" }, allowSetters = true)
    private Set<Technology> requiredTechnologies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Project name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Project description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperienceLevel() {
        return this.experienceLevel;
    }

    public Project experienceLevel(String experienceLevel) {
        this.setExperienceLevel(experienceLevel);
        return this;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getProjectType() {
        return this.projectType;
    }

    public Project projectType(String projectType) {
        this.setProjectType(projectType);
        return this;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getStatus() {
        return this.status;
    }

    public Project status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsuarioCreacion() {
        return this.usuarioCreacion;
    }

    public Project usuarioCreacion(String usuarioCreacion) {
        this.setUsuarioCreacion(usuarioCreacion);
        return this;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return this.usuarioModificacion;
    }

    public Project usuarioModificacion(String usuarioModificacion) {
        this.setUsuarioModificacion(usuarioModificacion);
        return this;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Instant getFechaCreacion() {
        return this.fechaCreacion;
    }

    public Project fechaCreacion(Instant fechaCreacion) {
        this.setFechaCreacion(fechaCreacion);
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return this.fechaModificacion;
    }

    public Project fechaModificacion(Instant fechaModificacion) {
        this.setFechaModificacion(fechaModificacion);
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Set<Technology> getRequiredTechnologies() {
        return this.requiredTechnologies;
    }

    public void setRequiredTechnologies(Set<Technology> technologies) {
        this.requiredTechnologies = technologies;
    }

    public Project requiredTechnologies(Set<Technology> technologies) {
        this.setRequiredTechnologies(technologies);
        return this;
    }

    public Project addRequiredTechnologies(Technology technology) {
        this.requiredTechnologies.add(technology);
        return this;
    }

    public Project removeRequiredTechnologies(Technology technology) {
        this.requiredTechnologies.remove(technology);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return getId() != null && getId().equals(((Project) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", experienceLevel='" + getExperienceLevel() + "'" +
            ", projectType='" + getProjectType() + "'" +
            ", status='" + getStatus() + "'" +
            ", usuarioCreacion='" + getUsuarioCreacion() + "'" +
            ", usuarioModificacion='" + getUsuarioModificacion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
