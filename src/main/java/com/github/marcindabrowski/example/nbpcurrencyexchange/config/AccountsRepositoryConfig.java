package com.github.marcindabrowski.example.nbpcurrencyexchange.config;

import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountInfo;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountNumber;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountOwner;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;
import java.util.List;

@ConfigurationProperties(prefix = "accounts-config")
public record AccountsRepositoryConfig(List<AccountConfig> accountConfigList) {

    public List<AccountInfo> getAccountsLists() {
        return this.accountConfigList.stream().map(AccountConfig::toAccount).toList();
    }

    public static record AccountConfig(String owner, String iban, BigDecimal balance) {

        private AccountInfo toAccount() {
            return new AccountInfo(new AccountOwner(owner), new AccountNumber(iban), balance);
        }
    }
}
