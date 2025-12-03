package org.codeup.parknexus.repository;

import org.codeup.parknexus.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}

