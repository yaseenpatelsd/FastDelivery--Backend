package com.Yua.FastDelivery.Delivery_App.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDto {
   private String username;
   private long itemid;
   private String address;
   private String city;
   private String state;
   private String pincode;

}
