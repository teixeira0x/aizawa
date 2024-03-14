package com.teixeira.aizawa.commands.economy.wallet;

import com.teixeira.aizawa.commands.AbsSubCommand;
import com.teixeira.aizawa.domain.controller.UserController;
import com.teixeira.aizawa.domain.model.UserModel;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SeeWalletCommand extends AbsSubCommand {

  SeeWalletCommand() {
    super("carteira", "ver", "Ver sua carteira ou de algum outro usuário");
  }

  @Override
  public void run(SlashCommandInteractionEvent event) {

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
