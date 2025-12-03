package org.codeup.parknexus.repository;

import org.codeup.parknexus.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBuildingRepository extends JpaRepository<Building, Long> {
}

