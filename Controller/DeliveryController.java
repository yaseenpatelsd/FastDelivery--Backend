package com.Yua.FastDelivery.Delivery_App.Controller;

import com.Yua.FastDelivery.Delivery_App.Dto.DeliveryRequestDto;
import com.Yua.FastDelivery.Delivery_App.Dto.DeliveryresponseDto;
import com.Yua.FastDelivery.Delivery_App.Entity.DeliveryEntity;
import com.Yua.FastDelivery.Delivery_App.Repository.DeliveryRepository;
import com.Yua.FastDelivery.Delivery_App.Service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DeliveryRepository deliveryRepository;

    @PostMapping("/buy")
    public ResponseEntity<DeliveryresponseDto> add(@RequestBody DeliveryRequestDto deliveryRequestDto){
        DeliveryresponseDto dto=deliveryService.create(deliveryRequestDto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<DeliveryresponseDto>> getAllOrders(Authentication authentication){
      List<DeliveryresponseDto> saved= deliveryService.getAllDeliveries(authentication.getName());
       return ResponseEntity.ok(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryresponseDto> edit(@PathVariable long id){
        DeliveryresponseDto dto=deliveryService.editstatus(id);
        return ResponseEntity.ok(dto);
    }
}
