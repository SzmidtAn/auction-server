package com.auction.demo.repository

import com.auction.demo.model.Ser

import org.springframework.data.jpa.repository.JpaRepository


public interface ProductRepository : JpaRepository<Ser?, Long?>