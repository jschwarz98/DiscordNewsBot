package de.schwarz.bot.commands;

import de.schwarz.bot.commandImplementations.PingPongCommand;

public enum Command {

	PING_PONG("!ping", Environment.EVERYWHERE, new PingPongCommand());

	public final String command;
	public final Environment environment;
	public final CommandHandlerImplementation handler;

	Command(String command, Environment environment, CommandHandlerImplementation implementation) {
		this.command = command;
		this.environment = environment;
		this.handler = implementation;
	}

}
