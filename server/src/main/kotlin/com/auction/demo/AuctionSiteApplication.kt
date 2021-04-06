package com.auction.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuctionSiteApplication

fun main(args: Array<String>) {
    runApplication<AuctionSiteApplication>(*args)
}
