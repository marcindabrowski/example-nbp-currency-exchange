package com.github.marcindabrowski.example.nbpcurrencyexchange.infrastructure.ability

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

trait RestApiAbility {
    @Autowired
    TestRestTemplate restTemplate

    ResponseEntity<Map> getAccountEuroBalanceExchangeInfo(String ownerUniqueId, String ibanNumber) {
        return restTemplate.getForEntity("/accounts/$ownerUniqueId/$ibanNumber/euroBalanceExchangeInfo", Map)
    }
}
