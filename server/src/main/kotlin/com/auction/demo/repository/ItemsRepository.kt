package com.auction.demo.repository

import com.auction.demo.model.Item

import org.springframework.data.jpa.repository.JpaRepository


public interface ItemsRepository : JpaRepository<Item?, Long?>