package com.github.marcindabrowski.example.nbpcurrencyexchange.adapter.rest.nbp;

import java.util.List;

public record NbpExchangeRatesDto(String table, String currency, String code, List<NbpRateDto> rates) {
}
