package org.codeup.parknexus.service.impl;

import org.codeup.parknexus.domain.User;
import org.codeup.parknexus.domain.enums.Role;
import org.codeup.parknexus.service.IAdminService;
import org.codeup.parknexus.web.dto.admin.AdminDashboardResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements IAdminService {
    @Override
    public AdminDashboardResponse getDashboard() {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User updateUserRole(Long userId, Role role) {
        return null;
    }
}

