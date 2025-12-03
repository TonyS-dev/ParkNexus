package org.codeup.parknexus.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.codeup.parknexus.domain.ActivityLog;
import org.codeup.parknexus.domain.Building;
import org.codeup.parknexus.repository.IActivityLogRepository;
import org.codeup.parknexus.repository.IUserRepository;
import org.codeup.parknexus.service.IAdminService;
import org.codeup.parknexus.web.dto.admin.*;
import org.codeup.parknexus.web.mapper.ActivityLogMapper;
import org.codeup.parknexus.web.mapper.BuildingMapper;
import org.codeup.parknexus.web.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IAdminService adminService;
    private final IActivityLogRepository activityLogRepository;
    private final IUserRepository userRepository;
    private final ActivityLogMapper activityLogMapper;
    private final BuildingMapper buildingMapper;
    private final UserMapper userMapper;

    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> dashboard() {
        return ResponseEntity.ok(adminService.getDashboard());
    }

    @PostMapping("/buildings")
    public ResponseEntity<BuildingResponse> createBuilding(@Valid @RequestBody BuildingRequest request) {
        Building building = Building.builder().name(request.getName()).address(request.getAddress()).build();
        return ResponseEntity.ok(buildingMapper.toResponse(building));
    }

    @GetMapping("/logs")
    public ResponseEntity<List<ActivityLogResponse>> logs(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ActivityLog> logPage = activityLogRepository.findAll(pageable);
        return ResponseEntity.ok(activityLogMapper.toResponses(logPage.getContent()));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> users(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<org.codeup.parknexus.domain.User> userPage = userRepository.findAll(pageable);
        return ResponseEntity.ok(userMapper.toResponses(userPage.getContent()));
    }
}
