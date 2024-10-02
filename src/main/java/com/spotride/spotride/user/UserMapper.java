package com.spotride.spotride.user;

import com.spotride.spotride.user.dto.request.UserCreateRequestDto;
import com.spotride.spotride.user.dto.UserResponseDto;
import com.spotride.spotride.user.dto.request.UserUpdateRequestDto;
import com.spotride.spotride.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for {@link User}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserCreateRequestDto userCreateRequestDto);

    User toEntity(UserUpdateRequestDto userUpdateRequestDto);

    UserResponseDto toDto(User user);

    void updateEntityFromDto(UserUpdateRequestDto userUpdateRequestDto, @MappingTarget User user);
}
