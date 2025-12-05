package com.devmatch.service.mapper;

import com.devmatch.domain.AuditHistory;
import com.devmatch.service.dto.AuditHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuditHistory} and its DTO {@link AuditHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuditHistoryMapper extends EntityMapper<AuditHistoryDTO, AuditHistory> {}
