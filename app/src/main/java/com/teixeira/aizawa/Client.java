package com.teixeira.aizawa;

import com.teixeira.aizawa.commands.ICommand;
import java.util.ArrayList;
import java.util.Collection;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client extends ListenerAdapter {

  private static final Logger LOG = LoggerFactory.getLogger("Client");

  private final JDA jda;

  public Client() {
    jda =
        JDABuilder.createDefault(Main.dotenv().get("BOT_TOKEN"))
            .setActivity(Activity.playing("firo firo"))
            .addEventListeners(this)
            .build();
  }

  @Override
  public void onReady(ReadyEvent event) {

    Collection<ICommand> icommands = ICommand.commands.values();
    Collection<CommandData> commands = new ArrayList<>();

    for (ICommand icommand : icommands) {

      if (icommand instanceof CommandData command) {
        commands.add(command);
        LOG.info("Commmand: " + command.getName() + " registered!");
      }
    }

    jda.updateCommands().addCommands(commands).queue();
  }

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    ICommand command = ICommand.commands.get(event.getName());

    if (command != null) {

      ICommand subcommand = ICommand.commands.get(event.getName() + event.getSubcommandName());
      if (subcommand != null) {
        subcommand.run(event);

        LOG.info(
            String.format(
                "Subcommand: %s %s executed!", event.getName(), event.getSubcommandName()));
        return;
      }

      command.run(event);

      LOG.info("Command: " + event.getName() + " executed!");
    }
  }

  @Override
  public void onShutdown(ShutdownEvent event) {}
}
