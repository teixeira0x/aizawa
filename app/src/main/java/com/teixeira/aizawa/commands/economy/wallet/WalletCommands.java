package com.teixeira.aizawa.commands.economy.wallet;

import com.teixeira.aizawa.controllers.UserController;
import com.teixeira.aizawa.core.action.ActionResult;
import com.teixeira.aizawa.core.command.SlashCommand;
import com.teixeira.aizawa.database.entity.UserEntity;
import com.teixeira.aizawa.listeners.ButtonListener;
import com.teixeira.aizawa.utils.BalanceUtils;
import java.awt.Color;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
  private final SeeWalletCommand seeWalletCommand;

  public WalletCommands() {
    super("carteira", "Comandos relacionados a carteira");

    seeWalletCommand = new SeeWalletCommand();
    setSubcommands(seeWalletCommand, new DepositCommand(), new WithdrawCommand());
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
      execute(hook, user, event.getTimeCreated());
    }

    private void execute(InteractionHook hook, User user, OffsetDateTime timestamp) {
      UserEntity userEntity = UserController.findOrInsertUser(user);

      EmbedBuilder embed = new EmbedBuilder();
      embed.setColor(Color.decode("#0d7a13"));
      embed.setAuthor(user.getName(), null, user.getAvatarUrl());
      embed.setThumbnail(user.getAvatarUrl());
      embed.setTimestamp(timestamp);

      setBalanceEmbedFields(embed, userEntity);

      Button button = Button.primary("economy:wallet:button", "Atualizar").withEmoji(Emoji.fromUnicode("🔄"));

      Message message = hook.sendMessageEmbeds(embed.build()).setActionRow(button).complete();

      ButtonListener.createButtonAction(message.getIdLong(), 30L, buttonEvent -> {
        if (buttonEvent.getUser().getIdLong() != user.getIdLong()) {
          buttonEvent.reply("Você não pode ultilizar está interação").setEphemeral(true).queue();
          return ActionResult.IGNORED;
        }

        buttonEvent.deferEdit().queue();

        setBalanceEmbedFields(embed, UserController.findOrInsertUser(user));
        buttonEvent.getHook().editOriginalEmbeds(embed.build()).queue();
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

  private class DepositCommand extends SlashCommand {
    DepositCommand() {
      super("depositar", "Depositar um saldo");

      setOptions(new OptionData(OptionType.STRING, "quantia", "Quantidade que você deseja depositar", true, false));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
      event.deferReply(false).queue();

      String amount = event.getOption("quantia").getAsString();

      User user = event.getUser();
      UserEntity userEntity = UserController.findOrInsertUser(user);
      BigDecimal userBalance = userEntity.getBalance();
      BigDecimal bankBalance = userEntity.getBankBalance();

      int amountInt = -1;
      if (amount.equals("tudo") || amount.equals("all")) {
        amountInt = bankBalance.add(userBalance).intValue();
      } else {
        try {
          amountInt = Integer.parseInt(amount);
        } catch (Exception e) {
          // Ignore
        }
      }

      if (amountInt > userBalance.intValue() || amountInt <= 0) {
        event.getHook().deleteOriginal().queue(unused -> {
          event.getHook()
              .sendMessage("Este valor é invalido, por favor digite outro valor e tente novamente.")
              .setEphemeral(true)
              .queue();
        });

        return;
      }

      userEntity.setBankBalance(bankBalance.add(BigDecimal.valueOf(amountInt)));
      userEntity.setBalance(BigDecimal.valueOf(userBalance.intValue() - amountInt));

      UserController.updateUser(userEntity);

      EmbedBuilder embed = new EmbedBuilder();
      embed.setColor(Color.decode("#0d7a13"));
      embed.setAuthor(user.getName(), null, user.getAvatarUrl());
      embed.setThumbnail(user.getAvatarUrl());
      embed.setTimestamp(event.getTimeCreated());

      embed.addField("Você depositou:", "$" + BalanceUtils.formatBalance(amountInt), false);

      Button button = Button.primary("economy:deposit:button", "Carteira").withEmoji(Emoji.fromUnicode("💰"));

      Message message = event.getHook().editOriginalEmbeds(embed.build()).setActionRow(button).complete();

      ButtonListener.createButtonAction(message.getIdLong(), 30L, buttonEvent -> {
        if (buttonEvent.getUser().getIdLong() != user.getIdLong()) {
          buttonEvent.reply("Você não pode ultilizar está interação").setEphemeral(true).queue();
          return ActionResult.IGNORED;
        }

        buttonEvent.deferEdit().queue();
        seeWalletCommand.execute(buttonEvent.getHook(), buttonEvent.getUser(), buttonEvent.getTimeCreated());
        return ActionResult.COMPLETED;
      });
    }
  }

  private class WithdrawCommand extends SlashCommand {
    WithdrawCommand() {
      super("sacar", "Sacar um saldo");

      setOptions(new OptionData(OptionType.STRING, "quantia", "Quantidade que você deseja sacar", true, false));
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
      event.deferReply(false).queue();

      String amount = event.getOption("quantia").getAsString();

      User user = event.getUser();
      UserEntity userEntity = UserController.findOrInsertUser(user);
      BigDecimal userBalance = userEntity.getBalance();
      BigDecimal bankBalance = userEntity.getBankBalance();

      int amountInt = -1;
      if (amount.equals("tudo") || amount.equals("all")) {
        amountInt = userBalance.add(bankBalance).intValue();
      } else {
        try {
          amountInt = Integer.parseInt(amount);
        } catch (Exception e) {
          // Ignore
        }
      }

      if (amountInt > bankBalance.intValue() || amountInt <= 0) {
        event.getHook().deleteOriginal().queue(unused -> {
          event.getHook()
              .sendMessage("Este valor é invalido, por favor digite outro valor e tente novamente.")
              .setEphemeral(true)
              .queue();
        });

        return;
      }

      userEntity.setBankBalance(BigDecimal.valueOf(0));
      userEntity.setBalance(BigDecimal.valueOf(amountInt));

      UserController.updateUser(userEntity);

      EmbedBuilder embed = new EmbedBuilder();
      embed.setColor(Color.decode("#0d7a13"));
      embed.setAuthor(user.getName(), null, user.getAvatarUrl());
      embed.setThumbnail(user.getAvatarUrl());
      embed.setTimestamp(event.getTimeCreated());

      embed.addField("Você sacou:", "$" + BalanceUtils.formatBalance(amountInt), false);

      Button button = Button.primary("economy:withdraw:button", "Carteira").withEmoji(Emoji.fromUnicode("💰"));

      Message message = event.getHook().editOriginalEmbeds(embed.build()).setActionRow(button).complete();

      ButtonListener.createButtonAction(message.getIdLong(), 30L, buttonEvent -> {
        if (buttonEvent.getUser().getIdLong() != user.getIdLong()) {
          buttonEvent.reply("Você não pode ultilizar está interação").setEphemeral(true).queue();
          return ActionResult.IGNORED;
        }

        buttonEvent.deferEdit().queue();
        seeWalletCommand.execute(buttonEvent.getHook(), buttonEvent.getUser(), buttonEvent.getTimeCreated());
        return ActionResult.COMPLETED;
      });
    }
  }
}
