package org.codeup.parknexus.service.impl;

import org.codeup.parknexus.domain.ActivityLog;
import org.codeup.parknexus.domain.User;
import org.codeup.parknexus.service.IActivityLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityLogServiceImpl implements IActivityLogService {
    @Override
    public void log(User user, String action, String details) {

    }

    @Override
    public List<ActivityLog> getUserLogs(Long userId) {
        return List.of();
    }
}

