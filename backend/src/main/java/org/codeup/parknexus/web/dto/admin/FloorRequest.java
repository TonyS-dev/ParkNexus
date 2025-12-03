package org.codeup.parknexus.web.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FloorRequest {
    @NotNull
    private Long buildingId;

    @NotBlank
    private String name;
}

