package com.Yua.FastDelivery.Delivery_App.Controller;

import com.Yua.FastDelivery.Delivery_App.Dto.ItemDto;
import com.Yua.FastDelivery.Delivery_App.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItem(@PathVariable long id){
      ItemDto item=itemService.getItemn(id);
      return ResponseEntity.ok(item);

    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ItemDto>> getAll(){
        List<ItemDto> saveditem=itemService.getall();
        return  ResponseEntity.ok(saveditem);
    }
}
