package org.codeup.parknexus.service;

import org.codeup.parknexus.domain.ParkingSession;
import org.codeup.parknexus.domain.ParkingSpot;
import org.codeup.parknexus.domain.User;
import org.codeup.parknexus.web.dto.user.CheckOutResponse;

import java.util.List;

public interface IParkingService {
    ParkingSession checkIn(User user, Long spotId);
    CheckOutResponse checkOut(Long sessionId);
    List<ParkingSpot> getAvailableSpots();
}

