package com.auction.demo.model

import javax.persistence.Entity
import javax.persistence.GenerationType

import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
class Ser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var name: String? = null
    var producer: String? = null
    var price = 0.0

    internal constructor() {}
    constructor(name: String?, producer: String?, price: Double) {
        this.name = name
        this.producer = producer
        this.price = price
    }
}