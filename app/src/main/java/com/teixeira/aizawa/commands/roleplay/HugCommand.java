package com.teixeira.aizawa.commands.roleplay;

import com.teixeira.aizawa.commands.AbsSubCommand;
import com.teixeira.aizawa.commands.roleplay.api.OtakusGifAPI;
import com.teixeira.aizawa.utils.RandomUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class HugCommand extends AbsSubCommand {

  public HugCommand() {
    super("roleplay", "abraçar", "Abraçar um usuário");

    addOption(OptionType.USER, "usuário", "Usuário que deseja abraçar", true, false);
  }

  @Override
  public void run(SlashCommandInteractionEvent event) {

    event.deferReply().queue();

    User user = event.getOption("usuário").getAsUser();

    String message;
    if (user.getIdLong() == event.getUser().getIdLong()) {
      message = "%s se abraçou!? :thinking:";
    } else {
      message = RandomUtils.choice("%s abraçou %s", "%s deu um abraço em %s");
    }

    OtakusGifAPI hugGif = OtakusGifAPI.get("hug");
    if (hugGif == null) {
      event
          .getHook()
          .setEphemeral(true)
          .editOriginal("Não foi possivel obter um gif, tente novamente mais tarde!")
          .queue();
      return;
    }

    EmbedBuilder embed = new EmbedBuilder();
    embed.setAuthor(event.getUser().getName(), null, event.getUser().getAvatarUrl());
    embed.setTitle(String.format(message, event.getUser().getAsMention(), user.getAsMention()));
    embed.setImage(hugGif.getUrl());

    event.getHook().editOriginalEmbeds(embed.build()).queue();
  }
}
