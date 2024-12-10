package com.teixeira0x.aizawa.feature.commands.common;

import com.teixeira0x.aizawa.core.command.SlashCommand;
import com.teixeira0x.aizawa.core.strings.StringsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand extends SlashCommand {
  public PingCommand(StringsManager stringsManager) {
    super(stringsManager, "common.ping.name", "common.ping.description");
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {
    long ping = event.getJDA().getGatewayPing();

    EmbedBuilder embed = new EmbedBuilder();
    embed.setDescription(
        String.format(
            stringsManager.getString(event.getGuildLocale(), "common.ping.message"), ping));
    event.replyEmbeds(embed.build()).queue();
  }
}
