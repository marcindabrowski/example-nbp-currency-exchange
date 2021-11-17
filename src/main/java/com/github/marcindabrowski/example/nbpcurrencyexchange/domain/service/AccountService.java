package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.service;

import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.error.AccountNotFoundException;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.error.ExchangeRateNotFoundException;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountBalanceExchangeInfo;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountNumber;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountOwner;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class AccountService {
    private final AccountsRepository accountsRepository;
    private final NbpExchangeRate nbpExchangeRate;
    private final CurrencyExchanger currencyExchanger = new CurrencyExchanger();

    public AccountBalanceExchangeInfo checkAccountBalanceExchangeInfo(AccountOwner accountOwner, AccountNumber accountNumber) {
        val accountInfo = accountsRepository.getAccountInfo(accountOwner, accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account owner: %s, iban: %s not found.",
                        accountOwner.ownerUniqueId(), accountNumber.ibanNumber())));
        val euroExchangeRate = nbpExchangeRate.getEuroExchangeRate()
                .orElseThrow(() -> new ExchangeRateNotFoundException("Euro exchange rate not found."));
        return new AccountBalanceExchangeInfo(accountInfo, euroExchangeRate,
                currencyExchanger.getAccountBalanceAfterExchange(accountInfo.balance(), euroExchangeRate.averageRate()));
    }
}
