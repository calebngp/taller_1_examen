package com.devmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Developer.
 */
@Entity
@Table(name = "developer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Developer implements Serializable {

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

    @Size(max = 200)
    @Column(name = "email", length = 200, unique = true)
    private String email;

    @Size(max = 50)
    @Column(name = "experience_level", length = 50)
    private String experienceLevel;

    @Lob
    @Column(name = "bio")
    private String bio;

    @Size(max = 200)
    @Column(name = "location", length = 200)
    private String location;

    @Size(max = 500)
    @Column(name = "github_profile", length = 500)
    private String githubProfile;

    @Size(max = 500)
    @Column(name = "linkedin", length = 500)
    private String linkedin;

    @Lob
    @Column(name = "motivation")
    private String motivation;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "developer")
    @JsonIgnoreProperties(value = { "developer" }, allowSetters = true)
    private Set<Experience> experiences = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_developer__skills",
        joinColumns = @JoinColumn(name = "developer_id"),
        inverseJoinColumns = @JoinColumn(name = "skills_id")
    )
    @JsonIgnoreProperties(value = { "developers", "projects" }, allowSetters = true)
    private Set<Technology> skills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Developer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Developer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Developer email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExperienceLevel() {
        return this.experienceLevel;
    }

    public Developer experienceLevel(String experienceLevel) {
        this.setExperienceLevel(experienceLevel);
        return this;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getBio() {
        return this.bio;
    }

    public Developer bio(String bio) {
        this.setBio(bio);
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return this.location;
    }

    public Developer location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGithubProfile() {
        return this.githubProfile;
    }

    public Developer githubProfile(String githubProfile) {
        this.setGithubProfile(githubProfile);
        return this;
    }

    public void setGithubProfile(String githubProfile) {
        this.githubProfile = githubProfile;
    }

    public String getLinkedin() {
        return this.linkedin;
    }

    public Developer linkedin(String linkedin) {
        this.setLinkedin(linkedin);
        return this;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getMotivation() {
        return this.motivation;
    }

    public Developer motivation(String motivation) {
        this.setMotivation(motivation);
        return this;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getUsuarioCreacion() {
        return this.usuarioCreacion;
    }

    public Developer usuarioCreacion(String usuarioCreacion) {
        this.setUsuarioCreacion(usuarioCreacion);
        return this;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return this.usuarioModificacion;
    }

    public Developer usuarioModificacion(String usuarioModificacion) {
        this.setUsuarioModificacion(usuarioModificacion);
        return this;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Instant getFechaCreacion() {
        return this.fechaCreacion;
    }

    public Developer fechaCreacion(Instant fechaCreacion) {
        this.setFechaCreacion(fechaCreacion);
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return this.fechaModificacion;
    }

    public Developer fechaModificacion(Instant fechaModificacion) {
        this.setFechaModificacion(fechaModificacion);
        return this;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Set<Experience> getExperiences() {
        return this.experiences;
    }

    public void setExperiences(Set<Experience> experiences) {
        if (this.experiences != null) {
            this.experiences.forEach(i -> i.setDeveloper(null));
        }
        if (experiences != null) {
            experiences.forEach(i -> i.setDeveloper(this));
        }
        this.experiences = experiences;
    }

    public Developer experiences(Set<Experience> experiences) {
        this.setExperiences(experiences);
        return this;
    }

    public Developer addExperiences(Experience experience) {
        this.experiences.add(experience);
        experience.setDeveloper(this);
        return this;
    }

    public Developer removeExperiences(Experience experience) {
        this.experiences.remove(experience);
        experience.setDeveloper(null);
        return this;
    }

    public Set<Technology> getSkills() {
        return this.skills;
    }

    public void setSkills(Set<Technology> technologies) {
        this.skills = technologies;
    }

    public Developer skills(Set<Technology> technologies) {
        this.setSkills(technologies);
        return this;
    }

    public Developer addSkills(Technology technology) {
        this.skills.add(technology);
        return this;
    }

    public Developer removeSkills(Technology technology) {
        this.skills.remove(technology);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Developer)) {
            return false;
        }
        return getId() != null && getId().equals(((Developer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Developer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", experienceLevel='" + getExperienceLevel() + "'" +
            ", bio='" + getBio() + "'" +
            ", location='" + getLocation() + "'" +
            ", githubProfile='" + getGithubProfile() + "'" +
            ", linkedin='" + getLinkedin() + "'" +
            ", motivation='" + getMotivation() + "'" +
            ", usuarioCreacion='" + getUsuarioCreacion() + "'" +
            ", usuarioModificacion='" + getUsuarioModificacion() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            "}";
    }
}
