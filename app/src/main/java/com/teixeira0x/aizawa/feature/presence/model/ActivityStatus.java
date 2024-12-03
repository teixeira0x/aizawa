package com.teixeira0x.aizawa.feature.presence.model;

import static net.dv8tion.jda.api.entities.Activity.ActivityType;

public class ActivityStatus {
  private final ActivityType type;
  private final String name;

  public ActivityStatus(ActivityType type, String name) {
    this.type = type;
    this.name = name;
  }

  public ActivityType getType() {
    return this.type;
  }

  public String getName() {
    return this.name;
  }
}
