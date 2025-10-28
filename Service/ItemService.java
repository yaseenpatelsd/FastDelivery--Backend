package com.Yua.FastDelivery.Delivery_App.Service;

import com.Yua.FastDelivery.Delivery_App.Dto.ItemDto;
import com.Yua.FastDelivery.Delivery_App.Entity.ItemEntity;
import com.Yua.FastDelivery.Delivery_App.Entity.UserEntity;
import com.Yua.FastDelivery.Delivery_App.Exception.ResourceNotFound;
import com.Yua.FastDelivery.Delivery_App.Exception.SomethingIsWrong;
import com.Yua.FastDelivery.Delivery_App.Mapping.ItemMapping;
import com.Yua.FastDelivery.Delivery_App.Repository.ItemRepository;
import com.Yua.FastDelivery.Delivery_App.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;


    public ItemDto additem(ItemDto dto){
        ItemEntity entity= ItemMapping.mapToEntity(dto);
        ItemEntity savedEntity=itemRepository.save(entity);
        return ItemMapping.mapToDto(savedEntity);
    }

    public ItemDto getItemn(long id){
      ItemEntity itemEntity=itemRepository.findById(id)
              .orElseThrow(()-> new ResourceNotFound("Id not found"));

      return ItemMapping.mapToDto(itemEntity);
    }

    public List<ItemDto> getall(){
        List<ItemEntity> itemEntities=itemRepository.findAll();
        return itemEntities.stream().map(ItemMapping::mapToDto).collect(Collectors.toList());
    }

    public void deleteItem(long id){
        ItemEntity entity=itemRepository.findById(id).orElseThrow(()->new ResourceNotFound("Item  not found"));

        itemRepository.delete(entity);
    }

    public ItemDto changeItem(ItemDto itemDto, long id){
        ItemEntity entity=itemRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Item not found"));

        entity.setName(itemDto.getName());
        entity.setDescription(itemDto.getDescription());
        entity.setAmount(itemDto.getAmount());
        entity.setRating(itemDto.getRating());
        entity.setStock(itemDto.getStock());

        itemRepository.save(entity);

        return ItemMapping.mapToDto(entity);
    }

    public String deleteAll(){
     try {
         List<ItemEntity> entity=  itemRepository.findAll();

         itemRepository.delete((ItemEntity) entity);
         return "Successfully";
     }catch (Exception e){
       return "Something is wrong"+e.getMessage();
     }
    }
}
