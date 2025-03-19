package com.oneil.insurance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oneil.insurance.model.Policy;
import com.oneil.insurance.model.Quote;
import com.oneil.insurance.service.PolicyService;
import com.oneil.insurance.service.QuoteService;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @Autowired
    private QuoteService quoteService;

    @PostMapping("/create")
    public String createPolicy(@RequestBody Policy policy, @RequestParam("baseAmount") double baseAmount) {
        policyService.createPolicy(policy, baseAmount);
        return "Policy created successfully!";
    }

    @PutMapping("/update")
    public String updatePolicy(@RequestBody Policy policy, @RequestParam("baseAmount") double baseAmount) {
        policyService.updatePolicy(policy, baseAmount);
        return "Policy updated successfully!";
    }

    @PostMapping("/quotes/generate")
    public Quote generateQuote(@RequestParam("policyType") String policyType, @RequestParam("policyHolder") String policyHolder, @RequestParam("baseAmount") double baseAmount) {
        return quoteService.generateQuote(policyType, policyHolder, baseAmount);
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