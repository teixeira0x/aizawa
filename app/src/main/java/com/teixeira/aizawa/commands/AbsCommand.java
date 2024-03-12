package com.teixeira.aizawa.commands;

import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public abstract class AbsCommand extends CommandDataImpl implements ICommand {

  public AbsCommand(Command.Type type, String name) {
    super(type, name);

    ICommand.commands.put(name, this);
  }

  public AbsCommand(String name, String description) {
    super(name, description);

    ICommand.commands.put(name, this);
  }
}
