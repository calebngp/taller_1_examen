package com.devmatch.service.mapper;

import com.devmatch.domain.Project;
import com.devmatch.domain.Technology;
import com.devmatch.service.dto.ProjectDTO;
import com.devmatch.service.dto.TechnologyDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "requiredTechnologies", source = "requiredTechnologies", qualifiedByName = "technologyIdSet")
    ProjectDTO toDto(Project s);

    @Mapping(target = "removeRequiredTechnologies", ignore = true)
    Project toEntity(ProjectDTO projectDTO);

    @Named("technologyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TechnologyDTO toDtoTechnologyId(Technology technology);

    @Named("technologyIdSet")
    default Set<TechnologyDTO> toDtoTechnologyIdSet(Set<Technology> technology) {
        return technology.stream().map(this::toDtoTechnologyId).collect(Collectors.toSet());
    }
}
