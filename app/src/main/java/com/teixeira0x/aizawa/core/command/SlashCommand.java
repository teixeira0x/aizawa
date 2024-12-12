package com.teixeira0x.aizawa.core.command;

import com.teixeira0x.aizawa.core.strings.StringsManager;
import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public abstract class SlashCommand {
  private final Map<DiscordLocale, String> localizedNames = new HashMap<>();
  private final Map<DiscordLocale, String> localizedDescriptions = new HashMap<>();
  private OptionData[] options = new OptionData[0];
  private SlashCommand[] subcommands = new SlashCommand[0];

  private boolean isSubcommand = false;
  private boolean guildOnly = false;

  protected final StringsManager stringsManager;
  private String name;
  private String description;

  public SlashCommand(StringsManager stringsManager, String name, String description) {
    this.stringsManager = stringsManager;
    this.name = name;
    this.description = description;
  }

  public abstract void execute(SlashCommandInteractionEvent event);

  public final CommandData toCommandData() {
    SubcommandData[] subcommandDataArr = new SubcommandData[subcommands.length];

    for (int i = 0; i < subcommands.length; i++) {
      SlashCommand subcommand = subcommands[i];
      subcommandDataArr[i] = subcommand.toSubcommandData();
    }

    SlashCommandData slashCommandData =
        Commands.slash(stringsManager.getString(name), stringsManager.getString(description))
            .setGuildOnly(guildOnly)
            .addOptions(options)
            .addSubcommands(subcommandDataArr);

    DiscordLocale[] locales = stringsManager.getAvailableLocales();
    for (DiscordLocale locale : locales) {
      localizedNames.put(locale, stringsManager.getString(locale, name));
      localizedDescriptions.put(locale, stringsManager.getString(locale, description));
    }

    slashCommandData.setNameLocalizations(localizedNames);
    slashCommandData.setDescriptionLocalizations(localizedDescriptions);

    return slashCommandData;
  }

  private final SubcommandData toSubcommandData() {
    if (!isSubcommand)
      throw new RuntimeException("Only `SlashCommand` configured as a subcommand can use this function.");

    SubcommandData subcommandData =
        new SubcommandData(stringsManager.getString(name), stringsManager.getString(description)).addOptions(options);

    DiscordLocale[] locales = stringsManager.getAvailableLocales();
    for (DiscordLocale locale : locales) {
      localizedNames.put(locale, stringsManager.getString(locale, name));
      localizedDescriptions.put(locale, stringsManager.getString(locale, description));
    }

    return subcommandData;
  }

  public SlashCommand setOptions(OptionData... options) {
    this.options = options;
    return this;
  }

  public SlashCommand setSubcommands(SlashCommand... subcommands) {
    for (int i = 0; i < subcommands.length; i++) {
      subcommands[i].isSubcommand = true;
    }
    this.subcommands = subcommands;
    return this;
  }

  public SlashCommand[] getSubcommands() {
    return this.subcommands;
  }

  public OptionData[] getOptions() {
    return this.options;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public boolean isSubcommand() {
    return this.isSubcommand;
  }

  public boolean isGuildOnly() {
    return this.guildOnly;
  }

  public void setGuildOnly(boolean guildOnly) {
    this.guildOnly = guildOnly;
  }

  public Map<DiscordLocale, String> getLocalizedNames() {
    return this.localizedNames;
  }

  public Map<DiscordLocale, String> getLocalizedDescriptions() {
    return this.localizedDescriptions;
  }
}
