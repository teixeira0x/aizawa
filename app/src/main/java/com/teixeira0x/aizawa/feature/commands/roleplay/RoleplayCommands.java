package com.teixeira0x.aizawa.feature.commands.roleplay;

import com.teixeira0x.aizawa.core.action.ActionResult;
import com.teixeira0x.aizawa.core.command.SlashCommand;
import com.teixeira0x.aizawa.feature.gif.OtakuGifsAPI;
import com.teixeira0x.aizawa.feature.gif.model.GifURL;
import com.teixeira0x.aizawa.listeners.ButtonListener;
import com.teixeira0x.aizawa.utils.RandomUtils;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class RoleplayCommands extends SlashCommand {
  public RoleplayCommands() {
    super("roleplay", "Comandos relacionados a roleplay");

    setSubcommands(new HugCommand(), new KissCommand(), new PunchCommand());
    setGuildOnly(true);
  }

  @Override
  public void execute(SlashCommandInteractionEvent event) {}

  private abstract class BaseRoleplayCommand extends SlashCommand {
    public BaseRoleplayCommand(String name, String description) {
      super(name, description);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
      event.deferReply().queue();

      executeRoleplay(event.getHook(), event.getUser(), event.getOption("usuário").getAsUser());
    }

    protected abstract void executeRoleplay(InteractionHook hook, User sender, User receiver);
  }

  private class HugCommand extends BaseRoleplayCommand {
    HugCommand() {
      super("abraçar", "Abraçar um usuário");

      setOptions(new OptionData(OptionType.USER, "usuário", "Usuário que deseja abraçar", true, false));
    }

    @Override
    protected void executeRoleplay(InteractionHook hook, User sender, User receiver) {
      GifURL hugGif = OtakuGifsAPI.get("hug");
      if (hugGif == null) {
        hook.deleteOriginal().queue(unused -> {
          hook.sendMessage("Não foi possivel obter um gif, tente novamente mais tarde!").setEphemeral(true).queue();
        });
        return;
      }

      String description;
      if (sender.getIdLong() == receiver.getIdLong()) {
        description = RandomUtils.choice("%s se abraçou!? :thinking:", "%s deu um abraço em si mesmo?! :thinking:");
      } else {
        description = RandomUtils.choice("%s abraçou %s", "%s deu um abraço em %s");
      }

      EmbedBuilder embed = new EmbedBuilder();
      embed.setColor(Color.RED);
      embed.setAuthor(sender.getName(), null, sender.getAvatarUrl());
      embed.setDescription(String.format(description, sender.getAsMention(), receiver.getAsMention()));
      embed.setImage(hugGif.getUrl());

      Button button =
          Button.primary("roleplay:hug:button", "Retribuir").withDisabled(sender.getIdLong() == receiver.getIdLong());

      Message message = hook.editOriginalEmbeds(embed.build()).setActionRow(button).complete();

      ButtonListener.createButtonAction(message.getIdLong(), 60L, event -> {
        if (event.getUser().getIdLong() == sender.getIdLong()) {
          event.reply("Você não pode retribuir seu próprio abraço!").setEphemeral(true).queue();
          return ActionResult.IGNORED;
        }

        if (event.getUser().getIdLong() != receiver.getIdLong()) {
          event.reply("Este abraço não foi para você!").setEphemeral(true).queue();
          return ActionResult.IGNORED;
        }
        event.deferReply().queue();

        executeRoleplay(event.getHook(), event.getUser(), sender);
        return ActionResult.COMPLETED;
      });
    }
  }

  private class KissCommand extends BaseRoleplayCommand {
    KissCommand() {
      super("beijar", "Beijar um usuário");

      setOptions(new OptionData(OptionType.USER, "usuário", "Usuário que deseja beijar", true, false));
    }

    @Override
    protected void executeRoleplay(InteractionHook hook, User sender, User receiver) {
      GifURL kissGif = OtakuGifsAPI.get("kiss");
      if (kissGif == null) {
        hook.deleteOriginal().queue(unused -> {
          hook.sendMessage("Não foi possivel obter um gif, tente novamente mais tarde!").setEphemeral(true).queue();
        });
        return;
      }

      String description;
      if (sender.getIdLong() == receiver.getIdLong()) {
        description = "%s se beijou!? :thinking:";
      } else {
        description = RandomUtils.choice("%s beijou %s", "%s deu um beijo em %s");
      }

      EmbedBuilder embed = new EmbedBuilder();
      embed.setColor(Color.RED);
      embed.setAuthor(sender.getName(), null, sender.getAvatarUrl());
      embed.setTitle(String.format(description, sender.getAsMention(), receiver.getAsMention()));
      embed.setImage(kissGif.getUrl());

      Button button =
          Button.primary("roleplay:kiss:button", "Retribuir").withDisabled(sender.getIdLong() == receiver.getIdLong());

      Message message = hook.editOriginalEmbeds(embed.build()).setActionRow(button).complete();

      ButtonListener.createButtonAction(message.getIdLong(), 60L, event -> {
        if (event.getUser().getIdLong() == sender.getIdLong()) {
          event.reply("Você não pode retribuir seu próprio beijo!").setEphemeral(true).queue();
          return ActionResult.IGNORED;
        }

        if (event.getUser().getIdLong() != receiver.getIdLong()) {
          event.reply("Este beijo não foi para você!").setEphemeral(true).queue();
          return ActionResult.IGNORED;
        }
        event.deferReply().queue();

        executeRoleplay(event.getHook(), event.getUser(), sender);
        return ActionResult.COMPLETED;
      });
    }
  }

  private class PunchCommand extends BaseRoleplayCommand {
    PunchCommand() {
      super("socar", "Socar um usuário");

      setOptions(new OptionData(OptionType.USER, "usuário", "Usuário que deseja socar", true, false));
    }

    @Override
    protected void executeRoleplay(InteractionHook hook, User sender, User receiver) {
      GifURL punchGif = OtakuGifsAPI.get("punch");
      if (punchGif == null) {
        hook.deleteOriginal().queue(unused -> {
          hook.sendMessage("Não foi possivel obter um gif, tente novamente mais tarde!").setEphemeral(true).queue();
        });
        return;
      }

      String description;
      if (sender.getIdLong() == receiver.getIdLong()) {
        description = "%s se deu um soco!? :thinking:";
      } else {
        description = RandomUtils.choice("%s socou %s", "%s deu um soco em %s");
      }

      EmbedBuilder embed = new EmbedBuilder();
      embed.setColor(Color.RED);
      embed.setAuthor(sender.getName(), null, sender.getAvatarUrl());
      embed.setTitle(String.format(description, sender.getAsMention(), receiver.getAsMention()));
      embed.setImage(punchGif.getUrl());

      Button button =
          Button.primary("roleplay:punch:button", "Retribuir").withDisabled(sender.getIdLong() == receiver.getIdLong());

      Message message = hook.editOriginalEmbeds(embed.build()).setActionRow(button).complete();

      ButtonListener.createButtonAction(message.getIdLong(), 60L, event -> {
        if (event.getUser().getIdLong() == sender.getIdLong()) {
          event.reply("Você não pode retribuir seu próprio soco!").setEphemeral(true).queue();
          return ActionResult.IGNORED;
        }

        if (event.getUser().getIdLong() != receiver.getIdLong()) {
          event.reply("Este soco não foi para você!").setEphemeral(true).queue();
          return ActionResult.IGNORED;
        }
        event.deferReply().queue();

        executeRoleplay(event.getHook(), event.getUser(), sender);
        return ActionResult.COMPLETED;
      });
    }
  }
}
