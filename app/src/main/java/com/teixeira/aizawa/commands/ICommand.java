package com.teixeira.aizawa.commands;

import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public abstract class ICommand extends CommandDataImpl {

  public static final Map<String, ICommand> commands = new HashMap<>();

  public ICommand(String name, String description) {
    super(name, description);
    commands.put(name, this);
  }

  public abstract void run(SlashCommandInteractionEvent event);
}
