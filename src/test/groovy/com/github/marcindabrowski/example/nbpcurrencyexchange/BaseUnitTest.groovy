package com.github.marcindabrowski.example.nbpcurrencyexchange

import com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.repository.InMemoryAccountsRepositoryAdapter
import com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.rest.nbp.FakeNbpExchangeRateAdapter
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.service.AccountService
import spock.lang.Specification

import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_CONFIG_LIST

abstract class BaseUnitTest extends Specification {

    InMemoryAccountsRepositoryAdapter accountsRepositoryAdapter = new InMemoryAccountsRepositoryAdapter(ACCOUNT_CONFIG_LIST)
    FakeNbpExchangeRateAdapter nbpExchangeRateAdapter = new FakeNbpExchangeRateAdapter()
    AccountService accountService = new AccountService(accountsRepositoryAdapter, nbpExchangeRateAdapter)

    void cleanup() {
        nbpExchangeRateAdapter.setReturnDefaultExchangeRate()
    }
}
