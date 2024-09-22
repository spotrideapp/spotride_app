package com.spotride.spotride.user;

import com.spotride.spotride.user.dto.UserRequestDto;
import com.spotride.spotride.user.dto.UserResponseDto;
import com.spotride.spotride.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for {@link User}.
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserRequestDto userRequestDto);

    UserResponseDto toDto(User user);

    void updateEntityFromDto(UserRequestDto userRequestDto, @MappingTarget User user);
}
