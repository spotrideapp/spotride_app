package com.spotride.spotride.user.service;

import com.spotride.spotride.user.UserMapper;
import com.spotride.spotride.user.dto.UserResponseDto;
import com.spotride.spotride.user.dto.request.UserCreateRequestDto;
import com.spotride.spotride.user.dto.request.UserUpdateRequestDto;
import com.spotride.spotride.user.model.User;
import com.spotride.spotride.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link UserService}.
 */
@SpringBootTest
class UserServiceTest {

    private static final LocalDateTime DATE_TIME_NOW = LocalDateTime.now();

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private UserMapper mockUserMapper;

    @Autowired
    private UserService userService;

    @Test
    void testGetAllUsers() {
        var user = User.builder()
                .id(1L)
                .username("john")
                .password("password")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .createdAt(DATE_TIME_NOW)
                .modifiedAt(null)
                .build();

        var userDto = UserResponseDto.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        when(mockUserRepository.findAll()).thenReturn(List.of(user));
        when(mockUserMapper.toDto(user)).thenReturn(userDto);

        var result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("john", result.getFirst().getUsername());
        verify(mockUserRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        var user = User.builder()
                .id(1L)
                .username("john")
                .password("password")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .createdAt(DATE_TIME_NOW)
                .modifiedAt(null)
                .build();

        var userDto = UserResponseDto.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mockUserMapper.toDto(user)).thenReturn(userDto);

        var result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        verify(mockUserRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateUser() {
        var userCreateRequestDto = UserCreateRequestDto.builder()
                .username("john")
                .password("password")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        var user = User.builder()
                .id(1L)
                .username("john")
                .password("password")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .createdAt(DATE_TIME_NOW)
                .modifiedAt(null)
                .build();

        var savedUser = User.builder()
                .id(1L)
                .username("john")
                .password("password")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .createdAt(DATE_TIME_NOW)
                .modifiedAt(DATE_TIME_NOW)
                .build();

        var userResponseDto = UserResponseDto.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        when(mockUserMapper.toEntity(userCreateRequestDto)).thenReturn(user);
        when(mockUserRepository.save(any(User.class))).thenReturn(savedUser);
        when(mockUserMapper.toDto(savedUser)).thenReturn(userResponseDto);

        var createdUser = userService.createUser(userCreateRequestDto);

        assertNotNull(createdUser);
        assertEquals("john", createdUser.getUsername());
        assertEquals("john@example.com", createdUser.getEmail());
        verify(mockUserRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        var userUpdateRequestDto = UserUpdateRequestDto.builder()
                .id(null)
                .username("john_updated")
                .password("password")
                .email("john_updated@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        var user = User.builder()
                .id(1L)
                .username("john")
                .password("password")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .createdAt(DATE_TIME_NOW)
                .modifiedAt(null)
                .build();

        var updatedUser = User.builder()
                .id(1L)
                .username("john_updated")
                .password("password")
                .email("john_updated@example.com")
                .firstName("John")
                .lastName("Doe")
                .createdAt(DATE_TIME_NOW)
                .modifiedAt(DATE_TIME_NOW)
                .build();

        var updatedUserDto = UserResponseDto.builder()
                .id(1L)
                .username("john_updated")
                .email("john_updated@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        doAnswer(invocation -> {
            user.setUsername(userUpdateRequestDto.getUsername());
            user.setEmail(userUpdateRequestDto.getEmail());
            return null;
        }).when(mockUserMapper).updateEntityFromDto(userUpdateRequestDto, user);
        when(mockUserRepository.save(user)).thenReturn(updatedUser);
        when(mockUserMapper.toDto(updatedUser)).thenReturn(updatedUserDto);

        var result = userService.updateUser(1L, userUpdateRequestDto);

        assertNotNull(result);
        assertEquals("john_updated", result.getUsername());
        verify(mockUserRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(mockUserRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(mockUserRepository, times(1)).deleteById(1L);
    }
}
