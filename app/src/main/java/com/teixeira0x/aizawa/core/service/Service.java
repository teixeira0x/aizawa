package com.teixeira0x.aizawa.core.service;

import jakarta.annotation.Nonnull;
import net.dv8tion.jda.api.JDA;

/**
 * Base class for services working in the background.
 *
 * @author Felipe Teixeira
 */
public abstract class Service {
  private boolean isRunning;

  /**
   * Check if the service is currently running.
   *
   * @return {@code true} if the service is running, otherwise {@code false}.
   */
  public boolean isRunning() {
    return isRunning;
  }

  /**
   * Start the service.
   *
   * @param jda The JDA client instance.
   */
  public final void start(@Nonnull JDA jda) {
    this.isRunning = true;
    this.onStart(jda);
  }

  /** Stop the service. */
  public final void stop() {
    this.isRunning = false;
    this.onStop();
  }

  /**
   * Starts the service. This method is called when the service begins its operation.
   *
   * @param jda The JDA client instance used for service operation.
   */
  protected abstract void onStart(@Nonnull JDA jda);

  /** Stops the service. This method is called to clean up the service. */
  protected abstract void onStop();
}
