package com.Yua.FastDelivery.Delivery_App.Jwt;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OtpUtil {

    public static String otpGenerator(){
        int otp=100000+(int)(Math.random()*900000);
        return String.valueOf(otp);
    }

    public static Long expiredate(long minute){
        return System.currentTimeMillis()+(minute*1000*60);
    }
}
