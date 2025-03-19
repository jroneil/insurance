package com.oneil.insurance.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneil.insurance.model.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    // Custom query method to find the most recent quote
    Quote findTopByOrderByIdDesc();
}