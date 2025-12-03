package org.codeup.parknexus.repository;

import org.codeup.parknexus.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IBuildingRepository extends JpaRepository<Building, UUID> {
    // Validation when creating a building
    boolean existsByNameIgnoreCase(String name);
}
