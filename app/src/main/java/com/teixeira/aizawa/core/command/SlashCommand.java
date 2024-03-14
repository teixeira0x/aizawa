package com.teixeira.aizawa.core.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public abstract class SlashCommand {

  private OptionData[] options = new OptionData[0];
  private SlashCommand[] subcommands = new SlashCommand[0];

  private boolean isSubcommand = false;
  private boolean guildOnly = false;

  private String name;
  private String description;

  public SlashCommand(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public abstract void execute(SlashCommandInteractionEvent event);

  public CommandData toCommandData() {
    SubcommandData[] subcommandDataArr = new SubcommandData[subcommands.length];

    for (int i = 0; i < subcommands.length; i++) {
      SlashCommand subcommand = subcommands[i];
      subcommandDataArr[i] =
          new SubcommandData(subcommand.name, subcommand.description)
              .addOptions(subcommand.options);
    }

    return Commands.slash(name, description)
        .setGuildOnly(guildOnly)
        .addOptions(options)
        .addSubcommands(subcommandDataArr);
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
}
