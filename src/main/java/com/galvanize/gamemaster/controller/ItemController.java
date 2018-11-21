package com.galvanize.gamemaster.controller;

import com.galvanize.gamemaster.model.Item;
import com.galvanize.gamemaster.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@ControllerAdvice // use for ErrorDetail
@RestController
@RequestMapping("/itemservice")
public class ItemController {
    public final ItemService itemService;


    public ItemController(ItemService service){
        this.itemService = service;

    }

    /**
    *Senario #1
    *Add item in Data Store and return response 200.
    * http://localhost:8080/itemservice/create, in RequestBody : {  "item_name" : "Mug" }
    * added Sword, Shield, Mug, Ball, Stick, Fish.
     *
     * Senario #2
     * http://localhost:8080/itemservice/create, in RequestBody : {"item_id : 4, "item_name" : "Coin"}
     * changed 4th item name to "Coin".
     */

        @PostMapping("/create")
        public ResponseEntity<Object> addItem(@RequestBody Item item){
        return this.itemService.addItem(item);
    }

    /** Senario # 3
     *  http://localhost:8080/itemservice/delete/4 - 200 - Item Deleted.
     *  Senario # 4
     *  http://localhost:8080/itemservice/delete/45 - 404 - Item not Found
     */

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<?> deleteItem(@PathVariable Long id){
            return this.itemService.deleteItem(id);

        }


    /**
     * Senario # 5
     * http://localhost:8080/itemservice/get/3 - 200 - Item retrieved.
     *
     * Senario # 6
     * http://localhost:8080/itemservice/get/34 - 404 - Item Not Found.
     */

        @GetMapping("/get/{id}")
        public ResponseEntity<Item> getItem(@PathVariable Long id){
        return this.itemService.getItem(id);
    }


    /**
     * Senario # 7
     * htto://localhost:8080/itemservice/get/{name}
     * http://localhost:8080/itemservice/get/item/sword
     */

        @GetMapping("/get/item/{item_name}")
        public Iterable<Item> getAllItemsByName(@PathVariable String item_name){
            return this.itemService.getAllItemsByName(item_name);
        }

    /**
     * Senario # 8
     * htto://localhost:8080/itemservice/allitems
     */

        @GetMapping("/allitems")
        public Iterable<Item> getAllItems(){
         return this.itemService.getAllItems();
        }

}