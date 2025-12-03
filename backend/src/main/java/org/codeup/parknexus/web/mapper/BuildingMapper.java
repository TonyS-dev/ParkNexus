package org.codeup.parknexus.web.mapper;

import org.codeup.parknexus.domain.Building;
import org.codeup.parknexus.web.dto.admin.BuildingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BuildingMapper {
    @Mapping(target = "totalFloors", ignore = true)
    @Mapping(target = "totalSpots", ignore = true)
    BuildingResponse toResponse(Building building);
}

