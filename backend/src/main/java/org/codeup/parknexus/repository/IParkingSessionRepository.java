package org.codeup.parknexus.repository;

import org.codeup.parknexus.domain.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IParkingSessionRepository extends JpaRepository<ParkingSession, UUID> {

    // To check if a user already has a car parked (Prevent double check-in)
    Optional<ParkingSession> findByUserIdAndStatus(UUID userId, String status); // status = 'ACTIVE'

    // To find the active session for a specific Spot (when trying to leave)
    Optional<ParkingSession> findBySpotIdAndStatus(UUID spotId, String status);

    // Admin Dashboard: See how many cars are inside RIGHT NOW
    long countByStatus(String status);
}

