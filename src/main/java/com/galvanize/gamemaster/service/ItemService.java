package com.galvanize.gamemaster.service;

import com.galvanize.gamemaster.Exception.ItemNotFoundException;
import com.galvanize.gamemaster.model.Item;
import com.galvanize.gamemaster.repository.ItemRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    public ItemService(ItemRepository repository){
        this.itemRepository = repository;
    }

    // add Item, update item with same request for Senario # 1, Senario # 2.
    // insert item in data store and send response 200

    public Item addItem(Item item){

        if(item.getItem_id() != null) {
            if(itemRepository.existsById(item.getItem_id())) {
                LOGGER.info("Item with item_id: " + item.getItem_id() + " is updated.");
                this.itemRepository.save(item);
            }

        }
        LOGGER.info("New item added.");
        return this.itemRepository.save(item);
    }

    // delete Item.
    public void deleteItem(Long id){
        Optional<Item> itemOptional = itemRepository.findById(id);

        if (!itemOptional.isPresent())
            LOGGER.info("Item not found.");
        else
            LOGGER.info("Item is deleted.");
            itemRepository.deleteById(id);
    }

    //change item,
    //if exist then change and send back
    //if not exist then send message not exist

    public Item changeItem(Item item, Long id){

        Optional<Item> itemOptional = itemRepository.findById(id);

        if (!itemOptional.isPresent())
            return new Item();

        Item newItem = ((Item)itemOptional.get());
        newItem.setItem_id(id);


        return this.itemRepository.save(newItem);

       // return   ResponseEntity.noContent().build();

    }



    //




}
