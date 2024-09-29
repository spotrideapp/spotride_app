package com.spotride.spotride.user.service;

import com.spotride.spotride.user.UserMapper;
import com.spotride.spotride.user.dto.UserRequestDto;
import com.spotride.spotride.user.dto.UserResponseDto;
import com.spotride.spotride.user.model.User;
import com.spotride.spotride.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @MockBean
    private UserRepository mockUserRepository;

    @MockBean
    private UserMapper mockUserMapper;

    @Autowired
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        var user = new User(1L, "john", "password", "john@example.com", "John", "Doe");
        var userDto = new UserResponseDto(1L, "john", "john@example.com", "John", "Doe");

        when(mockUserRepository.findAll()).thenReturn(List.of(user));
        when(mockUserMapper.toDto(user)).thenReturn(userDto);

        var result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("john", result.getFirst().getUsername());
        verify(mockUserRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        var user = new User(1L, "john", "password", "john@example.com", "John", "Doe");
        var userDto = new UserResponseDto(1L, "john", "john@example.com", "John", "Doe");

        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mockUserMapper.toDto(user)).thenReturn(userDto);

        UserResponseDto result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        verify(mockUserRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateUser() {
        var userRequestDto = new UserRequestDto(null, "testUsername", "testPassword", "testEmail", "Test", "User");
        var user = new User(null, "testUsername", "testPassword", "testEmail", "Test", "User");
        var savedUser = new User(1L, "testUsername", "testPassword", "testEmail", "Test", "User"); // Добавляем ID
        var userResponseDto = new UserResponseDto(1L, "testUsername", "testEmail", "Test", "User");

        when(mockUserMapper.toEntity(userRequestDto)).thenReturn(user);
        when(mockUserRepository.save(any(User.class))).thenReturn(savedUser);
        when(mockUserMapper.toDto(savedUser)).thenReturn(userResponseDto);

        var createdUser = userService.createUser(userRequestDto);

        assertNotNull(createdUser);
        assertEquals("testUsername", createdUser.getUsername());
        assertEquals("testEmail", createdUser.getEmail());
        verify(mockUserRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        var userRequestDto = new UserRequestDto(null, "john_updated", "password", "john_updated@example.com", "John", "Doe");
        var user = new User(1L, "john", "password", "john@example.com", "John", "Doe");
        var updatedUser = new User(1L, "john_updated", "password", "john_updated@example.com", "John", "Doe");
        var updatedUserDto = new UserResponseDto(1L, "john_updated", "john_updated@example.com", "John", "Doe");

        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        doAnswer(invocation -> {
            // Update the user with the new DTO values
            user.setUsername(userRequestDto.getUsername());
            user.setEmail(userRequestDto.getEmail());
            return null;
        }).when(mockUserMapper).updateEntityFromDto(userRequestDto, user);
        when(mockUserRepository.save(user)).thenReturn(updatedUser);
        when(mockUserMapper.toDto(updatedUser)).thenReturn(updatedUserDto);

        UserResponseDto result = userService.updateUser(1L, userRequestDto);

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
