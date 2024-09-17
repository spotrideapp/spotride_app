package com.spotride.spotride.user.service;

import com.spotride.spotride.user.model.UserModel;
import com.spotride.spotride.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User service.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public final class UserService {
    private final UserRepository userRepository;

    /**
     * Returns all users.
     *
     * @return list of users
     */
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Returns user by their id.
     *
     * @param id user id
     * @return user by id
     */
    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Create user.
     *
     * @param user user to create
     */
    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    /**
     * Update user by id.
     *
     * @param id user id
     * @param updatedUser user to be updated
     */
    public UserModel updateUser(Long id, UserModel updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(updatedUser.getPassword());
                    user.setEmail(updatedUser.getEmail());
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());

                    return userRepository.save(user);
                }).orElse(null);
    }

    /**
     * Delete user.
     *
     * @param id user id
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
