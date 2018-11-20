package com.galvanize.gamemaster.model;


import javax.persistence.*;

@Entity(name="ITEM")
public class Item {
    public Item(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long item_id;
    private String item_name;

    public Item(Long id, String name){
        this.item_id = id;
        this.item_name = name;
    }


    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }



}
