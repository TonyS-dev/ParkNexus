package org.codeup.parknexus.service;

import org.codeup.parknexus.web.dto.user.DashboardResponse;

public interface IUserService {
    DashboardResponse getDashboard(Long userId);
}

