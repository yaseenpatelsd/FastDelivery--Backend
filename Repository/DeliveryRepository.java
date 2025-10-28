package com.Yua.FastDelivery.Delivery_App.Repository;

import com.Yua.FastDelivery.Delivery_App.Entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
    List<DeliveryEntity> findByUser_Username(String username);
}
