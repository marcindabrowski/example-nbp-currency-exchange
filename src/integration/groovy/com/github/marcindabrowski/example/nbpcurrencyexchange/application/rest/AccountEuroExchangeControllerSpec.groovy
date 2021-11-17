package com.github.marcindabrowski.example.nbpcurrencyexchange.application.rest

import com.github.marcindabrowski.example.nbpcurrencyexchange.BaseIntegrationTest
import com.github.marcindabrowski.example.nbpcurrencyexchange.infrastructure.ability.RestApiAbility
import org.springframework.http.ResponseEntity
import spock.lang.Unroll

import java.time.LocalDate

import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_01_ACCOUNT_NUMBER_02
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_02
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_02_ACCOUNT_NUMBER_02
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_03
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_03_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_04
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_04_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_05
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_05_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.DEFAULT_ZONE_ID
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

class AccountEuroExchangeControllerSpec extends BaseIntegrationTest implements RestApiAbility {

    @Unroll
    def "test getAccountEuroBalanceExchangeInfo REST API endpoint - #testCaseName"() {
        given:
            LocalDate today = LocalDate.now(DEFAULT_ZONE_ID)

        when: "call getAccountEuroBalanceExchangeInfo REST API endpoint"
            ResponseEntity<Map> responseEntity = getAccountEuroBalanceExchangeInfo(accountOwner.ownerUniqueId(), accountNumber.ibanNumber())

        then: "the account balance in Euro is returned"
            AccountEuroBalanceExchangeInfoResponseAssertion.assertThat(responseEntity) {
                hasStatusCode(OK)
                hasBalanceInEuro(balanceAfterExchange)
                hasExchangeRateDate(today)
            }

        where:
            testCaseName                       | accountOwner     | accountNumber                      | balanceAfterExchange
            "account owner 01 with account 01" | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01 | BigDecimal.valueOf(466.14)
            "account owner 01 with account 02" | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_01_ACCOUNT_NUMBER_02 | BigDecimal.valueOf(573.35)
            "account owner 02 with account 01" | ACCOUNT_OWNER_02 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01 | BigDecimal.ZERO
            "account owner 03 with account 01" | ACCOUNT_OWNER_03 | ACCOUNT_OWNER_03_ACCOUNT_NUMBER_01 | BigDecimal.ZERO
            "account owner 04 with account 01" | ACCOUNT_OWNER_04 | ACCOUNT_OWNER_04_ACCOUNT_NUMBER_01 | BigDecimal.valueOf(18.65)
    }

    @Unroll
    def "test getAccountEuroBalanceExchangeInfo REST API endpoint - unknown account - #testCaseName"() {
        when:
            ResponseEntity<Map> responseEntity = getAccountEuroBalanceExchangeInfo(accountOwner.ownerUniqueId(), accountNumber.ibanNumber())

        then:
            AccountEuroBalanceExchangeInfoResponseAssertion.assertThat(responseEntity) {
                hasStatusCode(NOT_FOUND)
                hasErrorMessage(String.format("Account owner: %s, iban: %s not found.", accountOwner.ownerUniqueId(), accountNumber.ibanNumber()))
            }

        where:
            testCaseName                                                  | accountOwner     | accountNumber
            "account owner 01 with not his account number"                | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01
            "account owner 02 with non existing account number"           | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_02
            "non existing account owner with not his account number"      | ACCOUNT_OWNER_05 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01
            "non existing account owner with not existing account number" | ACCOUNT_OWNER_05 | ACCOUNT_OWNER_05_ACCOUNT_NUMBER_01
    }
}
