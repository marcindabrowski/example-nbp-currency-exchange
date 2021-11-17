package com.github.marcindabrowski.example.nbpcurrencyexchange.application.rest;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountEuroBalanceExchangeInfoResponse(BigDecimal balanceInEuro, LocalDate exchangeRateDate) {
}
