package com.oneil.insurance.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class ContentSecurityPolicyFilter extends OncePerRequestFilter {


    @Value("${csp.header.value:default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://cdnjs.cloudflare.com; style-src 'self' 'unsafe-inline' https://cdnjs.cloudflare.com; img-src 'self' data: https://cdnjs.cloudflare.com; font-src 'self' https://cdnjs.cloudflare.com; connect-src 'self' https://api.trusted-source.com; frame-src 'self'; object-src 'none'; form-action 'self'; base-uri 'self'; upgrade-insecure-requests}")
    private String cspHeaderValue;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        log.debug("Applying Content Security Policy (CSP) header to response");

        // Set the CSP header
        response.setHeader("Content-Security-Policy", cspHeaderValue);

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}