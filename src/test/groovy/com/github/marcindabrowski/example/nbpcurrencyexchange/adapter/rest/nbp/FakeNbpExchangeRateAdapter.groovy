package com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.rest.nbp

import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.CurrencyRate
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.service.NbpExchangeRate

import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.EURO_CURRENCY_RATE
import static com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.rest.nbp.FakeNbpExchangeRateAdapter.NbpExchangeRateAdapterBehavior.RETURN_DEFAULT_EXCHANGE_RATE
import static com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.rest.nbp.FakeNbpExchangeRateAdapter.NbpExchangeRateAdapterBehavior.RETURN_NULL

class FakeNbpExchangeRateAdapter implements NbpExchangeRate {
    private static final Optional<CurrencyRate> DEFAULT_EXCHANGE_RATE = Optional.of(EURO_CURRENCY_RATE)
    private NbpExchangeRateAdapterBehavior behavior = RETURN_DEFAULT_EXCHANGE_RATE

    void setReturnDefaultExchangeRate() {
        this.behavior = RETURN_DEFAULT_EXCHANGE_RATE
    }

    void setReturnNullExchangeRate() {
        this.behavior = RETURN_NULL
    }

    @Override
    Optional<CurrencyRate> getEuroExchangeRate() {
        return behavior.getEuroExchangeRate()
    }

    static enum NbpExchangeRateAdapterBehavior {
        RETURN_DEFAULT_EXCHANGE_RATE{
            Optional<CurrencyRate> getEuroExchangeRate() {
                return DEFAULT_EXCHANGE_RATE
            }
        },
        RETURN_NULL{
            Optional<CurrencyRate> getEuroExchangeRate() {
                return Optional.empty()
            }
        }

        abstract Optional<CurrencyRate> getEuroExchangeRate()
    }
}
