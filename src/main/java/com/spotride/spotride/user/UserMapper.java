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

    // Маппинг для создания пользователя
    User toEntity(UserCreateRequestDto userCreateRequestDto);

    // Маппинг для обновления пользователя
    User toEntity(UserUpdateRequestDto userUpdateRequestDto);

    // Для возврата ответа в DTO
    UserResponseDto toDto(User user);

    // Обновление сущности на основе UserUpdateRequestDto
    void updateEntityFromDto(UserUpdateRequestDto userUpdateRequestDto, @MappingTarget User user);
}
