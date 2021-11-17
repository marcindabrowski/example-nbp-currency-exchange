package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.service

import com.github.marcindabrowski.example.nbpcurrencyexchange.BaseUnitTest
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.CurrencyRate

class NbpExchangeRateSpec extends BaseUnitTest {

    def "should return Euro exchange rate"() {
        setup:
            nbpExchangeRateAdapter.setReturnDefaultExchangeRate()

        when:
            Optional<CurrencyRate> rate = nbpExchangeRateAdapter.getEuroExchangeRate()

        then:
            !rate.isEmpty()
    }

    def "should not return Euro exchange rate"() {
        setup:
            nbpExchangeRateAdapter.setReturnNullExchangeRate()

        when:
            Optional<CurrencyRate> rate = nbpExchangeRateAdapter.getEuroExchangeRate()

        then:
            rate.isEmpty()
    }
}
