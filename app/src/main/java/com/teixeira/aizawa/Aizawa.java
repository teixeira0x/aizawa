package com.teixeira.aizawa;

import com.teixeira.aizawa.commands.common.PingCommand;
import com.teixeira.aizawa.commands.economy.wallet.WalletCommands;
import com.teixeira.aizawa.commands.roleplay.RoleplayCommands;
import com.teixeira.aizawa.config.Config;
import com.teixeira.aizawa.listeners.ButtonListener;
import com.teixeira.aizawa.listeners.SlashCommandListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Aizawa {

  private final Config config = Config.getInstance();

  private final JDA jda;

  private Aizawa() {
    SlashCommandListener slashCommandListener =
        new SlashCommandListener()
            .addCommands(new PingCommand(), new RoleplayCommands(), new WalletCommands());

    jda =
        JDABuilder.createDefault(config.getBotToken())
            .addEventListeners(slashCommandListener, new ButtonListener())
            .build();
  }

  public Config getConfig() {
    return this.config;
  }

  private static Aizawa sInstance;

  public static void main(String[] args) {
    sInstance = new Aizawa();
  }

  public static Aizawa getInstance() {
    return sInstance;
  }
}
