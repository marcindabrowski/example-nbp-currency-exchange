package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.error;

public final class AccountNotFoundException extends NbpExchangeRateException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
