package com.teixeira0x.aizawa.core.service;

import com.teixeira0x.aizawa.core.service.exception.ServiceException;
import jakarta.annotation.Nonnull;
import java.util.List;
import net.dv8tion.jda.api.JDA;

/**
 * Base interface for managing services. Provides methods to add, start, and stop services.
 *
 * @author Felipe Teixeira
 */
public interface ServiceManager {
  /**
   * Retrieves the list of managed services.
   *
   * @return A non-null list of services.
   */
  @Nonnull List<Service> getAllServices();

  /**
   * Adds the provided services to the manager.
   *
   * @param services Services to add.
   */
  void addServices(@Nonnull Service... services);

  /**
   * Starts all managed services.
   *
   * @param jda The JDA client instance used for initializing services.
   * @throws ServiceException If an error occurs while starting any service.
   */
  void startServices(@Nonnull JDA jda) throws ServiceException;

  /**
   * Stops all managed services.
   *
   * @throws ServiceException If an error occurs while stopping any service.
   */
  void stopServices() throws ServiceException;
}
