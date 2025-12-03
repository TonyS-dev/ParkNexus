package org.codeup.parknexus.service.impl;

import org.codeup.parknexus.domain.ParkingSession;
import org.codeup.parknexus.domain.ParkingSpot;
import org.codeup.parknexus.domain.User;
import org.codeup.parknexus.service.IParkingService;
import org.codeup.parknexus.web.dto.user.CheckOutResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl implements IParkingService {
    @Override
    public ParkingSession checkIn(User user, Long spotId) {
        return null;
    }

    @Override
    public CheckOutResponse checkOut(Long sessionId) {
        return null;
    }

    @Override
    public List<ParkingSpot> getAvailableSpots() {
        return List.of();
    }
}

