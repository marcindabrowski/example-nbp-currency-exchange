package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CurrencyRate(LocalDate rateDate, BigDecimal averageRate) {
}
