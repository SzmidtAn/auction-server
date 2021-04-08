package com.auction.demo.api

import com.auction.demo.model.Item
import com.auction.demo.repository.ItemsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*


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

    @GetMapping("/items/{id}")
    fun getTutorialById(@PathVariable("id") id: Long): ResponseEntity<Any?> {
        val tutorialData: Optional<Item?> = itemsRepository.findById(id)
        return if (tutorialData.isPresent) {
            ResponseEntity<Any?>(tutorialData.get(), HttpStatus.OK)
        } else {
            ResponseEntity<Any?>(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/items/{id}")
    fun updateTutorial(@PathVariable("id") id: Long, @RequestBody newItem: Item): ResponseEntity<Any?> {
        val tutorialData: Optional<Item?> = itemsRepository.findById(id)
        return if (tutorialData.isPresent) {
            val item: Item = tutorialData.get()
            item.currentPrice = newItem.currentPrice
            ResponseEntity<Any?>(itemsRepository.save(item), HttpStatus.OK)
        } else {
            ResponseEntity<Any?>(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/items/{id}")
    fun deleteTutorial(@PathVariable("id") id: Long): ResponseEntity<HttpStatus?>? {
        return try {
            itemsRepository.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/items")
    fun deleteAllTutorials(): ResponseEntity<HttpStatus?>? {
        return try {
            itemsRepository.deleteAll()
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: java.lang.Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

}