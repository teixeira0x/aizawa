package com.teixeira.aizawa.commands.economy.wallet;

import com.teixeira.aizawa.core.command.SlashCommand;
import com.teixeira.aizawa.domain.controller.UserController;
import com.teixeira.aizawa.domain.model.UserModel;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class WalletCommands extends SlashCommand {
  public WalletCommands() {
    super("carteira", "Comandos relacionados a carteira");

    setSubcommands(new SeeWalletCommand());
    setGuildOnly(true);
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {}

  private class SeeWalletCommand extends SlashCommand {
    SeeWalletCommand() {
      super("ver", "Ver sua carteira ou de algum outro usuário");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
      event.deferReply().queue();

      User user = event.getUser();
      UserModel userModel = UserController.findOrInsertUser(user);

      EmbedBuilder embed = new EmbedBuilder();
      embed.setColor(Color.green);
      embed.setAuthor(user.getName(), null, user.getAvatarUrl());
      embed.setThumbnail(user.getAvatarUrl());
      embed.setTimestamp(event.getTimeCreated());

      embed.addField("Saldo:", "$" + userModel.balance(), false);

      event.getHook().editOriginalEmbeds(embed.build()).queue();
    }
  }
}
