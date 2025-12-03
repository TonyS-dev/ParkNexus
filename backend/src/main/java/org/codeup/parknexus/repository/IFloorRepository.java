package org.codeup.parknexus.repository;

import org.codeup.parknexus.domain.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFloorRepository extends JpaRepository<Floor, Long> {
}

