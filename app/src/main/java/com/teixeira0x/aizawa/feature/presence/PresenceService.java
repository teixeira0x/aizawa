package com.teixeira0x.aizawa.feature.presence;

import static net.dv8tion.jda.api.entities.Activity.ActivityType;

import com.teixeira0x.aizawa.core.service.Service;
import com.teixeira0x.aizawa.feature.presence.model.ActivityStatus;
import com.teixeira0x.aizawa.utils.RandomUtils;
import jakarta.annotation.Nonnull;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;

public class PresenceService extends Service {
  private static final long DELAY = 30_000L; // 30 seconds
  private static final String SERVER_COUNT = "<server-count>";
  private static final ActivityStatus[] ACTIVITY_STATUS_LIST = {
      new ActivityStatus(ActivityType.PLAYING, "atualizações ☺️"),
      new ActivityStatus(ActivityType.PLAYING, SERVER_COUNT + " servidores 😝"),
      new ActivityStatus(ActivityType.LISTENING, "sugestões da comunidade 🎧"),
      new ActivityStatus(ActivityType.STREAMING, "novidades em breve! 🚀"),
      new ActivityStatus(ActivityType.WATCHING, "estatísticas em tempo real 📊")};

  private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

  @Override
  protected void onStart(@Nonnull JDA jda) {
    scheduler.scheduleAtFixedRate(() -> updateStatus(jda), 0, DELAY, TimeUnit.MILLISECONDS);
  }

  @Override
  protected void onStop() {
    scheduler.shutdownNow();
  }

  private void updateStatus(@Nonnull JDA jda) {
    try {
      ActivityStatus status = RandomUtils.choice((Object[]) ACTIVITY_STATUS_LIST);
      ActivityType type = status.getType();
      String name = getNameFormatted(jda, status.getName());
      jda.getPresence().setActivity(Activity.of(type, name));
    } catch (Exception err) {
      err.printStackTrace();
    }
  }

  private String getNameFormatted(JDA jda, String name) {
    if (name.contains(SERVER_COUNT)) {
      return name.replace(SERVER_COUNT, String.valueOf(jda.getGuilds().size()));
    }
    return name;
  }
}