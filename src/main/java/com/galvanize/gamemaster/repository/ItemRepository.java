package com.galvanize.gamemaster.repository;

import com.galvanize.gamemaster.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}
