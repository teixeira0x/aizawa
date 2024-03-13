package com.teixeira.aizawa.commands.roleplay;

import com.teixeira.aizawa.commands.AbsCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class RoleplayCommands extends AbsCommand {

  public RoleplayCommands() {
    super("roleplay", "Comandos relacionados a roleplay");

    addSubcommands(new KissCommand(), new HugCommand(), new PunchCommand());
  }

  @Override
  public void run(SlashCommandInteractionEvent event) {}
}
