package org.codeup.parknexus.service.impl;

import org.codeup.parknexus.service.IUserService;
import org.codeup.parknexus.web.dto.user.DashboardResponse;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Override
    public DashboardResponse getDashboard(Long userId) {
        return null;
    }
}

