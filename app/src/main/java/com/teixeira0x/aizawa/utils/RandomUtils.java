package com.teixeira0x.aizawa.utils;

import java.util.List;
import java.util.Random;

/**
 * This class contains additional functions to the "java.util.Random" class.
 *
 * @author Felipe Teixeira.
 */
public class RandomUtils {
  /** The random instance */
  private static final Random random = new Random();

  /**
   * Choose a random value from the list.
   *
   * @param items The list to choose the random value.
   * @return The random value obtained.
   */
  public static <T> T choice(List<T> items) {
    return items.get(nextInt(0, items.size()));
  }

  /**
   * Choose a random value from the array.
   *
   * @param items The array to choose the random value.
   * @return The random value obtained.
   */
  @SuppressWarnings("unchecked")
  public static <T> T choice(Object... items) {
    return (T) items[nextInt(0, items.length)];
  }

  /**
   * Returns a random number within the minimum number and maximum number.
   *
   * @param start The minimum number
   * @param end The maximum number.
   * @return The random number obtained.
   */
  public static int nextInt(int min, int max) {
    return random.nextInt(min, max);
  }

  private RandomUtils() {}
}
