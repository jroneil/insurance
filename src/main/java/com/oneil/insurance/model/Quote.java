package com.oneil.insurance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Policy type is required")
    @Size(max = 50, message = "Policy type must be less than 50 characters")
    private String policyType;

    @NotBlank(message = "Policy holder is required")
    @Size(max = 100, message = "Policy holder must be less than 100 characters")
    private String policyHolder;

    @NotNull(message = "Estimated premium is required")
    @PositiveOrZero(message = "Estimated premium must be a positive number or zero")
    private Double estimatedPremium;

    @NotNull(message = "Quote date is required")
    @PastOrPresent(message = "Quote date must be in the past or present")
    private LocalDate quoteDate;
}