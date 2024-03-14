package com.teixeira.aizawa.commands.common;

import com.teixeira.aizawa.core.command.SlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand extends SlashCommand {

  public PingCommand() {
    super("ping", "Mostrar meu ping atual");
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    long ping = event.getJDA().getGatewayPing();
    event.reply("Meu ping atual: " + ping).queue();
  }
}
