package com.Yua.FastDelivery.Delivery_App.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username", nullable = false,unique = true)
    private String username;
    @Column(name = "password", nullable = false,unique = true)
    private String password;
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    @Column(name = "role")
    private String role;

    private String otp;
    private Long expiretime;
    private Boolean varified=false;

    private String status="NonSubscriber";
}
