package com.teixeira0x.aizawa.feature.service;

import com.teixeira0x.aizawa.core.service.Service;
import com.teixeira0x.aizawa.core.service.ServiceManager;
import com.teixeira0x.aizawa.core.service.exception.ServiceException;
import com.teixeira0x.aizawa.feature.service.thread.ServicesThread;
import jakarta.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceManagerImpl implements ServiceManager {
  private static final Logger LOG = LoggerFactory.getLogger(ServiceManagerImpl.class);

  private final List<Service> services = new ArrayList<>();
  private ServicesThread thread = null;

  public ServiceManagerImpl() {}

  public ServiceManagerImpl(@Nonnull Service... services) {
    addServices(services);
  }

  @Nonnull
  @Override
  public List<Service> getAllServices() {
    return List.copyOf(services);
  }

  @Override
  public void addServices(@Nonnull Service... services) {
    this.services.addAll(List.of(services));
  }

  @Override
  public void startServices(@Nonnull JDA jda) throws ServiceException {
    if (thread != null) {
      throw new ServiceException("Services are already running");
    }

    LOG.info("Starting services");

    thread = new ServicesThread(jda, services);
    thread.start();

    LOG.info("Services started");
  }

  @Override
  public void stopServices() throws ServiceException {
    if (thread == null) {
      throw new ServiceException("No services running");
    }

    LOG.info("Stoping services");

    thread.stopServices();
    thread = null;

    LOG.info("Services stoped");
  }
}
