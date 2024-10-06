package com.spotride.spotride.user.service;

import com.spotride.spotride.user.UserMapper;
import com.spotride.spotride.user.UserMapperImpl;
import com.spotride.spotride.user.dto.request.UserCreateRequestDto;
import com.spotride.spotride.user.dto.request.UserUpdateRequestDto;
import com.spotride.spotride.user.model.User;
import com.spotride.spotride.user.repository.UserRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link UserService}.
 */
@ContextConfiguration(classes = {UserService.class, UserMapperImpl.class})
@ExtendWith(SpringExtension.class)
@SuppressFBWarnings(value = "SECHCP", justification = "Hardcoded password.")
class UserServiceTest {

    private static final LocalDateTime DATE_TIME_NOW = LocalDateTime.now();

    @MockBean
    private UserRepository mockUserRepository;

    @Autowired
    private UserMapper userMapper;

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
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .createdAt(DATE_TIME_NOW)
                .modifiedAt(null)
                .build();

        when(mockUserRepository.findAll()).thenReturn(List.of(user));

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
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .createdAt(DATE_TIME_NOW)
                .modifiedAt(null)
                .build();

        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));

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
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .build();

        var savedUser = User.builder()
                .id(1L)
                .username("john")
                .password("password")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .createdAt(DATE_TIME_NOW)
                .modifiedAt(DATE_TIME_NOW)
                .build();

        when(mockUserRepository.save(any(User.class))).thenReturn(savedUser);

        var user = userMapper.toEntity(userCreateRequestDto);
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
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .build();

        var user = User.builder()
                .id(1L)
                .username("john")
                .password("password")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
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
                .phoneNumber("123456789")
                .birthDate(LocalDate.now())
                .city("CityName")
                .createdAt(DATE_TIME_NOW)
                .modifiedAt(DATE_TIME_NOW)
                .build();

        when(mockUserRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mockUserRepository.save(user)).thenReturn(updatedUser);

        userMapper.updateEntityFromDto(userUpdateRequestDto, user);
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
