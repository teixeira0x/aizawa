package com.teixeira0x.aizawa.localization;

import com.teixeira0x.aizawa.core.strings.StringsManager;
import com.teixeira0x.aizawa.core.strings.file.StringsFileParser;
import com.teixeira0x.aizawa.feature.l10n.parser.LocalizedStringsFileParser;
import java.util.Map;
import java.util.HashMap;
import net.dv8tion.jda.api.interactions.DiscordLocale;

public class LocalizedStringsManager implements StringsManager {

  private static final DiscordLocale[] AVAILABLE_LOCALES = {DiscordLocale.PORTUGUESE_BRAZILIAN};

  private final Map<DiscordLocale, Map<String, String>> localizedStrings = new HashMap<>();
  private final StringsFileParser fileParser;

  public LocalizedStringsManager() {
    this.fileParser = new LocalizedStringsFileParser();
    this.initialize();
  }

  private void initialize() {
    for (DiscordLocale locale : AVAILABLE_LOCALES) {
      localizedStrings.put(
          locale,
          fileParser.parse(
              getClass().getResourceAsStream("/locales/" + locale.getLocale() + ".locale")));
    }
  }

  @Override
  public String getString(String key) {
    return getString(key, null);
  }

  @Override
  public String getString(String key, String defaultValue) {
    return getString(DiscordLocale.PORTUGUESE_BRAZILIAN, key, defaultValue);
  }

  @Override
  public String getString(DiscordLocale locale, String key) {
    return getString(locale, key, null);
  }

  @Override
  public String getString(DiscordLocale locale, String key, String defaultValue) {
    if (!localizedStrings.containsKey(locale)) {
      throw new UnsupportedLocaleException(
          "The location: " + locale.getLocale() + ". is not available.");
    }

    return localizedStrings
        .get(locale)
        .getOrDefault(key, defaultValue != null ? defaultValue : "Key not found: " + key);
  }

  @Override
  public DiscordLocale[] getAvailableLocales() {
    return AVAILABLE_LOCALES;
  }

  public static class UnsupportedLocaleException extends RuntimeException {
    public UnsupportedLocaleException(String message) {
      super(message);
    }
  }
}
