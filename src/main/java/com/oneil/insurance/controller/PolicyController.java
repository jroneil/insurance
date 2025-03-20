package com.oneil.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.oneil.insurance.model.Policy;
import com.oneil.insurance.model.Quote;
import com.oneil.insurance.service.PolicyService;
import com.oneil.insurance.service.QuoteService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/v1/policies")
@Validated
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @Autowired
    private QuoteService quoteService;

    @PostMapping("/create")
    public String createPolicy(@Valid @RequestBody Policy policy, @RequestParam("baseAmount") double baseAmount) {
        policyService.createPolicy(policy, baseAmount);
        return "Policy created successfully!";
    }

    @PutMapping("/update")
    public String updatePolicy(@RequestBody Policy policy, @RequestParam("baseAmount") double baseAmount) {
        policyService.updatePolicy(policy, baseAmount);
        return "Policy updated successfully!";
    }

    @PostMapping("/quotes/generate")
    public ResponseEntity<?> generateQuote(
            @RequestParam("policyType") @NotBlank String policyType,
            @RequestParam("policyHolder") @NotBlank String policyHolder,
            @RequestParam("baseAmount") @Positive double baseAmount) {

        if (policyType.isBlank() || policyHolder.isBlank()||baseAmount<0) {
            return ResponseEntity.badRequest().body("policyType and policyHolder must not be blank");
        }

        return ResponseEntity.ok(quoteService.generateQuote(policyType, policyHolder, baseAmount));
    }

    @GetMapping("/quotes/{id}")
    public ResponseEntity<?> getQuoteById(@PathVariable("id") Long id) {
        try {
            Quote quote = quoteService.getQuoteById(id);
            return ResponseEntity.ok(quote);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
