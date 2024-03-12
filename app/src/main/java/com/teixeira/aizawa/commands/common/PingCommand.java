package com.teixeira.aizawa.commands.common;

import com.teixeira.aizawa.commands.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand extends ICommand {

  public PingCommand() {
    super("ping", "Mostrar meu ping atual");
  }

  @Override
  public void run(SlashCommandInteractionEvent event) {
    long ping = event.getJDA().getGatewayPing();
    event.reply("Meu ping atual: " + ping).queue();
  }
}
