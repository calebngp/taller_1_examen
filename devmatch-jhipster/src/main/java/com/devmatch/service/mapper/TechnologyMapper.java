package com.devmatch.service.mapper;

import com.devmatch.domain.Developer;
import com.devmatch.domain.Project;
import com.devmatch.domain.Technology;
import com.devmatch.service.dto.DeveloperDTO;
import com.devmatch.service.dto.ProjectDTO;
import com.devmatch.service.dto.TechnologyDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Technology} and its DTO {@link TechnologyDTO}.
 */
@Mapper(componentModel = "spring")
public interface TechnologyMapper extends EntityMapper<TechnologyDTO, Technology> {
    @Mapping(target = "developers", source = "developers", qualifiedByName = "developerIdSet")
    @Mapping(target = "projects", source = "projects", qualifiedByName = "projectIdSet")
    TechnologyDTO toDto(Technology s);

    @Mapping(target = "developers", ignore = true)
    @Mapping(target = "removeDevelopers", ignore = true)
    @Mapping(target = "projects", ignore = true)
    @Mapping(target = "removeProjects", ignore = true)
    Technology toEntity(TechnologyDTO technologyDTO);

    @Named("developerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DeveloperDTO toDtoDeveloperId(Developer developer);

    @Named("developerIdSet")
    default Set<DeveloperDTO> toDtoDeveloperIdSet(Set<Developer> developer) {
        return developer.stream().map(this::toDtoDeveloperId).collect(Collectors.toSet());
    }

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);

    @Named("projectIdSet")
    default Set<ProjectDTO> toDtoProjectIdSet(Set<Project> project) {
        return project.stream().map(this::toDtoProjectId).collect(Collectors.toSet());
    }
}
