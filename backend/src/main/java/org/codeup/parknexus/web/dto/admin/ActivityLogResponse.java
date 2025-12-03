package org.codeup.parknexus.web.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ActivityLogResponse {
    private Long id;
    private String message;
    private String actor;
    private LocalDateTime createdAt;
}

