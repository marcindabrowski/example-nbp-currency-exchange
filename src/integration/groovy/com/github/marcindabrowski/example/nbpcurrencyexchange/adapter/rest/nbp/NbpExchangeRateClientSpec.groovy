package com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.rest.nbp

import com.github.marcindabrowski.example.nbpcurrencyexchange.BaseIntegrationTest
import feign.FeignException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class NbpExchangeRateClientSpec extends BaseIntegrationTest {

    @Autowired
    NbpExchangeRateClient nbpExchangeRateClient

    def "should get rate of Euro in table A"() {
        when: "ask for Euro in table A"
            NbpExchangeRatesDto rate = nbpExchangeRateClient.getCurrentExchangeRate("a", "EUR")

        then: "get current Euro rate in table A"
            rate.table() == "A"
            rate.currency() == "euro"
            rate.code() == "EUR"
            rate.rates().size() == 1
            rate.rates()[0] != null
    }

    def "should receive 400 Bad Request when get rate of Euro in table D"() {
        when: "ask for Euro in table D"
            nbpExchangeRateClient.getCurrentExchangeRate("D", "EUR")

        then: "get 400 Bad Request"
            FeignException.BadRequest exception = thrown FeignException.BadRequest.class
            exception.status() == HttpStatus.BAD_REQUEST.value()
    }

    def "should receive 404 Not Found when get rate of XXX in table A"() {
        when: "ask for XXX in table A"
            nbpExchangeRateClient.getCurrentExchangeRate("A", "XXX")

        then: "get 404 Not Found"
            FeignException.NotFound exception = thrown FeignException.NotFound.class
            exception.status() == HttpStatus.NOT_FOUND.value()
    }
}
