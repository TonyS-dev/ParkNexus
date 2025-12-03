package org.codeup.parknexus.web.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservationRequest {
    @NotNull
    private Long spotId;

    @NotNull
    private LocalDateTime startTime;

    // optional duration in minutes
    private Integer durationMinutes;
}

