package com.spotride.spotride.user.service;

import com.spotride.spotride.user.model.User;
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
        User user1 = new User(1L, "user1", "pass1", "user1@example.com", "First1", "Last1");
        User user2 = new User(2L, "user2", "pass2", "user2@example.com", "First2", "Last2");
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
        assertEquals(user1, users.get(0));
        assertEquals(user2, users.get(1));
    }

    @Test
    void testGetUserById() {
        User user = new User(1L, "user1", "pass1", "user1@example.com", "First1", "Last1");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);
        assertEquals(user, foundUser);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        User foundUser = userService.getUserById(1L);
        assertNull(foundUser);
    }

    @Test
    void testCreateUser() {
        User user = new User(1L, "user1", "pass1", "user1@example.com", "First1", "Last1");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertEquals(user, createdUser);
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User(1L, "user1", "pass1", "user1@example.com", "First1", "Last1");
        User updatedUser = new User(1L, "user1Updated", "pass1Updated", "user1Updated@example.com", "First1Updated", "Last1Updated");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(1L, updatedUser);
        assertEquals(updatedUser, result);
    }

    @Test
    void testUpdateUserNotFound() {
        User updatedUser = new User(1L, "user1Updated", "pass1Updated", "user1Updated@example.com", "First1Updated", "Last1Updated");
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        User result = userService.updateUser(1L, updatedUser);
        assertNull(result);
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(1L);
        verify(userRepository).deleteById(1L);
    }
}