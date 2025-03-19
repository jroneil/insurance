package com.oneil.insurance.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneil.insurance.model.Policy;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}