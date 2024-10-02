package com.spotride.spotride.user.service;

import com.spotride.spotride.user.UserMapper;
import com.spotride.spotride.user.dto.request.UserCreateRequestDto;
import com.spotride.spotride.user.dto.UserResponseDto;
import com.spotride.spotride.user.dto.request.UserUpdateRequestDto;
import com.spotride.spotride.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public final class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    /**
     * Gets all users.
     *
     * @return list of {@link UserResponseDto}
     */
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Returns user by id.
     *
     * @param id user id
     * @return {@link UserResponseDto} by user id
     */
    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

    /**
     * Created user.
     *
     * @param userRequestDto {@link UserCreateRequestDto} for user
     * @return {@link UserResponseDto} for created user
     */
    public UserResponseDto createUser(UserCreateRequestDto userRequestDto) {
        var user = userMapper.toEntity(userRequestDto);

        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Update user by id.
     *
     * @param id user id
     * @param updatedUserDto user to be updated
     */
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto updatedUserDto) {
        return userRepository.findById(id)
                .map(user -> {
                    userMapper.updateEntityFromDto(updatedUserDto, user);
                    return userMapper.toDto(userRepository.save(user));
                })
                .orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
