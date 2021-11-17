package com.github.marcindabrowski.example.nbpcurrencyexchange.config;

import com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.repository.InMemoryAccountsRepositoryAdapter;
import com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.rest.nbp.NbpExchangeRateClient;
import com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.service.NbpExchangeRateAdapter;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.repository.AccountsRepository;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.service.AccountService;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.service.NbpExchangeRate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NbpExchangeConfiguration {

    @Bean
    public AccountsRepository accountsRepository(AccountsRepositoryConfig accountsRepositoryConfig) {
        return new InMemoryAccountsRepositoryAdapter(accountsRepositoryConfig.getAccountsLists());
    }

    @Bean
    public NbpExchangeRate nbpExchangeRate(NbpExchangeRateClient nbpExchangeRateClient) {
        return new NbpExchangeRateAdapter(nbpExchangeRateClient);
    }

    @Bean
    public AccountService accountService(AccountsRepository accountsRepository, NbpExchangeRate nbpExchangeRate) {
        return new AccountService(accountsRepository, nbpExchangeRate);
    }
}
