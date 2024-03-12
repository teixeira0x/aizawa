package com.teixeira.aizawa.components;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.internal.entities.EntityBuilder;
import net.dv8tion.jda.internal.interactions.component.ButtonImpl;

public abstract class ButtonComponent extends ButtonImpl implements IComponent {

  public ButtonComponent(DataObject data) {
    this(
        data.getString("custom_id", null),
        data.getString("label", ""),
        ButtonStyle.fromKey(data.getInt("style")),
        data.getString("url", null),
        data.getBoolean("disabled"),
        data.optObject("emoji").map(EntityBuilder::createEmoji).orElse(null));
  }

  public ButtonComponent(
      String id, String label, ButtonStyle style, boolean disabled, Emoji emoji) {
    this(id, label, style, null, disabled, emoji);
  }

  public ButtonComponent(
      String id, String label, ButtonStyle style, String url, boolean disabled, Emoji emoji) {
    super(id, label, style, url, disabled, emoji);

    IComponent.components.put(id, this);
  }
}
