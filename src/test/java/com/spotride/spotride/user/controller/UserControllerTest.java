package com.spotride.spotride.user.controller;

import com.spotride.spotride.user.dto.UserResponseDto;
import com.spotride.spotride.user.dto.request.UserCreateRequestDto;
import com.spotride.spotride.user.dto.request.UserUpdateRequestDto;
import com.spotride.spotride.user.service.UserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
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
@ContextConfiguration(classes = UserController.class)
@ExtendWith(SpringExtension.class)
@SuppressFBWarnings(value = "SECHCP", justification = "Hardcoded password.")
class UserControllerTest {

    @MockBean
    private UserService mockUserService;

    @Autowired
    private UserController userController;

    @Test
    void testGetAllUsers() {
        var userDto = UserResponseDto.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .build();

        when(mockUserService.getAllUsers()).thenReturn(List.of(userDto));

        var result = userController.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockUserService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        var userDto = UserResponseDto.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .build();

        when(mockUserService.getUserById(1L)).thenReturn(userDto);

        var response = userController.getUserById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("john", Objects.requireNonNull(response.getBody()).getUsername());
        verify(mockUserService, times(1)).getUserById(1L);
    }

    @Test
    void testCreateUser() {
        var userCreateRequestDto = UserCreateRequestDto.builder()
                .username("john")
                .password("password")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .build();

        var createdUserDto = UserResponseDto.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .build();


        when(mockUserService.createUser(userCreateRequestDto)).thenReturn(createdUserDto);

        var response = userController.createUser(userCreateRequestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("john", Objects.requireNonNull(response.getBody()).getUsername());
        verify(mockUserService, times(1)).createUser(userCreateRequestDto);
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
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .build();

        var updatedUserDto = UserResponseDto.builder()
                .id(1L)
                .username("john_updated")
                .email("john_updated@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .build();

        when(mockUserService.updateUser(1L, userUpdateRequestDto)).thenReturn(updatedUserDto);

        var response = userController.updateUser(1L, userUpdateRequestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("john_updated", Objects.requireNonNull(response.getBody()).getUsername());
        verify(mockUserService, times(1)).updateUser(1L, userUpdateRequestDto);
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
