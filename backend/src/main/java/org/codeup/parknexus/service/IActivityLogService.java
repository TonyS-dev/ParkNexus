package org.codeup.parknexus.service;

import org.codeup.parknexus.domain.ActivityLog;
import org.codeup.parknexus.domain.User;

import java.util.List;

public interface IActivityLogService {
    void log(User user, String action, String details);
    List<ActivityLog> getUserLogs(Long userId);
}

