package org.codeup.parknexus.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CheckInResponse {
    private Long sessionId;
    private Long spotId;
    private LocalDateTime checkInTime;
    private String message;
}

