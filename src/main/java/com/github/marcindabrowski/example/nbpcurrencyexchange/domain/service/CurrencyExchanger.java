package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.service;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

class CurrencyExchanger {
    private static final int RESULT_PRECISION = 2;

    public BigDecimal getAccountBalanceAfterExchange(BigDecimal accountBalance, BigDecimal exchangeRate) {
        if (accountBalance.compareTo(BigDecimal.ZERO) > 0) {
            return accountBalance.multiply(exchangeRate).setScale(RESULT_PRECISION, HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
