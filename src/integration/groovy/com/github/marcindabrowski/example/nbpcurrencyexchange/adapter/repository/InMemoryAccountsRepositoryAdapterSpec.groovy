package com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.repository

import com.github.marcindabrowski.example.nbpcurrencyexchange.BaseIntegrationTest
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountInfo
import org.springframework.beans.factory.annotation.Autowired

import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_02_ACCOUNT_NUMBER_02
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_05
import static com.github.marcindabrowski.example.nbpcurrencyexchange.TestData.ACCOUNT_OWNER_05_ACCOUNT_NUMBER_01

class InMemoryAccountsRepositoryAdapterSpec extends BaseIntegrationTest {
    @Autowired
    InMemoryAccountsRepositoryAdapter inMemoryAccountsRepositoryAdapter

    def "should return information about account - #testCaseName"() {
        when: "ask for account information"
            Optional<AccountInfo> account = inMemoryAccountsRepositoryAdapter.getAccountInfo(accountOwner, accountNumber)

        then: "account should meet given conditions"
            account.isPresent() == exists
            if (exists) {
                account.get().owner() == accountOwner
                account.get().number() == accountNumber
                account.get().balance() == balance
            }

        where:
            testCaseName                                                  | accountOwner     | accountNumber                      | exists | balance
            "account owner 01 with his account number"                    | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_01_ACCOUNT_NUMBER_01 | true   | BigDecimal.valueOf(100)
            "account owner 01 with not his account number"                | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01 | false  | null
            "account owner 02 with non existing account number"           | ACCOUNT_OWNER_01 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_02 | false  | null
            "non existing account owner with not his account number"      | ACCOUNT_OWNER_05 | ACCOUNT_OWNER_02_ACCOUNT_NUMBER_01 | false  | null
            "non existing account owner with not existing account number" | ACCOUNT_OWNER_05 | ACCOUNT_OWNER_05_ACCOUNT_NUMBER_01 | false  | null
    }
}
