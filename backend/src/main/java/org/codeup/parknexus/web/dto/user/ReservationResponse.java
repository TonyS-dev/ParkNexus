package org.codeup.parknexus.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservationResponse {
    private Long id;
    private Long spotId;
    private String spotIdentifier;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
}

