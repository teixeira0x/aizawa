package com.teixeira0x.aizawa.listeners;

import com.teixeira0x.aizawa.Aizawa;
import com.teixeira0x.aizawa.config.Config;
import com.teixeira0x.aizawa.utils.TextFormatter;
import java.time.OffsetDateTime;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class LogListener implements EventListener {
  @Override
  public void onEvent(GenericEvent event) {
    if (event instanceof GuildJoinEvent guildJoinEvent) {
      onGuildJoin(guildJoinEvent);
    } else if (event instanceof SlashCommandInteractionEvent slashCommandInteractionEvent) {
      onSlashCommandInteraction(slashCommandInteractionEvent);
    }
  }

  private void onGuildJoin(GuildJoinEvent joinEvent) {
    Guild guild = joinEvent.getGuild();

    String iconUrl = guild.getIconUrl();
    String name = guild.getName();
    String desc = guild.getDescription();
    String id = guild.getId();
    String locale = guild.getLocale().getLocale();
    String totalServers = String.valueOf(Aizawa.getInstance().getJDA().getGuilds().size());

    EmbedBuilder embed = new EmbedBuilder()
                             .setTitle("Estou em um novo servidor")
                             .setThumbnail(iconUrl)
                             .setTimestamp(OffsetDateTime.now());

    embed.addField("Nome:", TextFormatter.bold(name), true);
    if (desc != null && !desc.isEmpty()) {
      embed.addField("Descrição:", TextFormatter.bold(desc), true);
    }
    embed.addField("Id:", TextFormatter.bold(id), true);
    embed.addField("Local:", TextFormatter.bold(locale), true);
    embed.addField("Total servidores:", TextFormatter.bold(totalServers), true);

    getLogChannel().sendMessageEmbeds(embed.build()).queue();
  }

  private void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    Guild guild = event.getGuild();
    String iconUrl = guild.getIconUrl();
    String guildId = guild.getId();
    String guildName = guild.getName();
    String commandName = event.getFullCommandName();

    EmbedBuilder embed = new EmbedBuilder()
                             .setTitle("Um comando foi utilizado")
                             .setThumbnail(iconUrl)
                             .setTimestamp(OffsetDateTime.now());

    embed.addField("Comando:", TextFormatter.bold(commandName), false);
    embed.addField("Servidor:",
        String.format("Id: %s\nName: %s", TextFormatter.inlineCode(guildId), TextFormatter.inlineCode(guildName)),
        false);

    getLogChannel().sendMessageEmbeds(embed.build()).queue();
  }

  private Guild getBotGuild() {
    Config config = Config.getInstance();
    Guild botGuild = Aizawa.getInstance().getJDA().getGuildById(config.getBotGuildId());

    if (botGuild == null) {
      throw new NullPointerException("Bot guild not found");
    }

    return botGuild;
  }

  private TextChannel getLogChannel() {
    Config config = Config.getInstance();
    TextChannel logChannel = getBotGuild().getChannelById(TextChannel.class, config.getBotLogChannelId());

    if (logChannel == null) {
      throw new NullPointerException("Log channel not found  ");
    }

    return logChannel;
  }
}
