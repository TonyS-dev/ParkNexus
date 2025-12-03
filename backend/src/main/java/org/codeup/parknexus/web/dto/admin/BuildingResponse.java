package org.codeup.parknexus.web.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BuildingResponse {
    private Long id;
    private String name;
    private String address;
}

