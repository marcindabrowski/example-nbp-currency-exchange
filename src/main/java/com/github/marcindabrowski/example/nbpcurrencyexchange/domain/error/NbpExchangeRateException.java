package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.error;

public abstract sealed class NbpExchangeRateException
        extends RuntimeException
        permits AccountNotFoundException, ExchangeRateNotFoundException {
    protected NbpExchangeRateException(String message) {
        super(message);
    }
}
