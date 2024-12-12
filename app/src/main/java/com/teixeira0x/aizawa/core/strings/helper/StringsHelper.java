package com.teixeira0x.aizawa.core.strings.helper;

import com.teixeira0x.aizawa.core.strings.StringsManager;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.interactions.DiscordLocale;

public class StringsHelper {
  private final DiscordLocale locale;
  private final StringsManager stringsManager;

  public StringsHelper(DiscordLocale locale, StringsManager stringsManager) {
    this.locale = locale;
    this.stringsManager = stringsManager;
  }

  public DiscordLocale getLocale() {
    return this.locale;
  }

  public StringsManager getStringsManager() {
    return this.stringsManager;
  }

  @Nonnull
  public String getString(@Nonnull String key) {
    return getString(key, null);
  }

  @Nonnull
  public String getString(@Nonnull String key, @Nullable String defaultValue) {
    return stringsManager.getString(locale, key, defaultValue);
  }
}
