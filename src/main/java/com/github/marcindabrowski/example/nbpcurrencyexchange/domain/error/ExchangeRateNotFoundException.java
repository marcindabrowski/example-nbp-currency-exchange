package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.error;

public final class ExchangeRateNotFoundException extends NbpExchangeRateException {
    public ExchangeRateNotFoundException(String message) {
        super(message);
    }
}
