package com.teixeira.aizawa.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BalanceUtils {
  private static final DecimalFormat balanceFormat = new DecimalFormat("#,###");

  public static String formatBalance(int balance) {
    return balanceFormat.format(balance);
  }

  public static String formatBalance(BigDecimal balance) {
    return balanceFormat.format(balance);
  }
}
