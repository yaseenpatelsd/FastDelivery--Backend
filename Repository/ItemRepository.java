package com.Yua.FastDelivery.Delivery_App.Repository;

import com.Yua.FastDelivery.Delivery_App.Entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity ,Long> {
    List<ItemEntity> findByUser_Username(String username);
}
