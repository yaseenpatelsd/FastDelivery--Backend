package com.Yua.FastDelivery.Delivery_App.Entity;

import com.Yua.FastDelivery.Delivery_App.Repository.UserRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "delivery_entity")
@Builder
public class DeliveryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "address",nullable = false)
    private String address;
    @Column(name = "city" ,nullable = false)
    private String city;
    @Column(name = "state" ,nullable = false)
    private String state;
    @Column(name = "pincode" ,nullable = false)
    private String pincode;
    @Column(name = "status" ,nullable = false)
    private String status="pending";

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private ItemEntity item;
}
