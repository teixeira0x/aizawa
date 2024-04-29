package com.teixeira.aizawa.commands.economy.wallet;

import com.teixeira.aizawa.controllers.UserController;
import com.teixeira.aizawa.core.action.ActionResult;
import com.teixeira.aizawa.core.command.SlashCommand;
import com.teixeira.aizawa.database.entity.UserEntity;
import com.teixeira.aizawa.listeners.ButtonListener;
import com.teixeira.aizawa.utils.BalanceUtils;
import java.awt.Color;
import java.math.BigDecimal;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

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

      setOptions(new OptionData(OptionType.USER, "usuário", "Usuário que deseja ver a carteira", false));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
      OptionMapping option = event.getOption("usuário");
      User user = option != null ? option.getAsUser() : event.getUser();

      if (user.isBot()) {
        event.reply("Você não pode ver a carteira de um bot").setEphemeral(true).queue();
        return;
      }

      event.deferReply(false).queue();
      InteractionHook hook = event.getHook();

      UserEntity userEntity = UserController.findOrInsertUser(user);

      EmbedBuilder embed = new EmbedBuilder();
      embed.setColor(Color.decode("#0d7a13"));
      embed.setAuthor(user.getName(), null, user.getAvatarUrl());
      embed.setThumbnail(user.getAvatarUrl());
      embed.setTimestamp(event.getTimeCreated());

      setBalanceEmbedFields(embed, userEntity);

      Button button = Button.primary("economy:wallet:button", "Atualizar").withEmoji(Emoji.fromUnicode("🔄"));

      Message message = hook.editOriginalEmbeds(embed.build()).setActionRow(button).complete();

      ButtonListener.createButtonAction(message.getIdLong(), 30L, buttonEvent -> {
        if (buttonEvent.getUser().getIdLong() != event.getUser().getIdLong()) {
          buttonEvent.reply("Você não pode ultilizar está interação").setEphemeral(true).queue();
          return ActionResult.IGNORED;
        }

        buttonEvent.deferEdit().queue();

        setBalanceEmbedFields(embed, UserController.findOrInsertUser(user));
        hook.editOriginalEmbeds(embed.build()).queue();
        return ActionResult.IGNORED;
      });
    }

    private void setBalanceEmbedFields(EmbedBuilder embed, UserEntity userEntity) {
      BigDecimal balance = userEntity.getBalance();
      BigDecimal bankBalance = userEntity.getBankBalance();
      BigDecimal totalBalance = balance.add(bankBalance);

      embed.clearFields();
      embed.addField("Seu saldo:", "$" + BalanceUtils.formatBalance(balance), false);
      embed.addField("Saldo bancário:", "$" + BalanceUtils.formatBalance(bankBalance), false);
      embed.addField("Saldo total:", "$" + BalanceUtils.formatBalance(totalBalance), false);
    }
  }
}
