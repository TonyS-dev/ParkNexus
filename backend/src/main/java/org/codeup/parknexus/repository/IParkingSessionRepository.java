package org.codeup.parknexus.repository;

import org.codeup.parknexus.domain.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IParkingSessionRepository extends JpaRepository<ParkingSession, Long> {
}

