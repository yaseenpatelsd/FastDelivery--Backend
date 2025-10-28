package com.Yua.FastDelivery.Delivery_App.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RazorpayResponseDto {
    private String id;
    private String currency;
    private String amount;
    private String status;
}
