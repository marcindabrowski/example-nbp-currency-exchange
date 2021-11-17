package com.github.marcindabrowski.example.nbpcurrencyexchange.application.rest

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import java.time.LocalDate

class AccountEuroBalanceExchangeInfoResponseAssertion {

    static class AccountEuroBalanceExchangeInfoResponseAssertionBuilder {
        private final ResponseEntity<Map> response

        AccountEuroBalanceExchangeInfoResponseAssertionBuilder(ResponseEntity<Map> response) {
            this.response = response
        }

        void hasStatusCode(HttpStatus httpStatus) {
            assert response.statusCode == httpStatus
        }

        void hasErrorMessage(String errorMessage) {
            assert response.body.errorMessage == errorMessage
        }

        void hasBalanceInEuro(BigDecimal balanceInEuro) {
            assert response.body.balanceInEuro == balanceInEuro
        }

        void hasExchangeRateDate(LocalDate exchangeRateDate) {
            assert LocalDate.parse(response.body.exchangeRateDate as String) <= exchangeRateDate
        }
    }

    static void assertThat(ResponseEntity<Map> response, @DelegatesTo(AccountEuroBalanceExchangeInfoResponseAssertionBuilder) Closure closure) {
        new AccountEuroBalanceExchangeInfoResponseAssertionBuilder(response).with(closure)
    }
}
