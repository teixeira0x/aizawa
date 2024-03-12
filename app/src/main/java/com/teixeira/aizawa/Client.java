package com.teixeira.aizawa;

import com.teixeira.aizawa.commands.ICommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Client extends ListenerAdapter {

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
    jda.updateCommands().addCommands(ICommand.commands.values());
  }

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    ICommand command = ICommand.commands.get(event.getName());

    if (command != null) {
      command.run(event);
    }
  }

  @Override
  public void onShutdown(ShutdownEvent event) {}
}
