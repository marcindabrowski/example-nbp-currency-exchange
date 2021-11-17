package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.service

import com.github.marcindabrowski.example.nbpcurrencyexchange.BaseUnitTest
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.error.AccountNotFoundException
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.error.ExchangeRateNotFoundException
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountBalanceExchangeInfo
import spock.lang.Unroll

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
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.EURO_EXCHANGE_RATE
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.EURO_EXCHANGE_RATE_DATE

class AccountServiceSpeck extends BaseUnitTest {
    @Unroll
    def "test checkAccountBalanceExchangeInfo - #testCaseName"() {
        when:
            AccountBalanceExchangeInfo accountBalanceExchangeInfo = accountService.checkAccountBalanceExchangeInfo(accountOwner, accountNumber)

        then:
            accountBalanceExchangeInfo.account().owner() == accountOwner
            accountBalanceExchangeInfo.account().number() == accountNumber
            accountBalanceExchangeInfo.currencyRate().rateDate() == EURO_EXCHANGE_RATE_DATE
            accountBalanceExchangeInfo.currencyRate().averageRate() == EURO_EXCHANGE_RATE
            accountBalanceExchangeInfo.balanceAfterExchange() == balanceAfterExchange

        where:
            testCaseName                       | accountOwner     | accountNumber                      | balanceAfterExchange
            "account owner 01 with account 01" | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01 | BigDecimal.valueOf(466.14)
            "account owner 01 with account 02" | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_01_ACCOUNT_NUMBER_02 | BigDecimal.valueOf(573.35)
            "account owner 02 with account 01" | ACCOUNT_OWNER_02 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01 | BigDecimal.ZERO
            "account owner 03 with account 01" | ACCOUNT_OWNER_03 | ACCOUNT_OWNER_03_ACCOUNT_NUMBER_01 | BigDecimal.ZERO
            "account owner 04 with account 01" | ACCOUNT_OWNER_04 | ACCOUNT_OWNER_04_ACCOUNT_NUMBER_01 | BigDecimal.valueOf(18.65)
    }

    @Unroll
    def "test checkAccountBalanceExchangeInfo - unknown account - #testCaseName"() {
        when:
            accountService.checkAccountBalanceExchangeInfo(accountOwner, accountNumber)

        then:
            AccountNotFoundException exception = thrown AccountNotFoundException.class
            exception.message == String.format("Account owner: %s, iban: %s not found.", accountOwner.ownerUniqueId(), accountNumber.ibanNumber())

        where:
            testCaseName                                                  | accountOwner     | accountNumber
            "account owner 01 with not his account number"                | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01
            "account owner 02 with non existing account number"           | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_02
            "non existing account owner with not his account number"      | ACCOUNT_OWNER_05 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01
            "non existing account owner with not existing account number" | ACCOUNT_OWNER_05 | ACCOUNT_OWNER_05_ACCOUNT_NUMBER_01
    }

    def "test checkAccountBalanceExchangeInfo - exchange rate is unknown"() {
        setup:
            nbpExchangeRateAdapter.setReturnNullExchangeRate()

        when:
            accountService.checkAccountBalanceExchangeInfo(ACCOUNT_OWNER_01, ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01)

        then:
            ExchangeRateNotFoundException exception = thrown ExchangeRateNotFoundException.class
            exception.message == "Euro exchange rate not found."
    }
}
