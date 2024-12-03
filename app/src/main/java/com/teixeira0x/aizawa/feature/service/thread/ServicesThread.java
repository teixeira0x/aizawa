package com.teixeira0x.aizawa.feature.service.thread;

import com.teixeira0x.aizawa.core.service.Service;
import jakarta.annotation.Nonnull;
import java.util.List;
import net.dv8tion.jda.api.JDA;

public class ServicesThread extends Thread {
  private final JDA jda;
  private final List<Service> services;

  public ServicesThread(@Nonnull JDA jda, @Nonnull List<Service> services) {
    this.jda = jda;
    this.services = services;
  }

  @Override
  public void run() {
    try {
      for (Service service : services) {
        service.start(jda);
      }
    } catch (Exception err) {
      err.printStackTrace();
    }
  }

  public void stopServices() {
    try {
      for (Service service : services) {
        service.stop();
      }
    } catch (Exception err) {
      err.printStackTrace();
    }
  }
}
