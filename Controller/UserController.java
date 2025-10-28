package com.Yua.FastDelivery.Delivery_App.Controller;

import com.Yua.FastDelivery.Delivery_App.Dto.*;
import com.Yua.FastDelivery.Delivery_App.Entity.UserEntity;
import com.Yua.FastDelivery.Delivery_App.Exception.InvalidOtpExeption;
import com.Yua.FastDelivery.Delivery_App.Exception.ResourceNotFound;
import com.Yua.FastDelivery.Delivery_App.Exception.SomethingIsWrong;
import com.Yua.FastDelivery.Delivery_App.Exception.UnAuthorizedExeption;
import com.Yua.FastDelivery.Delivery_App.Jwt.JwtUtil;
import com.Yua.FastDelivery.Delivery_App.Jwt.OtpUtil;
import com.Yua.FastDelivery.Delivery_App.Repository.RazorpayRepository;
import com.Yua.FastDelivery.Delivery_App.Repository.UserRepository;
import com.Yua.FastDelivery.Delivery_App.Service.EmailSander;
import com.Yua.FastDelivery.Delivery_App.Service.RazorPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailSander emailSander;
    @Autowired
    private RazorPayService razorPayService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto requestDto){
  try {
      Optional<UserEntity> optionalUserEntity=userRepository.findByUsername(requestDto.getUsername());
      if (optionalUserEntity.isPresent()){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username Already exist ");
      }

      UserEntity user=new UserEntity();

      user.setUsername(requestDto.getUsername());
      user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
      user.setEmail(requestDto.getEmail());
      user.setRole("USER");

      String otp= OtpUtil.otpGenerator();
      long expiretime=OtpUtil.expiredate(10);

      emailSander.otpforAccountVerifation(requestDto.getEmail(),otp);

      user.setOtp(otp);
      user.setExpiretime(expiretime);
      user.setVarified(false);

      userRepository.save(user);

      return ResponseEntity.status(HttpStatus.CREATED).body("User is register successfully");
  }catch (Exception e){
      throw new SomethingIsWrong("Something went wrong");
  }
}

@PostMapping("/admin/register")
    public ResponseEntity<?> AdminRegisterUser(@RequestBody UserRequestDto requestDto){
  try {
      Optional<UserEntity> optionalUserEntity=userRepository.findByUsername(requestDto.getUsername());
      if (optionalUserEntity.isPresent()){
         throw new ResourceNotFound("Username is already register");
      }

      UserEntity user=new UserEntity();

      user.setUsername(requestDto.getUsername());
      user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
      user.setEmail(requestDto.getEmail());
      user.setRole("ADMIN");

      String otp= OtpUtil.otpGenerator();
      long expiretime=OtpUtil.expiredate(10);

      emailSander.otpforAccountVerifation(requestDto.getEmail(),otp);

      user.setOtp(otp);
      user.setExpiretime(expiretime);
      user.setVarified(false);

      userRepository.save(user);

      return ResponseEntity.status(HttpStatus.CREATED).body("User is register successfully");
  }catch (Exception ex){
      throw new SomethingIsWrong("Something went wrong");
  }
}
    @PostMapping("/account-verification")
    public ResponseEntity<?> accountVerification(@RequestBody AccountVerificationDto verificationDto){
      try {
          Optional<UserEntity> optionalUserEntity=userRepository.findByUsername(verificationDto.getUsername());
          if (optionalUserEntity.isEmpty()){
              throw new ResourceNotFound("Username does not exist");
          }
          UserEntity user=optionalUserEntity.get();
          String otp=user.getOtp();
          Long expiredate=user.getExpiretime();
          String storeotp=verificationDto.getOtp();

          if(storeotp==null || expiredate==null){
              throw new InvalidOtpExeption("Otp is Invalid");
          }
          if (otp==null || otp.isEmpty()){
              throw new InvalidOtpExeption("Otp is Invalid");
          }

          if (!otp.equals(storeotp)){
              throw new InvalidOtpExeption("Invalid otp please try again");
          }
          if (expiredate<System.currentTimeMillis()){
              throw new InvalidOtpExeption("Otp is expired");
          }

          user.setOtp(null);
          user.setExpiretime(null);
          user.setVarified(true);

          userRepository.save(user);
          return ResponseEntity.status(HttpStatus.CREATED).body("Account is varified you can log in");
      }catch (Exception ex){
          throw new SomethingIsWrong("Something went wrong");
      }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto requestDto){
     try {
         Optional<UserEntity> optionalUserEntity=userRepository.findByUsername(requestDto.getUsername());
         if (optionalUserEntity.isEmpty()){
             throw new ResourceNotFound("Username does not exist");
         }
         UserEntity user=optionalUserEntity.get();
         if(user.getVarified().equals(false)){
            throw new UnAuthorizedExeption("Account is register but isnt verified yet please verify first before log in");
         }
         try {
             authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(requestDto.getUsername(),requestDto.getPassword())
             );
         }catch (Exception e){
            throw new UnAuthorizedExeption("Not authorized");
         }

         String token=jwtUtil.generateToken(requestDto.getUsername());
         UserResponseDto userResponseDto=new UserResponseDto();
         userResponseDto.setToken(token);

         user.setOtp(null);
         user.setExpiretime(null);
         userRepository.save(user);

         return ResponseEntity.ok(userResponseDto);
     }catch (Exception ex){
         throw new SomethingIsWrong("Something went wrong");
     }
    }

    @PostMapping("/Otp-request")
    public ResponseEntity<?> otpforAccount(@RequestBody OtpRequstDto otpRequstDto){
    try {
        Optional<UserEntity> optionalUserEntity=userRepository.findByUsername(otpRequstDto.getUsername());
        if (optionalUserEntity.isEmpty()){
            throw new ResourceNotFound("Username does not exist");
        }

        UserEntity user=optionalUserEntity.get();

        user.setOtp(null);
        user.setExpiretime(null);


        String otp=OtpUtil.otpGenerator();
        Long expiretime=OtpUtil.expiredate(10);

        user.setOtp(otp);
        user.setExpiretime(expiretime);

        userRepository.save(user);

        emailSander.otpforAccountVerifation(user.getEmail(),otp);
        return ResponseEntity.status(HttpStatus.CREATED).body("Otp sand successfully");
    }catch (Exception e){
        throw new SomethingIsWrong("Something went wrong");
    }
    }

    @PostMapping("/Otp-request-for-password-reset")
    public ResponseEntity<?> PasswordReset(@RequestBody OtpRequstDto otpRequstDto){
    try {
        Optional<UserEntity> optionalUserEntity=userRepository.findByUsername(otpRequstDto.getUsername());
        if (optionalUserEntity.isEmpty()){
            throw new ResourceNotFound("Username does not exist");
        }

        UserEntity user=optionalUserEntity.get();


        String otp=OtpUtil.otpGenerator();
        Long expiretime=OtpUtil.expiredate(10);

        user.setOtp(otp);
        user.setExpiretime(expiretime);

        userRepository.save(user);

        emailSander.otpforAccountVerifation(user.getEmail(),otp);
        return ResponseEntity.status(HttpStatus.CREATED).body("Otp sand successfully");
    }catch (Exception e){
        throw new SomethingIsWrong("Something went wrong");
    }
    }

    @PostMapping("/password-reset")
    public ResponseEntity<?> passwordreset(@RequestBody PasswordForgetDto forgetDto){
      try {
          Optional<UserEntity> optionalUserEntity=userRepository.findByUsername(forgetDto.getUsername());
          if (optionalUserEntity.isEmpty()){
             throw new ResourceNotFound("Username does not exist");
          }
          UserEntity user=optionalUserEntity.get();
          String storeotp= user.getOtp();
          Long expire= user.getExpiretime();
          String newPassword=forgetDto.getPassword();

          if (storeotp==null || expire==null){
            throw new InvalidOtpExeption("Otp or expire date is invalid");
          }
          if (!storeotp.equals(forgetDto.getOtp())){
             throw new InvalidOtpExeption("invalid otp");
          }
          if (forgetDto.getOtp()==null || forgetDto.getOtp().isEmpty()){
              throw new InvalidOtpExeption("Invalid otp please provide current one");
          }
          if (expire<System.currentTimeMillis()){
            throw new InvalidOtpExeption("Otp expired");
          }

          user.setOtp(null);
          user.setExpiretime(null);
          user.setPassword(passwordEncoder.encode(newPassword));
          userRepository.save(user);

          return ResponseEntity.status(HttpStatus.CREATED).body("New password is been set successfully");
      }catch (Exception e){
          throw new SomethingIsWrong("Something went wrong");
      }
    }

    @GetMapping("/about-me")
    public ResponseEntity<?> getABOUTMe(Authentication authentication){
        UserEntity user=userRepository.findByUsername(authentication.getName())
                .orElseThrow(()-> new ResourceNotFound("Username not found"));

        String username=user.getUsername();
        String email=user.getEmail();
        String subscribe=user.getStatus();

        return ResponseEntity.ok("Username is "+ username + "\n" +" Email is "+ email+ "\n"+
        "Status of account? "+ user.getStatus() +"\n"+ "Thanks for being our user");

    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody RazorpayRequestDto razorpayRequestDto){
        try {
            RazorpayResponseDto responseDto=razorPayService.razorpayResponseDto(razorpayRequestDto);
            return ResponseEntity.ok(responseDto);
        }catch (Exception e){
            throw new SomethingIsWrong("Something went wrong");
        }
    }
}
