package com.galvanize.gamemaster.service;

import com.galvanize.gamemaster.Exception.ItemNotFoundException;
import com.galvanize.gamemaster.model.Item;
import com.galvanize.gamemaster.repository.ItemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    public ItemService(ItemRepository repository){
        this.itemRepository = repository;
    }

    /** Senario # 1 - add Item, Senario #2 - update Item
    * insert item in data store, return 200
    */

    public ResponseEntity<Object> addItem(Item item){
        if(item.getId() != null) {
            if(itemRepository.existsById(item.getId())) {
                LOGGER.info("New item added.");
                this.itemRepository.save(item);
            }
        }
        else {
            LOGGER.info("Item with item_id: " + item.getId() + " is updated.");
            this.itemRepository.save(item);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /** Senario # 3 delete Item, return 200.
     * Senario # 4 while delete if Item not found, Service Return 404
     */

    public ResponseEntity<?> deleteItem(Long id){
        Optional<Item> itemOptional = itemRepository.findById(id);

        if(!itemOptional.isPresent()){
            LOGGER.info("Item not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //ResponseEntity.notFound().build();
             }
        else
            LOGGER.info("Item is deleted.");
            itemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);

    }

    /***
     * Scenario 5 : Get a specific item.
     * GET/ return in JSON
     * Given An object of type  "item" with the format { "id": 1, "name": "sword" } is in the data store.
     * When a GET call is made to /object/get/{className}/{id}
     * Then The object is returned in json format along with a 200 code.
     *
     * Senario # 6 - Get Request - Item not found - Service Returns 404
     * Because "ItemNotFoundException" have set @ResponseStatus(HttpStatus.NOT_FOUND)
     */

    public ResponseEntity<Item> getItem(Long id){

        return itemRepository.findById(id)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElseThrow(() -> new ItemNotFoundException(id.toString()));
    }



    /**
     * Scenario # 7 Get all items of a specific type.
     * GET / Specific type JSON Array
     * Given Several objects of type  "item" are in the data store.
     * When a GET call is made to /object/get/{className}
     * Then The service returns a JSON array of all the items that match the className (could be an empty array)
     * service returns 200

     */

    public Iterable<Item> getAllItemsByName(String name){
        return this.itemRepository.findItemsByName(name);
    }

    /**
     * Scenario # 8 -  Get all items in the registry.
     * Given Several objects are in the data store.
     * When a GET call is made to /object/get/
     * Then The service returns a JSON array of all the items in the data store(could be an empty array) and a 200.
     *
     */

    public Iterable<Item> getAllItems(){
        return this.itemRepository.findAll();
    }

}
