package com.ccfish.learnjava.es.controller;

import com.ccfish.learnjava.es.model.Item;
import com.ccfish.learnjava.es.test.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: Ciaos
 * @Date: 2019/11/3 14:28
 */
@RestController
@RequestMapping("es")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping("add")
    public String add() {
        Item item = new Item();
        item.setId("1");
        item.setBand("queen");
        item.setCategory("rock'n roll");
        item.setImages("pic.jpg");
        item.setPrice("100");
        item.setTitle("CD");
        itemRepository.save(item);
        System.out.println("add a obj");
        return "success";
    }

    @RequestMapping("delete")
    public String delete(){
        Item item = itemRepository.queryItemById("1");
        itemRepository.delete(item);
        return "success";
    }

    /**
     * 局部更新
     * @return
     */
    @RequestMapping("update")
    public String update() {
        Item item = itemRepository.queryItemById("1");
        item.setBand("bigbang");
        itemRepository.save(item);
        System.err.println("update a obj");
        return "success";
    }
    /**
     * 查询
     * @return
     */
    @RequestMapping("query")
    public Item query() {
        Item item = itemRepository.queryItemById("1");
        return item;
    }
}
