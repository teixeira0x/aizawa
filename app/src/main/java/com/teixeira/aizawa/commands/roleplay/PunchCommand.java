package com.teixeira.aizawa.commands.roleplay;

import com.teixeira.aizawa.commands.AbsSubCommand;
import com.teixeira.aizawa.commands.roleplay.api.OtakusGifAPI;
import com.teixeira.aizawa.components.ButtonComponent;
import com.teixeira.aizawa.utils.RandomUtils;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

public class PunchCommand extends AbsSubCommand {

  PunchCommand() {
    super("roleplay", "socar", "Socar um usuário");

    addOption(OptionType.USER, "usuário", "Usuário que deseja socar", true, false);
  }

  @Override
  public void run(SlashCommandInteractionEvent event) {

    event.deferReply().queue();

    sendPunch(event.getHook(), event.getUser(), event.getOption("usuário").getAsUser());
  }

  private void sendPunch(InteractionHook hook, User sender, User receiver) {

    OtakusGifAPI hugGif = OtakusGifAPI.get("punch");
    if (hugGif == null) {
      hook.setEphemeral(true)
          .editOriginal("Não foi possivel obter um gif, tente novamente mais tarde!")
          .queue();
      return;
    }

    String message;
    if (sender.getIdLong() == receiver.getIdLong()) {
      message = "%s se deu um soco!? :thinking:";
    } else {
      message = RandomUtils.choice("%s socou %s", "%s deu um soco em %s");
    }

    EmbedBuilder embed = new EmbedBuilder();
    embed.setColor(Color.RED);
    embed.setAuthor(sender.getName(), null, sender.getAvatarUrl());
    embed.setTitle(String.format(message, sender.getAsMention(), receiver.getAsMention()));
    embed.setImage(hugGif.getUrl());

    hook.editOriginalEmbeds(embed.build())
        .setActionRow(
            new ButtonComponent(
                "roleplay.punch.retribuir",
                "Retribuir",
                ButtonStyle.PRIMARY,
                sender.getIdLong() == receiver.getIdLong(),
                null) {

              @Override
              public void run(GenericComponentInteractionCreateEvent event) {
                event.deferReply(event.getUser().getIdLong() != receiver.getIdLong()).queue();

                if (event.getUser().getIdLong() == receiver.getIdLong()) {
                  sendPunch(event.getHook(), event.getUser(), sender);
                } else {
                  event.getHook().sendMessage("Esse soco não foi para você!").queue();
                }
              }
            })
        .queue();
  }
}
