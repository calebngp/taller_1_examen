package com.devmatch.service.mapper;

import com.devmatch.domain.Developer;
import com.devmatch.domain.Technology;
import com.devmatch.service.dto.DeveloperDTO;
import com.devmatch.service.dto.TechnologyDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Developer} and its DTO {@link DeveloperDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeveloperMapper extends EntityMapper<DeveloperDTO, Developer> {
    @Mapping(target = "skills", source = "skills", qualifiedByName = "technologyIdSet")
    DeveloperDTO toDto(Developer s);

    @Mapping(target = "removeSkills", ignore = true)
    Developer toEntity(DeveloperDTO developerDTO);

    @Named("technologyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TechnologyDTO toDtoTechnologyId(Technology technology);

    @Named("technologyIdSet")
    default Set<TechnologyDTO> toDtoTechnologyIdSet(Set<Technology> technology) {
        return technology.stream().map(this::toDtoTechnologyId).collect(Collectors.toSet());
    }
}
