package com.teixeira.aizawa.core.action;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface ButtonAction {

  ActionResult execute(ButtonInteractionEvent event);
}
