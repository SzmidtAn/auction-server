package com.auction.demo.model

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
    var currentPrice = 0.0

    internal constructor() {}
    constructor(name: String?, description: String?, currentPrice: Double) {
        this.name = name
        this.description = description
        this.currentPrice = currentPrice
    }
}