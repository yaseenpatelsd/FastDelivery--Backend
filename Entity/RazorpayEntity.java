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
@Table(name = "razor_pay")
@Builder
public class RazorpayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String currency;
    private Long amount;
    private String status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User")
    private UserEntity user;
}
