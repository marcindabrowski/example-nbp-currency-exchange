package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.repository;

import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountInfo;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountNumber;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountOwner;

import java.util.Optional;

public interface AccountsRepository {

    Optional<AccountInfo> getAccountInfo(AccountOwner accountOwner, AccountNumber accountNumber);
}
