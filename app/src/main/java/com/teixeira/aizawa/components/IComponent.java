package com.teixeira.aizawa.components;

import java.util.Map;
import java.util.WeakHashMap;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;

public interface IComponent {

  public static final Map<String, IComponent> components = new WeakHashMap<>();

  void run(GenericComponentInteractionCreateEvent event);
}
