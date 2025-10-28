package com.Yua.FastDelivery.Delivery_App.Controller;

import com.Yua.FastDelivery.Delivery_App.Dto.ItemDto;
import com.Yua.FastDelivery.Delivery_App.Repository.ItemRepository;
import com.Yua.FastDelivery.Delivery_App.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemRepository itemRepository;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ItemDto> add(@RequestBody ItemDto itemDto){
        ItemDto itemDto1=itemService.additem(itemDto);
        return ResponseEntity.ok(itemDto1);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeItem(@PathVariable long id){
      itemService.deleteItem(id);
      return ResponseEntity.ok("Delete successful");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-all-Item")
    public ResponseEntity<String> removeALlProduct(){
        itemService.deleteAll();
        return ResponseEntity.ok("Delete all items");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> edititem(@RequestBody ItemDto itemDto,@PathVariable long id){
        ItemDto itemDto1=itemService.changeItem(itemDto,id);
        return ResponseEntity.ok(itemDto1);
    }


}
