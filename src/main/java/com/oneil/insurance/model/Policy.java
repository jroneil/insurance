package com.oneil.insurance.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Policy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double premium;

	@NotBlank(message = "Policy type is required")
	@Size(max = 50, message = "Policy type must be less than 50 characters")
	private String policyType;

	@NotBlank(message = "Policy holder is required")
	@Size(max = 100, message = "Policy holder must be less than 100 characters")
	private String policyHolder;

	@NotNull(message = "Start date is required")
	private LocalDate startDate;

	@NotNull(message = "End date is required")
	private LocalDate endDate;

}