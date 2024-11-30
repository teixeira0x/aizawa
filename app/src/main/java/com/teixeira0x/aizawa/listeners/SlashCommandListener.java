package com.teixeira0x.aizawa.listeners;

import com.teixeira0x.aizawa.core.command.SlashCommand;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlashCommandListener implements EventListener {
  private static final Logger LOG = LoggerFactory.getLogger("SlashCommandListener");

  private final Map<String, Integer> slashCommandsIndex = new HashMap<>();
  private final List<SlashCommand> slashCommands = new ArrayList<>();

  @Override
  public void onEvent(GenericEvent event) {
    if (event instanceof ReadyEvent readyEvent) {
      onReady(readyEvent);
    } else if (event instanceof SlashCommandInteractionEvent slashCommandInteractionEvent) {
      onSlashCommandInteraction(slashCommandInteractionEvent);
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

  private void onReady(ReadyEvent event) {
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

  private void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    SlashCommand command = findSlashCommand(event.getFullCommandName());
    if (command != null) {
      command.execute(event);

      LOG.info("Command: " + event.getFullCommandName() + " executed!");
    }
  }

  private SlashCommand findSlashCommand(String fullname) {
    int commandIndex = slashCommandsIndex.get(fullname);
    SlashCommand command = slashCommands.get(commandIndex);
    return command;
  }
}
