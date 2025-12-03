package org.codeup.parknexus.web.mapper;

import org.codeup.parknexus.domain.ParkingSpot;
import org.codeup.parknexus.web.dto.admin.SpotResponse;
import org.codeup.parknexus.web.dto.user.ParkingSpotResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParkingSpotMapper {
    @Mapping(target = "floorName", expression = "java(\"Floor \" + spot.getFloor().getFloorNumber())")
    @Mapping(target = "buildingName", source = "floor.building.name")
    @Mapping(target = "type", expression = "java(spot.getType().name())")
    @Mapping(target = "status", expression = "java(spot.getStatus().name())")
    ParkingSpotResponse toUserResponse(ParkingSpot spot);
    List<ParkingSpotResponse> toUserResponses(List<ParkingSpot> spots);

    @Mapping(target = "floorId", source = "floor.id")
    @Mapping(target = "floorName", expression = "java(\"Floor \" + spot.getFloor().getFloorNumber())")
    @Mapping(target = "type", expression = "java(spot.getType().name())")
    @Mapping(target = "status", expression = "java(spot.getStatus().name())")
    SpotResponse toAdminResponse(ParkingSpot spot);
    List<SpotResponse> toAdminResponses(List<ParkingSpot> spots);
}

