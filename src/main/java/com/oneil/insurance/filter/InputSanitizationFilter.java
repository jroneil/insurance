package com.oneil.insurance.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class InputSanitizationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(InputSanitizationFilter.class);
    private static final Pattern GENERAL_SANITIZATION_PATTERN = Pattern.compile("[^\\p{L}\\p{N}._\\s-]");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\p{L}\\p{N}.!#$%&'*+/=?^_`{|}~-]+@[\\p{L}\\p{N}.-]+\\.[\\p{L}]{2,6}$");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Sanitize request parameters
        Map<String, String[]> sanitizedParams = request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> sanitizeValues(entry.getValue(), entry.getKey()))
                );

        // Wrap the request with sanitized parameters
        HttpServletRequest sanitizedRequest = new HttpServletRequestWrapper(request) {
            @Override
            public Map<String, String[]> getParameterMap() {
                return sanitizedParams;
            }

            @Override
            public String[] getParameterValues(String name) {
                return sanitizedParams.get(name);
            }

            @Override
            public String getParameter(String name) {
                String[] values = sanitizedParams.get(name);
                return values != null && values.length > 0 ? values[0] : null;
            }
        };

        // Continue the filter chain with the sanitized request
        filterChain.doFilter(sanitizedRequest, response);
    }

    private String[] sanitizeValues(String[] values, String parameterName) {
        return values == null ? null : Arrays.stream(values)
                .map(value -> sanitizeInput(value, parameterName))
                .toArray(String[]::new);
    }

    private String sanitizeInput(String input, String parameterName) {
        if (input == null) {
            return null;
        }

        String sanitizedInput;
        if (parameterName.equalsIgnoreCase("email")) {
            if (!EMAIL_PATTERN.matcher(input).matches()) {
                log.warn("Invalid email format detected. Returning empty string");
                return "";
            }
            sanitizedInput = input;
        } else {
            sanitizedInput = GENERAL_SANITIZATION_PATTERN.matcher(input).replaceAll("");
            sanitizedInput = StringEscapeUtils.escapeHtml4(sanitizedInput);
        }

        return sanitizedInput;
    }
}