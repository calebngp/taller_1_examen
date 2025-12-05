package com.devmatch.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.devmatch.domain.Developer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DeveloperDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    @Size(max = 200)
    private String email;

    @Size(max = 50)
    private String experienceLevel;

    @Lob
    private String bio;

    @Size(max = 200)
    private String location;

    @Size(max = 500)
    private String githubProfile;

    @Size(max = 500)
    private String linkedin;

    @Lob
    private String motivation;

    @Size(max = 100)
    private String usuarioCreacion;

    @Size(max = 100)
    private String usuarioModificacion;

    private Instant fechaCreacion;

    private Instant fechaModificacion;

    private Set<TechnologyDTO> skills = new HashSet<>();

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGithubProfile() {
        return githubProfile;
    }

    public void setGithubProfile(String githubProfile) {
        this.githubProfile = githubProfile;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Set<TechnologyDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<TechnologyDTO> skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeveloperDTO)) {
            return false;
        }

        DeveloperDTO developerDTO = (DeveloperDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, developerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeveloperDTO{" +
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
            ", skills=" + getSkills() +
            "}";
    }
}
