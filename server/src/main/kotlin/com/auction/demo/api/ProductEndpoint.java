package com.auction.demo.api;


import com.auction.demo.model.Ser;
import com.auction.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductEndpoint {

    private ProductRepository bookRepository;

    @Autowired
    public ProductEndpoint(ProductRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/api/ser")
    public List<Ser> getAll() {
        return bookRepository.findAll();
    }



    @PostMapping("/api/sery")
    public ResponseEntity<?> save(@RequestBody Ser book) {
        if(book.getId() == null) {
            Ser saved = bookRepository.save(book);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();
            return ResponseEntity.created(location).body(book);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}