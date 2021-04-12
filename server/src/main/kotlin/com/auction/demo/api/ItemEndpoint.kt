package com.auction.demo.api

import com.auction.demo.model.Item
import com.auction.demo.model.User
import com.auction.demo.repository.ItemsRepository
import com.auction.demo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api")
class ItemEndpoint @Autowired constructor(private val itemsRepository: ItemsRepository) {

    @Autowired
    var userRepository: UserRepository? = null


    @get:GetMapping("/items")
    val all: List<Item?>
        get() = itemsRepository.findAll()

    @PostMapping("/items")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun getTutorialById(@PathVariable("id") id: Long): ResponseEntity<Any?> {
        val tutorialData: Optional<Item?> = itemsRepository.findById(id)
        return if (tutorialData.isPresent) {
            ResponseEntity<Any?>(tutorialData.get(), HttpStatus.OK)
        } else {
            ResponseEntity<Any?>(HttpStatus.NOT_FOUND)
        }
    }


    @GetMapping("//auth/users/{UserId}")
    fun getItemsForUser(@PathVariable("UserId") id: Long): ResponseEntity<Any?> {
        val user: User = userRepository!!.findById(id).get()

        return ResponseEntity<Any?>(user.items, HttpStatus.OK)
    }

    @PutMapping("/items/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun updateTutorial(@PathVariable("id") id: Long, @RequestBody newItem: Item): ResponseEntity<Any?> {
        val tutorialData: Optional<Item?> = itemsRepository.findById(id)
        return if (tutorialData.isPresent) {
            val item: Item = tutorialData.get()
            item.currentPrice = newItem.currentPrice
            item.numOfOffers = item.numOfOffers?.plus(1)
            item.bestOffer = newItem.bestOffer

            val user: User = userRepository?.findByUsername(newItem.bestOffer)!!.get()
            val items: MutableSet<Item> = HashSet()
            items.add(item)

            user.items.add(item)


            ResponseEntity<Any?>(itemsRepository.save(item), HttpStatus.OK)
           ResponseEntity<Any?>(userRepository!!.save(user), HttpStatus.OK)
        } else {
            ResponseEntity<Any?>(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/items/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun deleteTutorial(@PathVariable("id") id: Long): ResponseEntity<HttpStatus?>? {
        return try {
            itemsRepository.deleteById(id)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/items")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun deleteAllTutorials(): ResponseEntity<HttpStatus?>? {
        return try {
            itemsRepository.deleteAll()
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: java.lang.Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }


}