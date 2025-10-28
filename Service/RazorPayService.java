package com.Yua.FastDelivery.Delivery_App.Service;

import com.Yua.FastDelivery.Delivery_App.Dto.RazorpayRequestDto;
import com.Yua.FastDelivery.Delivery_App.Dto.RazorpayResponseDto;
import com.Yua.FastDelivery.Delivery_App.Dto.UserResponseDto;
import com.Yua.FastDelivery.Delivery_App.Entity.RazorpayEntity;
import com.Yua.FastDelivery.Delivery_App.Entity.UserEntity;
import com.Yua.FastDelivery.Delivery_App.Repository.RazorpayRepository;
import com.Yua.FastDelivery.Delivery_App.Repository.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;

@Service
public class RazorPayService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RazorpayRepository razorpayRepository;
    @Autowired
    private RazorpayClient razorpayClient;

    private long AmountforSubscribption=100*50;

    public RazorpayResponseDto razorpayResponseDto(RazorpayRequestDto razorpayRequestDto)throws RazorpayException{
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("amount" , AmountforSubscribption);
        jsonObject.put("currency","INR");
        jsonObject.put("receipt", "txn_" + System.currentTimeMillis());
        jsonObject.put("payment_capture", 1);

        Order order= razorpayClient.orders.create(jsonObject);

        String id=order.get("id").toString();
        String amount=order.get("amount").toString();
        String stattus=order.get("status").toString();
        String currency=order.get("currency").toString();


        UserEntity user =userRepository.findByUsername(razorpayRequestDto.getUsername())
                .orElseThrow(()->new RuntimeException("Username not available "));

         RazorpayEntity razorpay=RazorpayEntity.builder()
                .amount(Long.parseLong(amount))
                .currency(currency)
                .status(stattus)
                .user(user)
                .build();

         razorpayRepository.save(razorpay);

         user.setStatus("Subscribed");
         userRepository.save(user);

         return new RazorpayResponseDto(id,currency,amount,stattus);
    }
}
