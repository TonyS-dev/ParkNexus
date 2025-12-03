package org.codeup.parknexus.web.mapper;

import org.codeup.parknexus.domain.Reservation;
import org.codeup.parknexus.web.dto.user.ReservationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    @Mapping(target = "spotId", source = "spot.id")
    @Mapping(target = "spotIdentifier", source = "spot.spotNumber")
    @Mapping(target = "status", expression = "java(reservation.getStatus().name())")
    ReservationResponse toResponse(Reservation reservation);
}

