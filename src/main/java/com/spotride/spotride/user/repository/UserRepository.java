package com.spotride.spotride.user.repository;

import com.spotride.spotride.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementation {@link JpaRepository} for {@link UserModel}.
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
}
