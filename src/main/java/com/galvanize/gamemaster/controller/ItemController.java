package com.galvanize.gamemaster.controller;

import com.galvanize.gamemaster.model.Item;
import com.galvanize.gamemaster.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itemservice")
public class ItemController {
    public final ItemService itemService;


    public ItemController(ItemService service){
        this.itemService = service;

    }

    /*
    *Senario #1
    *Add item in Data Store and return response 200.
    * http://localhost:8080/itemservice/create, in RequestBody : {  "item_name" : "Mug" }
    * added Sword, Shield, Mug, Ball, Stick, Fish.
    * Senario #2
    * http://localhost:8080/itemservice/create, in RequestBody : {"item_id : 4, "item_name" : "Coin"}
    * changed 4th item name to "Coin".
    */

    @PostMapping("/create")
    public Item addItem(@RequestBody Item item){
        return this.itemService.addItem(item);
    }

    /* Senario # 3
    http://localhost:8080/itemservice/delete/4

     */
    @DeleteMapping("/delete/{id}")
    public void deleteItem(@PathVariable Long id){
        itemService.deleteItem(id);

    }

    /*
     *Senario #2
     * Update item in Data Store if exists return response 200.
     * http://localhost:8080/itemservice/update/{4}, in RequestBody : {  "item_name" : "Coin" }
     * if "id" found then change "item_name".
     */

    @PutMapping("/update/{id}")
    public Item changeItem(@RequestBody Item item, @PathVariable Long id){


        return this.itemService.changeItem(item, id);
    }


}
