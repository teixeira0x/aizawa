package com.teixeira0x.aizawa;

import com.teixeira0x.aizawa.config.Config;
import com.teixeira0x.aizawa.core.service.ServiceManager;
import com.teixeira0x.aizawa.core.strings.StringsManager;
import com.teixeira0x.aizawa.feature.commands.common.PingCommand;
import com.teixeira0x.aizawa.feature.commands.roleplay.RoleplayCommands;
import com.teixeira0x.aizawa.feature.presence.PresenceService;
import com.teixeira0x.aizawa.feature.service.ServiceManagerImpl;
import com.teixeira0x.aizawa.listeners.ButtonListener;
import com.teixeira0x.aizawa.listeners.LogListener;
import com.teixeira0x.aizawa.listeners.SlashCommandListener;
import com.teixeira0x.aizawa.listeners.StatusListener;
import com.teixeira0x.aizawa.localization.LocalizedStringsManager;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Aizawa {
  private final Config config = Config.getInstance();

  private final JDA jda;

  private Aizawa(StringsManager stringsManager, @Nullable ServiceManager serviceManager) {
    StatusListener statusListener = new StatusListener(serviceManager);
    LogListener logListener = new LogListener();

    SlashCommandListener slashCommandListener =
        new SlashCommandListener()
            .addCommands(new PingCommand(stringsManager), new RoleplayCommands(stringsManager));
    ButtonListener buttonListener = new ButtonListener();

    this.jda =
        JDABuilder.create(config.getBotToken(), GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
            .addEventListeners(statusListener, logListener, slashCommandListener, buttonListener)
            .build();
  }

  public JDA getJDA() {
    return jda;
  }

  public Config getConfig() {
    return this.config;
  }

  private static Aizawa sInstance;

  public static void main(String[] args) {
    sInstance =
        new Aizawa(new LocalizedStringsManager(), new ServiceManagerImpl(new PresenceService()));
  }

  public static Aizawa getInstance() {
    return sInstance;
  }
}
