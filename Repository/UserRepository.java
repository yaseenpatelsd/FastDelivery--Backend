package com.Yua.FastDelivery.Delivery_App.Repository;

import com.Yua.FastDelivery.Delivery_App.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
}
