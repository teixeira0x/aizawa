package com.teixeira.aizawa.commands.economy.wallet;

import com.teixeira.aizawa.commands.AbsCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class WalletCommands extends AbsCommand {

  public WalletCommands() {
    super("carteira", "Comandos relacionados a carteira");

    addSubcommands(new SeeWalletCommand());
  }

  @Override
  public void run(SlashCommandInteractionEvent event) {}
}
