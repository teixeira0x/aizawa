package com.teixeira0x.aizawa.listeners;

import com.teixeira0x.aizawa.core.service.ServiceManager;
import com.teixeira0x.aizawa.core.service.exception.ServiceException;
import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StatusListener implements EventListener {
  private final ServiceManager serviceManager;

  public StatusListener(@Nullable ServiceManager serviceManager) {
    this.serviceManager = serviceManager;
  }

  @Override
  public void onEvent(GenericEvent event) {
    if (event instanceof ReadyEvent readyEvent) {
      onReady(readyEvent);
    } else if (event instanceof ShutdownEvent shutdownEvent) {
      onShutdown(shutdownEvent);
    }
  }

  private void onReady(ReadyEvent readyEvent) {
    if (serviceManager != null) {
      try {
        serviceManager.startServices(readyEvent.getJDA());
      } catch (ServiceException se) {
        se.printStackTrace();
      }
    }
  }

  private void onShutdown(ShutdownEvent shutdownEvent) {
    if (serviceManager != null) {
      try {
        serviceManager.stopServices();
      } catch (ServiceException se) {
        se.printStackTrace();
      }
    }
  }
}
