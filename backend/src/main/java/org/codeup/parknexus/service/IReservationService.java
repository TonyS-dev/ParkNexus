package org.codeup.parknexus.service;

import org.codeup.parknexus.domain.Reservation;
import org.codeup.parknexus.domain.User;

import java.util.List;

public interface IReservationService {
    Reservation createReservation(User user, Long spotId);
    void cancelReservation(Long reservationId);
    List<Reservation> getUserReservations(Long userId);
}

