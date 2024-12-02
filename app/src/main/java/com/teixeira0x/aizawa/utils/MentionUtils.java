package com.teixeira0x.aizawa.utils;

public class MentionUtils {
  public static String commandMention(String commandId, String commandName) {
    return "</" + commandName + ":" + commandId + ">";
  }
}
