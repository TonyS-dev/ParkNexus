package org.codeup.parknexus.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardResponse {
    private Long activeReservations;
    private Long activeSessions;
    private Long totalReservations;
    private Double outstandingFees;
}

