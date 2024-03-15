package com.teixeira.aizawa.listeners;

import com.teixeira.aizawa.core.command.SlashCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlashCommandListener extends ListenerAdapter {
  private static final Logger LOG = LoggerFactory.getLogger("SlashCommandListener");

  private final Map<String, Integer> slashCommandsIndex = new HashMap<>();
  private final List<SlashCommand> slashCommands = new ArrayList<>();

  @Override
  public void onReady(ReadyEvent event) {
    JDA jda = event.getJDA();

    List<CommandData> commands = new ArrayList<>();
    for (SlashCommand command : slashCommands) {
      if (command.isSubcommand()) {
        continue;
      }
      commands.add(command.toCommandData());
    }
    jda.updateCommands().addCommands(commands).queue();
  }

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    SlashCommand command = findSlashCommand(event.getFullCommandName());
    if (command != null) {
      command.execute(event);

      LOG.info("Command: " + event.getFullCommandName() + " executed!");
    }
  }

  public SlashCommandListener addCommands(SlashCommand... commands) {
    for (SlashCommand command : commands) {
      addCommand(command.getName(), command);
    }

    return this;
  }

  private void addCommand(String commandName, SlashCommand command) {
    slashCommandsIndex.put(commandName, slashCommands.size());
    slashCommands.add(command);

    for (SlashCommand subcommand : command.getSubcommands()) {
      addCommand(commandName + " " + subcommand.getName(), subcommand);
    }
  }

  private SlashCommand findSlashCommand(String fullname) {
    int commandIndex = slashCommandsIndex.get(fullname);
    SlashCommand command = slashCommands.get(commandIndex);
    return command;
  }
}
