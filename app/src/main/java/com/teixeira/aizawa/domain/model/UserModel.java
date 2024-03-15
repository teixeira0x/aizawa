package com.teixeira.aizawa.domain.model;

import java.math.BigDecimal;

public record UserModel(long id, BigDecimal balance, BigDecimal bankBalance) {}
