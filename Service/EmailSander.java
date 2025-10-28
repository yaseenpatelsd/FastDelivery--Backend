package com.Yua.FastDelivery.Delivery_App.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSander {

    @Autowired
    private JavaMailSender javaMailSender;

    public String otpforAccountVerifation(String email,String otp){
      try {
          SimpleMailMessage message=new SimpleMailMessage();
          message.setTo(email);
          message.setSubject("Otp for account verification");
          message.setText(otp+" is your otp for account verification it will be valid for 10 minutes");

          javaMailSender.send(message);

          return "Mail is sand to "+email;
      }catch (Exception e){
          return "Something is wrong "+e.getMessage();
      }
    }
}
