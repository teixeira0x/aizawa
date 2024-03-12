package com.teixeira.aizawa.components;

import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;

public interface IComponent {

  public static final Map<String, IComponent> components = new HashMap<>();

  void run(GenericComponentInteractionCreateEvent event);
}
