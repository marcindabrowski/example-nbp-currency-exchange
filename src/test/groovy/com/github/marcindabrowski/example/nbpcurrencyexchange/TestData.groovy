package com.github.marcindabrowski.example.nbpcurrencyexchange

import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountInfo
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountNumber
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountOwner
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.CurrencyRate

import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class TestData {
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of("Europe/Warsaw")
    public static final Instant NOW = Instant.parse("2021-11-17T11:00:00.000Z")
    public static final Clock CLOCK_AT_NOW = Clock.fixed(NOW, DEFAULT_ZONE_ID)
    public static final LocalDate EURO_EXCHANGE_RATE_DATE = LocalDate.now(CLOCK_AT_NOW)
    public static final BigDecimal EURO_EXCHANGE_RATE = BigDecimal.valueOf(4.6614)
    public static final CurrencyRate EURO_CURRENCY_RATE = new CurrencyRate(EURO_EXCHANGE_RATE_DATE, EURO_EXCHANGE_RATE)

    public static final AccountOwner ACCOUNT_OWNER_01 = new AccountOwner("AccountOwner01")
    public static final AccountNumber ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01 = new AccountNumber("iban_ao01_01")
    public static final AccountNumber ACCOUNT_OWNER_01_ACCOUNT_NUMBER_02 = new AccountNumber("iban_ao01_02")

    public static final AccountOwner ACCOUNT_OWNER_02 = new AccountOwner("AccountOwner02")
    public static final AccountNumber ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01 = new AccountNumber("iban_ao02_01")
    public static final AccountNumber ACCOUNT_OWNER_02_ACCOUNT_NUMBER_02 = new AccountNumber("iban_ao02_02")

    public static final AccountOwner ACCOUNT_OWNER_03 = new AccountOwner("AccountOwner03")
    public static final AccountNumber ACCOUNT_OWNER_03_ACCOUNT_NUMBER_01 = new AccountNumber("iban_ao03_01")

    public static final AccountOwner ACCOUNT_OWNER_04 = new AccountOwner("AccountOwner04")
    public static final AccountNumber ACCOUNT_OWNER_04_ACCOUNT_NUMBER_01 = new AccountNumber("iban_ao04_01")

    public static final AccountOwner ACCOUNT_OWNER_05 = new AccountOwner("AccountOwner05")
    public static final AccountNumber ACCOUNT_OWNER_05_ACCOUNT_NUMBER_01 = new AccountNumber("iban_ao05_01")

    public static final List<AccountInfo> ACCOUNT_CONFIG_LIST = [
            new AccountInfo(ACCOUNT_OWNER_01, ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01, BigDecimal.valueOf(100)),
            new AccountInfo(ACCOUNT_OWNER_01, ACCOUNT_OWNER_01_ACCOUNT_NUMBER_02, BigDecimal.valueOf(123)),
            new AccountInfo(ACCOUNT_OWNER_02, ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01, BigDecimal.valueOf(0)),
            new AccountInfo(ACCOUNT_OWNER_03, ACCOUNT_OWNER_03_ACCOUNT_NUMBER_01, BigDecimal.valueOf(-100)),
            new AccountInfo(ACCOUNT_OWNER_04, ACCOUNT_OWNER_04_ACCOUNT_NUMBER_01, BigDecimal.valueOf(4)),
    ].asUnmodifiable()
}
