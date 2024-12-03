package com.teixeira0x.aizawa.feature.commands.common;

import com.teixeira0x.aizawa.core.command.SlashCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand extends SlashCommand {
  public PingCommand() {
    super("ping", "Mostrar meu ping atual");
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    long ping = event.getJDA().getGatewayPing();

    EmbedBuilder embed = new EmbedBuilder();
    embed.setDescription("Meu ping atual: " + ping);
    event.replyEmbeds(embed.build()).queue();
  }
}
