package com.devmatch.service.mapper;

import com.devmatch.domain.Developer;
import com.devmatch.domain.Experience;
import com.devmatch.service.dto.DeveloperDTO;
import com.devmatch.service.dto.ExperienceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Experience} and its DTO {@link ExperienceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExperienceMapper extends EntityMapper<ExperienceDTO, Experience> {
    @Mapping(target = "developer", source = "developer", qualifiedByName = "developerId")
    ExperienceDTO toDto(Experience s);

    @Named("developerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DeveloperDTO toDtoDeveloperId(Developer developer);
}
