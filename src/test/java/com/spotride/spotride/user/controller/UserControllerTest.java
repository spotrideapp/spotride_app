package com.spotride.spotride.user.controller;

import com.spotride.spotride.user.dto.UserRequestDto;
import com.spotride.spotride.user.dto.UserResponseDto;
import com.spotride.spotride.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link UserController}.
 */
class UserControllerTest {

    @Mock
    private UserService mockUserService;

    @InjectMocks
    private UserController mockUserController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        var userDto = new UserResponseDto(1L, "john", "john@example.com", "John", "Doe");
        when(mockUserService.getAllUsers()).thenReturn(List.of(userDto));

        var result = mockUserController.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockUserService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        var userDto = new UserResponseDto(1L, "john", "john@example.com", "John", "Doe");
        when(mockUserService.getUserById(1L)).thenReturn(userDto);

        var response = mockUserController.getUserById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("john", response.getBody().getUsername());
        verify(mockUserService, times(1)).getUserById(1L);
    }

    @Test
    void testCreateUser() {
        var userRequestDto = new UserRequestDto(null, "john", "password", "john@example.com", "John", "Doe");
        var createdUserDto = new UserResponseDto(1L, "john", "john@example.com", "John", "Doe");

        when(mockUserService.createUser(userRequestDto)).thenReturn(createdUserDto);

        var response = mockUserController.createUser(userRequestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("john", response.getBody().getUsername());
        verify(mockUserService, times(1)).createUser(userRequestDto);
    }

    @Test
    void testUpdateUser() {
        var userRequestDto = new UserRequestDto(null, "john_updated", "password", "john_updated@example.com", "John", "Doe");
        var updatedUserDto = new UserResponseDto(1L, "john_updated", "john_updated@example.com", "John", "Doe");

        when(mockUserService.updateUser(1L, userRequestDto)).thenReturn(updatedUserDto);

        var response = mockUserController.updateUser(1L, userRequestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("john_updated", response.getBody().getUsername());
        verify(mockUserService, times(1)).updateUser(1L, userRequestDto);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(mockUserService).deleteUser(1L);

        var response = mockUserController.deleteUser(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(mockUserService, times(1)).deleteUser(1L);
    }
}
