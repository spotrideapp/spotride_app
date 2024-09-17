package com.spotride.spotride.user.controller;

import com.spotride.spotride.user.model.UserModel;
import com.spotride.spotride.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for {@link UserModel}.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public final class UserController {
    private final UserService userService;

    /**
     * Returns all users.
     *
     * @return list of users
     */
    @GetMapping
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Returns user by their id.
     *
     * @param id user id
     * @return user by id
     */
    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    /**
     * Create user.
     *
     * @param user user to create
     */
    @PostMapping
    public void createUser(@RequestBody UserModel user) {
        userService.createUser(user);
    }

    /**
     * Update user by id.
     *
     * @param id user id
     * @param user user to be updated
     */
    @PutMapping("/{id}")
    public void updateUser(@PathVariable long id, @RequestBody UserModel user) {
        userService.updateUser(id, user);
    }

    /**
     * Delete user.
     *
     * @param id user id
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }
}
