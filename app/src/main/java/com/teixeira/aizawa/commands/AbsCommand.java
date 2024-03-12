package com.teixeira.aizawa.commands;

import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public abstract class AbsCommand extends CommandDataImpl implements ICommand {

  public AbsCommand(String name, String description) {
    super(name, description);
    ICommand.commands.put(name, this);
  }
}
