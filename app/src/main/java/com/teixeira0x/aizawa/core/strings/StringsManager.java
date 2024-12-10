package com.teixeira0x.aizawa.core.strings;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.interactions.DiscordLocale;

public interface StringsManager {

  @Nonnull
  String getString(@Nonnull String key);

  @Nonnull
  String getString(@Nonnull String key, @Nullable String defaultValue);

  @Nonnull
  String getString(@Nonnull DiscordLocale locale, @Nonnull String key);

  @Nonnull
  String getString(@Nonnull DiscordLocale locale, @Nonnull String key, @Nonnull String defaultValue);

  @Nonnull
  DiscordLocale[] getAvailableLocales();
}
