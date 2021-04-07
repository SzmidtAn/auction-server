package com.auction.demo.api

import com.auction.demo.model.Item
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired
import com.auction.demo.repository.ItemsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.http.HttpStatus

@RestController
class ItemEndpoint @Autowired constructor(private val itemsRepository: ItemsRepository) {
    @get:GetMapping("/api/items")
    val all: List<Item?>
        get() = itemsRepository.findAll()

    @PostMapping("/api/items")
    fun save(@RequestBody item: Item): ResponseEntity<*> {
        return if (item.id == null) {
            val saved = itemsRepository.save(item)
            val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id)
                .toUri()
            ResponseEntity.created(location).body(item)
        } else {
            ResponseEntity.status(HttpStatus.CONFLICT).build<Any>()
        }
    }
}