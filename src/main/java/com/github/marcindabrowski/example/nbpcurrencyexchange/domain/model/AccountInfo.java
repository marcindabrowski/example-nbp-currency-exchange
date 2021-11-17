package com.github.marcindabrowski.example.nbpcurrencyexchange.domain.model;

import lombok.NonNull;

import java.math.BigDecimal;

public record AccountInfo(@NonNull AccountOwner owner, @NonNull AccountNumber number, @NonNull BigDecimal balance) {
}
