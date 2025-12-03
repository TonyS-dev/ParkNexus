package org.codeup.parknexus.service.impl;

import org.codeup.parknexus.domain.Reservation;
import org.codeup.parknexus.domain.User;
import org.codeup.parknexus.service.IReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements IReservationService {
    @Override
    public Reservation createReservation(User user, Long spotId) {
        return null;
    }

    @Override
    public void cancelReservation(Long reservationId) {

    }

    @Override
    public List<Reservation> getUserReservations(Long userId) {
        return List.of();
    }
}

