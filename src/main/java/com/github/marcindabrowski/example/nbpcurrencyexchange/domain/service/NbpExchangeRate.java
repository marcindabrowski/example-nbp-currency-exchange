package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.service;

import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.CurrencyRate;

import java.util.Optional;

public interface NbpExchangeRate {
    Optional<CurrencyRate> getEuroExchangeRate();
}
