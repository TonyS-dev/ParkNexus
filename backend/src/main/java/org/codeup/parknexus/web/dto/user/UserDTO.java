package org.codeup.parknexus.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codeup.parknexus.domain.enums.Role;

import java.util.UUID;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private UUID id;
    private String email;
    private String fullName;
}