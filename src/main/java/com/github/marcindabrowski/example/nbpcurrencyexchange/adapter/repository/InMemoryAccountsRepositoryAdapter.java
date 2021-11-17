package com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.repository;

import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountInfo;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountNumber;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountOwner;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.repository.AccountsRepository;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryAccountsRepositoryAdapter implements AccountsRepository {
    private final Map<String, AccountInfo> repository;

    public InMemoryAccountsRepositoryAdapter(@NonNull List<AccountInfo> accountConfigList) {
        this.repository = accountConfigList.stream().collect(Collectors.toMap(this::getAccountKey, account -> account));
    }

    @Override
    public Optional<AccountInfo> getAccountInfo(AccountOwner accountOwner, AccountNumber accountNumber) {
        return Optional.ofNullable(repository.get(getAccountKey(accountOwner, accountNumber)));
    }

    private String getAccountKey(AccountInfo account) {
        return account.owner().toString() + account.number().toString();
    }

    private String getAccountKey(AccountOwner owner, AccountNumber accountNumber) {
        return owner.toString() + accountNumber.toString();
    }
}
