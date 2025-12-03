package org.codeup.parknexus.web.mapper;

import org.codeup.parknexus.domain.ParkingSession;
import org.codeup.parknexus.web.dto.user.ParkingSessionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ParkingSpotMapper.class})
public interface ParkingSessionMapper {
    
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "spotId", source = "spot.id")
    @Mapping(target = "spot", source = "spot")
    @Mapping(target = "status", expression = "java(session.getStatus().name())")
    @Mapping(target = "duration", ignore = true)
    @Mapping(target = "fee", source = "amountDue")
    @Mapping(target = "transactionId", ignore = true)
    @Mapping(target = "paymentMethod", ignore = true)
    ParkingSessionResponse toResponse(ParkingSession session);
    
    List<ParkingSessionResponse> toResponses(List<ParkingSession> sessions);
}

