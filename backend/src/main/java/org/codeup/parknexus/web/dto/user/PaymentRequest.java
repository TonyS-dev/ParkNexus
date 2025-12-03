package org.codeup.parknexus.web.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentRequest {
    @NotNull
    private Long sessionId;

    @NotNull
    private Double amount;

    private String paymentMethod; // e.g., card, cash
}

