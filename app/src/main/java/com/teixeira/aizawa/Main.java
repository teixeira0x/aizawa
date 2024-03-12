package com.teixeira.aizawa;

import com.teixeira.aizawa.commands.ICommand;
import com.teixeira.aizawa.commands.common.PingCommand;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {

  private static Dotenv dotenv;
  private static Client client;

  private static final ICommand[] commands = new ICommand[] {
    new PingCommand()
  };

  public static void main(String[] args) {
    dotenv = Dotenv.load();
    client = new Client();
  }

  public static Dotenv dotenv() {
    return dotenv;
  }

  public static Client client() {
    return client;
  }

  public static ICommand[] getCommands() {
    return commands;
  }
}
