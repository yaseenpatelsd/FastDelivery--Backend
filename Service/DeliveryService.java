package com.Yua.FastDelivery.Delivery_App.Service;

import com.Yua.FastDelivery.Delivery_App.Dto.DeliveryRequestDto;
import com.Yua.FastDelivery.Delivery_App.Dto.DeliveryresponseDto;
import com.Yua.FastDelivery.Delivery_App.Dto.UserRequestDto;
import com.Yua.FastDelivery.Delivery_App.Dto.UserResponseDto;
import com.Yua.FastDelivery.Delivery_App.Entity.DeliveryEntity;
import com.Yua.FastDelivery.Delivery_App.Entity.ItemEntity;
import com.Yua.FastDelivery.Delivery_App.Entity.UserEntity;
import com.Yua.FastDelivery.Delivery_App.Repository.DeliveryRepository;
import com.Yua.FastDelivery.Delivery_App.Repository.ItemRepository;
import com.Yua.FastDelivery.Delivery_App.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;


    public DeliveryresponseDto create(DeliveryRequestDto dto){
        UserEntity user=userRepository.findByUsername(dto.getUsername())
                .orElseThrow(()->new RuntimeException("Username not found"));

        ItemEntity item=itemRepository.findById(dto.getItemid())
                .orElseThrow(()-> new RuntimeException("Item not found"));

        DeliveryEntity deliveryEntity=DeliveryEntity.builder()
                .address(dto.getAddress())
                .city(dto.getCity())
                .state(dto.getState())
                .pincode(dto.getPincode())
                .user(user)
                .item(item)
                .status("pending")
                .build();

        DeliveryEntity saved=deliveryRepository.save(deliveryEntity);

        return new DeliveryresponseDto(saved.getId(),item.getName(), saved.getAddress(), saved.getStatus());
    }

    public List<DeliveryresponseDto> getAllDeliveries(String username){
        List<DeliveryEntity> deliveries=deliveryRepository.findByUser_Username(username);
        return deliveries.stream()
                .map(d-> new DeliveryresponseDto(d.getId(),d.getItem().getName(),d.getAddress(),d.getStatus()))
                .collect(Collectors.toList());
    }

    public DeliveryresponseDto editstatus(long id){
        DeliveryEntity entity=deliveryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Item not found"));

        entity.setStatus("Delivered");
        deliveryRepository.save(entity);

        return new DeliveryresponseDto(
                entity.getId(),
                entity.getItem().getName(),
                entity.getAddress(),
                entity.getState()

        );

    }
}
