package com.spotride.spotride.user.controller;

import com.spotride.spotride.user.dto.request.UserCreateRequestDto;
import com.spotride.spotride.user.dto.UserResponseDto;
import com.spotride.spotride.user.dto.request.UserUpdateRequestDto;
import com.spotride.spotride.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link UserController}.
 */
@SpringBootTest
class UserControllerTest {

    @MockBean
    private UserService mockUserService;

    @Autowired
    private UserController userController;

    private LocalDateTime testZonedDateTime;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setUp() {
        testZonedDateTime = LocalDateTime.now();
    }

    @Test
    void testGetAllUsers() {
        var userDto = new UserResponseDto(1L, "john", "john@example.com", "John", "Doe", testZonedDateTime, testZonedDateTime);
        when(mockUserService.getAllUsers()).thenReturn(List.of(userDto));

        var result = userController.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockUserService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        var userDto = new UserResponseDto(1L, "john", "john@example.com", "John", "Doe", testZonedDateTime, testZonedDateTime);
        when(mockUserService.getUserById(1L)).thenReturn(userDto);

        var response = userController.getUserById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("john", Objects.requireNonNull(response.getBody()).getUsername());
        verify(mockUserService, times(1)).getUserById(1L);
    }

    @Test
    void testCreateUser() {
        var userRequestDto = new UserCreateRequestDto("john", "password", "john@example.com", "John", "Doe");
        var createdUserDto = new UserResponseDto(1L, "john", "john@example.com", "John", "Doe",  testZonedDateTime, testZonedDateTime);

        when(mockUserService.createUser(userRequestDto)).thenReturn(createdUserDto);

        var response = userController.createUser(userRequestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("john", Objects.requireNonNull(response.getBody()).getUsername());
        verify(mockUserService, times(1)).createUser(userRequestDto);
    }

    @Test
    void testUpdateUser() {
        var userRequestDto = new UserUpdateRequestDto(null, "john_updated", "password", "john_updated@example.com", "John", "Doe");
        var updatedUserDto = new UserResponseDto(1L, "john_updated", "john_updated@example.com", "John", "Doe", testZonedDateTime, testZonedDateTime);

        when(mockUserService.updateUser(1L, userRequestDto)).thenReturn(updatedUserDto);

        var response = userController.updateUser(1L, userRequestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("john_updated", Objects.requireNonNull(response.getBody()).getUsername());
        verify(mockUserService, times(1)).updateUser(1L, userRequestDto);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(mockUserService).deleteUser(1L);

        var response = userController.deleteUser(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());
        verify(mockUserService, times(1)).deleteUser(1L);
    }
}
