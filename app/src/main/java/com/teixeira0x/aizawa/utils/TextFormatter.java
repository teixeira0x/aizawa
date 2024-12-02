package com.teixeira0x.aizawa.utils;

/** Utility class for formatting text. */
public class TextFormatter {
  /**
   * Formats text as bold.
   *
   * @param text the text to format
   * @return the formatted text
   */
  public static String bold(String text) {
    return "**" + text + "**";
  }

  /**
   * Formats text as italic.
   *
   * @param text the text to format
   * @return the formatted text
   */
  public static String italic(String text) {
    return "*" + text + "*";
  }

  /**
   * Formats text as underlined.
   *
   * @param text the text to format
   * @return the formatted text
   */
  public static String underline(String text) {
    return "__" + text + "__";
  }

  /**
   * Formats text as strikethrough.
   *
   * @param text the text to format
   * @return the formatted text
   */
  public static String strikethrough(String text) {
    return "~~" + text + "~~";
  }

  /**
   * Formats text as a block of code.
   *
   * @param text the code to format
   * @return the formatted text
   */
  public static String codeBlock(String text) {
    return "```\n" + text + "\n```";
  }

  /**
   * Formats text as inline code.
   *
   * @param text the code to format
   * @return the formatted text
   */
  public static String inlineCode(String text) {
    return "`" + text + "`";
  }

  /**
   * Formats text as a spoiler.
   *
   * @param text the text to format
   * @return the formatted text
   */
  public static String spoiler(String text) {
    return "||" + text + "||";
  }

  /** Private constructor */
  private TextFormatter() {}
}
