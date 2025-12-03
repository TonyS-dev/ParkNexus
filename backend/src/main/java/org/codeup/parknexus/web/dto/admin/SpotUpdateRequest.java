package org.codeup.parknexus.web.dto.admin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SpotUpdateRequest {
    @NotNull
    private Long spotId;

    @NotNull
    private String status; // e.g., AVAILABLE, MAINTENANCE, OCCUPIED
}

