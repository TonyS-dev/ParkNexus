package org.codeup.parknexus.web.mapper;

import org.codeup.parknexus.domain.ActivityLog;
import org.codeup.parknexus.web.dto.admin.ActivityLogResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityLogMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    ActivityLogResponse toResponse(ActivityLog log);
    List<ActivityLogResponse> toResponses(List<ActivityLog> logs);
}

