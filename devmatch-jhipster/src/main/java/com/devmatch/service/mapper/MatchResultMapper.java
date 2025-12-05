package com.devmatch.service.mapper;

import com.devmatch.domain.Developer;
import com.devmatch.domain.MatchResult;
import com.devmatch.domain.Project;
import com.devmatch.service.dto.DeveloperDTO;
import com.devmatch.service.dto.MatchResultDTO;
import com.devmatch.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MatchResult} and its DTO {@link MatchResultDTO}.
 */
@Mapper(componentModel = "spring")
public interface MatchResultMapper extends EntityMapper<MatchResultDTO, MatchResult> {
    @Mapping(target = "project", source = "project", qualifiedByName = "projectId")
    @Mapping(target = "developer", source = "developer", qualifiedByName = "developerId")
    MatchResultDTO toDto(MatchResult s);

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);

    @Named("developerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DeveloperDTO toDtoDeveloperId(Developer developer);
}
