package com.teixeira.aizawa.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public abstract class AbsSubCommand extends SubcommandData implements ICommand {

  public AbsSubCommand(String parentName, String name, String description) {
    super(name, description);

    ICommand.commands.put(parentName + name, this);
  }
}
