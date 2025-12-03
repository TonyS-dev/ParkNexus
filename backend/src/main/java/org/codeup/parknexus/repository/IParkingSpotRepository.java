package org.codeup.parknexus.repository;

import org.codeup.parknexus.domain.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
}

