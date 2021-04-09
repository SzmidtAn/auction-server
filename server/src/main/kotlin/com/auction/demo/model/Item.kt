package com.auction.demo.model

import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.GenerationType

import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var name: String? = null
    var description: String? = null
    var category: String? = null
    var owner: String? = null
    var image: String? = null
    var numOfOffers: Int? = 0
    var payment: String? = null
    var bestOffer: String? = "Na razie nie ma żadnych ofert"
    var timeLeft: Int? = 0
    var currentPrice = 0.0


}