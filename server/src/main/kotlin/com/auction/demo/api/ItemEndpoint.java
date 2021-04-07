package com.auction.demo.api;


import com.auction.demo.model.Item;
import com.auction.demo.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ItemEndpoint {

    private ItemsRepository itemsRepository;

    @Autowired
    public ItemEndpoint(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @GetMapping("/api/items")
    public List<Item> getAll() {
        return itemsRepository.findAll();
    }



    @PostMapping("/api/items")
    public ResponseEntity<?> save(@RequestBody Item item) {
        if(item.getId() == null) {
            Item saved = itemsRepository.save(item);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();
            return ResponseEntity.created(location).body(item);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}