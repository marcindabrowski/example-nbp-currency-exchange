package com.github.marcindabrowski.example.nbpcurrencyexchange.application.rest;

import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountNumber;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model.AccountOwner;
import com.github.marcindabrowski.example.nbpcurrencyexchange.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
class AccountEuroExchangeController {
    private final AccountService accountService;

    @GetMapping(value = "/{ownerUniqueId}/{ibanNumber}/euroBalanceExchangeInfo", produces = APPLICATION_JSON_VALUE)
    public AccountEuroBalanceExchangeInfoResponse getAccountEuroBalanceExchangeInfo(
            @PathVariable("ownerUniqueId") String ownerUniqueId,
            @PathVariable("ibanNumber") String ibanNumber) {
        val accountBalanceExchangeInfo = accountService
                .checkAccountBalanceExchangeInfo(new AccountOwner(ownerUniqueId), new AccountNumber(ibanNumber));
        return new AccountEuroBalanceExchangeInfoResponse(
                accountBalanceExchangeInfo.balanceAfterExchange(),
                accountBalanceExchangeInfo.currencyRate().rateDate());
    }
}
