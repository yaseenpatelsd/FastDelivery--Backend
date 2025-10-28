package com.Yua.FastDelivery.Delivery_App.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryresponseDto {
    private long id;
    private String itemName;
    private String address;
    private String status;

}
