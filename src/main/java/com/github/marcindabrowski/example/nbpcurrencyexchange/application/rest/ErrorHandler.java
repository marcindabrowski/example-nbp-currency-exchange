package com.github.marcindabrowski.example.nbpcurrencyexchange.application.rest;

import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.error.AccountNotFoundException;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.error.ExchangeRateNotFoundException;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.error.NbpExchangeRateException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Order(HIGHEST_PRECEDENCE)
public class ErrorHandler {

    @ExceptionHandler({AccountNotFoundException.class, ExchangeRateNotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    public @ResponseBody Map<String, Object> handleOrderControlNotFoundException(NbpExchangeRateException exception) {
        return Map.of("errorMessage", exception.getMessage());
    }
}
