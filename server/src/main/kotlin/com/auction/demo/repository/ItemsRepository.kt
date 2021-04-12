package com.auction.demo.repository

import com.auction.demo.model.Item
import com.auction.demo.model.User

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface ItemsRepository : JpaRepository<Item?, Long?> {

}