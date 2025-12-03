package org.codeup.parknexus.web.mapper;

import org.codeup.parknexus.domain.User;
import org.codeup.parknexus.web.dto.admin.UserResponse;
import org.codeup.parknexus.web.dto.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "email", source = "email")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "role", expression = "java(user.getRole().name())")
    @Mapping(target = "isActive", source = "isActive")
    @Mapping(target = "createdAt", source = "createdAt")
    UserResponse toResponse(User user);
    List<UserResponse> toResponses(List<User> users);
    UserDTO toDTO(User user);
}

