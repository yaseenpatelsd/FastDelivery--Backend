package com.Yua.FastDelivery.Delivery_App.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountVerificationDto {
    private String username;
    private String otp;
}
