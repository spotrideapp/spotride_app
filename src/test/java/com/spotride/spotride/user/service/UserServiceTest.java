package com.spotride.spotride.user.service;

import com.spotride.spotride.user.model.UserModel;
import com.spotride.spotride.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        UserModel user1 = new UserModel(1L, "user1", "pass1", "user1@example.com", "First1", "Last1");
        UserModel user2 = new UserModel(2L, "user2", "pass2", "user2@example.com", "First2", "Last2");
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserModel> users = userService.getAllUsers();
        assertEquals(2, users.size());
        assertEquals(user1, users.get(0));
        assertEquals(user2, users.get(1));
    }

    @Test
    void testGetUserById() {
        UserModel user = new UserModel(1L, "user1", "pass1", "user1@example.com", "First1", "Last1");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserModel foundUser = userService.getUserById(1L);
        assertEquals(user, foundUser);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        UserModel foundUser = userService.getUserById(1L);
        assertNull(foundUser);
    }

    @Test
    void testCreateUser() {
        UserModel user = new UserModel(1L, "user1", "pass1", "user1@example.com", "First1", "Last1");
        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        UserModel createdUser = userService.createUser(user);
        assertEquals(user, createdUser);
    }

    @Test
    void testUpdateUser() {
        UserModel existingUser = new UserModel(1L, "user1", "pass1", "user1@example.com", "First1", "Last1");
        UserModel updatedUser = new UserModel(1L, "user1Updated", "pass1Updated", "user1Updated@example.com", "First1Updated", "Last1Updated");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserModel.class))).thenReturn(updatedUser);

        UserModel result = userService.updateUser(1L, updatedUser);
        assertEquals(updatedUser, result);
    }

    @Test
    void testUpdateUserNotFound() {
        UserModel updatedUser = new UserModel(1L, "user1Updated", "pass1Updated", "user1Updated@example.com", "First1Updated", "Last1Updated");
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        UserModel result = userService.updateUser(1L, updatedUser);
        assertNull(result);
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }
}