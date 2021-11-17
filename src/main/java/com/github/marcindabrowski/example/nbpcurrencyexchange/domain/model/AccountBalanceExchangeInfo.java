package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model;

import java.math.BigDecimal;

public record AccountBalanceExchangeInfo(AccountInfo account, CurrencyRate currencyRate, BigDecimal balanceAfterExchange) {
}
