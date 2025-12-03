package org.codeup.parknexus.web.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CheckInRequest {
    @NotNull
    private Long spotId;

    // optional reservation id if checking in for a reservation
    private Long reservationId;
}

