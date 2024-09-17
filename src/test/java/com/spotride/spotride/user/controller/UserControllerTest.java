package com.spotride.spotride.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotride.spotride.user.model.UserModel;
import com.spotride.spotride.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllUsers() throws Exception {
        UserModel user1 = new UserModel(1L, "user1", "pass1", "user1@example.com", "First1", "Last1");
        UserModel user2 = new UserModel(2L, "user2", "pass2", "user2@example.com", "First2", "Last2");
        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(user1, user2))));
    }

    @Test
    void testGetUserById() throws Exception {
        UserModel user = new UserModel(1L, "user1", "pass1", "user1@example.com", "First1", "Last1");
        when(userService.getUserById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    void testCreateUser() throws Exception {
        UserModel user = new UserModel(1L, "user1", "pass1", "user1@example.com", "First1", "Last1");
        when(userService.createUser(any(UserModel.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateUser() throws Exception {
        UserModel user = new UserModel(1L, "user1Updated", "pass1Updated", "user1Updated@example.com", "First1Updated", "Last1Updated");
        when(userService.updateUser(anyLong(), any(UserModel.class))).thenReturn(user);

        mockMvc.perform(put("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isOk());
    }
}