package com.galvanize.gamemaster.repository;

import com.galvanize.gamemaster.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

   @Query("from Item it where it.name=:name")
    public List<Item> findItemsByName(@Param("name") String name);


}