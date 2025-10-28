package com.Yua.FastDelivery.Delivery_App.Config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfig {

    @Value("${razorpay.key_id}")
    private String key;
    @Value("${razorpay.key_secret}")
    private String secret;

    @Bean
    public RazorpayClient razorpayClient()throws RazorpayException{
        return new RazorpayClient(key,secret);
    }
}
