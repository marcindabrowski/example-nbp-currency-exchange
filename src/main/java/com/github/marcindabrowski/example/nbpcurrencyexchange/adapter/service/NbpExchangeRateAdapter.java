package com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.service;

import com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.rest.nbp.NbpExchangeRateClient;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.CurrencyRate;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.service.NbpExchangeRate;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class NbpExchangeRateAdapter implements NbpExchangeRate {
    private static final String EURO_RATE_TABLE = "a";
    private static final String EURO_RATE_CURRENCY_CODE = "eur";

    private final NbpExchangeRateClient nbpExchangeRateClient;

    @Override
    public Optional<CurrencyRate> getEuroExchangeRate() {
        try {
            val rate = nbpExchangeRateClient.getCurrentExchangeRate(EURO_RATE_TABLE, EURO_RATE_CURRENCY_CODE);
            if (!rate.rates().isEmpty()) {
                val nbpRateDto = rate.rates().get(0);
                return Optional.of(new CurrencyRate(nbpRateDto.effectiveDate(), nbpRateDto.mid()));
            }
        } catch (FeignException fe) {
            log.error("Error during calling NBP API", fe);
        }
        return Optional.empty();
    }
}
