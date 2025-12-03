package org.codeup.parknexus.service;

import org.codeup.parknexus.domain.User;
import org.codeup.parknexus.domain.enums.Role;
import org.codeup.parknexus.web.dto.admin.AdminDashboardResponse;

import java.util.List;

public interface IAdminService {
    AdminDashboardResponse getDashboard();
    List<User> getAllUsers();
    User updateUserRole(Long userId, Role role);
}

