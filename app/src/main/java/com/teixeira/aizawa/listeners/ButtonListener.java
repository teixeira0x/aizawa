package com.teixeira.aizawa.listeners;

import com.teixeira.aizawa.core.action.Action;
import com.teixeira.aizawa.core.action.ActionResult;
import com.teixeira.aizawa.core.action.ButtonAction;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ButtonListener implements EventListener {
  private static final Logger LOG = LoggerFactory.getLogger(ButtonListener.class);

  private static final Map<Long, ScheduledButtonAction> ACTIONS = new ConcurrentHashMap<>();
  private static final ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(3);

  public static void createButtonAction(long messageId, Action<ButtonInteractionEvent> action) {
    ACTIONS.put(messageId, new ScheduledButtonAction(null, action));
  }

  public static void createButtonAction(long messageId, long timeoutSeconds, Action<ButtonInteractionEvent> action) {
    ScheduledFuture<?> future =
        scheduledExecutor.schedule(() -> removeAction(messageId), timeoutSeconds, TimeUnit.SECONDS);

    ACTIONS.put(messageId, new ScheduledButtonAction(future, action));
  }

  private static void removeAction(long messageId) {
    ACTIONS.remove(messageId);
    LOG.info("ButtonAction: " + messageId + " removed!");
  }

  @Override
  public void onEvent(GenericEvent event) {
    if (event instanceof ButtonInteractionEvent buttonInteractionEvent) {
      onButtonInteraction(buttonInteractionEvent);
    }
  }

  public void onButtonInteraction(ButtonInteractionEvent event) {
    long messageId = event.getMessageIdLong();

    ScheduledButtonAction scheduledAction = ACTIONS.get(messageId);
    if (scheduledAction != null) {
      ActionResult result = scheduledAction.action.execute(event);

      LOG.info("ButtonAction: " + messageId + " executed!");

      if (result == ActionResult.COMPLETED) {
        if (scheduledAction.future != null) {
          scheduledAction.future.cancel(true);
        }

        ACTIONS.remove(messageId);

        LOG.info("ButtonAction: " + messageId + " completed!");
      }

    } else {
      event.reply("Esta interação não existe, ou já foi usada!").setEphemeral(true).queue();
    }
  }

  static class ScheduledButtonAction {
    private final ScheduledFuture<?> future;
    private final Action<ButtonInteractionEvent> action;

    public ScheduledButtonAction(ScheduledFuture<?> future, Action<ButtonInteractionEvent> action) {
      this.future = future;
      this.action = action;
    }

    public ScheduledFuture<?> getFuture() {
      return this.future;
    }

    public Action getAction() {
      return this.action;
    }
  }
}
