package com.Yua.FastDelivery.Delivery_App.GlobleExeption;

import com.Yua.FastDelivery.Delivery_App.Dto.ResponseDto;
import com.Yua.FastDelivery.Delivery_App.Exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExeption {

  @ExceptionHandler(InvalidOtpExeption.class)
    public org.springframework.http.ResponseEntity<ResponseDto> handleNotFound(InvalidOtpExeption ex, HttpServletRequest request){
      return buildResponse(HttpStatus.BAD_REQUEST ,"Inalid otp or otp is expire" ,ex.getMessage(),request);
  }

  @ExceptionHandler(PaymentFailExeption.class)
  public ResponseEntity<ResponseDto> handleNotFound(PaymentFailExeption ex,HttpServletRequest request){
      return buildResponse(HttpStatus.BAD_REQUEST , "payment fails", ex.getMessage(), request);
  }

  @ExceptionHandler(ResourceNotFound.class)
  public ResponseEntity<ResponseDto> handleNotFound(ResourceNotFound ex,HttpServletRequest request){
      return buildResponse(HttpStatus.NOT_FOUND,"Resource not found" , ex.getMessage(), request);
  }
  @ExceptionHandler(SomethingIsWrong.class)
  public ResponseEntity<ResponseDto> handleNotFound(SomethingIsWrong ex, HttpServletRequest request){
      return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Something is wrong ", ex.getMessage(), request);
  }

  @ExceptionHandler(UnAuthorizedExeption.class)
  public ResponseEntity<ResponseDto> handleNotFound(UnAuthorizedExeption ex,HttpServletRequest request){
      return buildResponse(HttpStatus.UNAUTHORIZED,"UnAuthorized for this page" , ex.getMessage(), request);
  }
    private ResponseEntity<ResponseDto> buildResponse(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request
    ) {
        ResponseDto response = new ResponseDto(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(response);
    }
}
