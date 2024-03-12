package com.teixeira.aizawa.commands;

import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface ICommand {

  public static final Map<String, ICommand> commands = new HashMap<>();

  void run(SlashCommandInteractionEvent event);
}
