package com.teixeira.aizawa;

import com.teixeira.aizawa.commands.common.PingCommand;
import com.teixeira.aizawa.commands.economy.wallet.WalletCommands;
import com.teixeira.aizawa.commands.roleplay.RoleplayCommands;
import com.teixeira.aizawa.listeners.ButtonListener;
import com.teixeira.aizawa.listeners.SlashCommandListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Aizawa {

  private static final Logger LOG = LoggerFactory.getLogger(Aizawa.class);

  private static Aizawa sInstance;

  public static void main(String[] args) {
    sInstance = new Aizawa();
  }

  public static Aizawa getInstance() {
    return sInstance;
  }

  private final Dotenv dotenv;

  private final JDA jda;

  private Aizawa() {
    dotenv = Dotenv.load();

    SlashCommandListener slashCommandListener =
        new SlashCommandListener()
            .addCommands(new PingCommand(), new RoleplayCommands(), new WalletCommands());

    jda =
        JDABuilder.createDefault(getEnv("BOT_TOKEN"))
            .addEventListeners(slashCommandListener, new ButtonListener())
            .build();
  }

  public String getEnv(String name) {
    return dotenv.get(name);
  }
}
