package com.Yua.FastDelivery.Delivery_App.Repository;

import com.Yua.FastDelivery.Delivery_App.Entity.RazorpayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RazorpayRepository extends JpaRepository<RazorpayEntity, Long> {
    List<RazorpayEntity> findByUser_Username(String username);
}
