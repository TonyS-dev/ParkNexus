package org.codeup.parknexus.repository;

import org.codeup.parknexus.domain.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IActivityLogRepository extends JpaRepository<ActivityLog, Long> {
}

