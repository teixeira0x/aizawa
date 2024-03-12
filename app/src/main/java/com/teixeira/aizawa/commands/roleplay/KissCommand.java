package com.teixeira.aizawa.commands.roleplay;

import com.teixeira.aizawa.commands.AbsSubCommand;
import com.teixeira.aizawa.commands.roleplay.api.OtakusGifAPI;
import com.teixeira.aizawa.components.ButtonComponent;
import com.teixeira.aizawa.utils.RandomUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

public class KissCommand extends AbsSubCommand {

  public KissCommand() {
    super("roleplay", "beijar", "Beijar um usuário");

    addOption(OptionType.USER, "usuário", "Usuário que deseja beijar", true, false);
  }

  @Override
  public void run(SlashCommandInteractionEvent event) {

    event.deferReply(false).queue();

    sendKiss(event.getHook(), event.getUser(), event.getOption("usuário").getAsUser());
  }

  private void sendKiss(InteractionHook hook, User sender, User receiver) {

    OtakusGifAPI kissGif = OtakusGifAPI.get("kiss");
    if (kissGif == null) {
      hook.setEphemeral(true)
          .editOriginal("Não foi possivel obter um gif, tente novamente mais tarde!")
          .queue();
      return;
    }

    String message;
    if (sender.getIdLong() == receiver.getIdLong()) {
      message = "%s se beijou!? :thinking:";
    } else {
      message = RandomUtils.choice("%s beijou %s", "%s deu um beijo em %s");
    }

    EmbedBuilder embed = new EmbedBuilder();
    embed.setAuthor(sender.getName(), null, sender.getAvatarUrl());
    embed.setTitle(String.format(message, sender.getAsMention(), receiver.getAsMention()));
    embed.setImage(kissGif.getUrl());

    hook.editOriginalEmbeds(embed.build())
        .setActionRow(
            new ButtonComponent(
                "roleplay.kiss.retribuir",
                "Retribuir",
                ButtonStyle.PRIMARY,
                sender.getIdLong() == receiver.getIdLong(),
                null) {

              @Override
              public void run(GenericComponentInteractionCreateEvent event) {
                event.deferReply(event.getUser().getIdLong() != receiver.getIdLong()).queue();

                if (event.getUser().getIdLong() == receiver.getIdLong()) {
                  sendKiss(event.getHook(), event.getUser(), sender);
                } else {
                  event.getHook().sendMessage("Esse beijo não foi para você!").queue();
                }
              }
            })
        .queue();
  }
}
