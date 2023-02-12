package de.schwarz.bot.commands;

import de.schwarz.bot.commands.commandImplementations.ClearChannel;
import de.schwarz.bot.commands.commandImplementations.PingPongCommand;
import de.schwarz.bot.commands.commandImplementations.StartNewsFeed;
import de.schwarz.bot.commands.commandImplementations.StopNewsFeed;

public enum Command {
	START_FEED("!start", Environment.PUBLIC, new StartNewsFeed()),
	STOP_FEED("!stop", Environment.PUBLIC, new StopNewsFeed()),
	CLEAR_CHANNEL("!clear", Environment.EVERYWHERE, new ClearChannel()),
	PING_PONG("!ping", Environment.EVERYWHERE, new PingPongCommand());

	public final String command;
	public final Environment environment;
	public final CommandHandler handler;

	Command(String command, Environment environment, CommandHandler implementation) {
		this.command = command;
		this.environment = environment;
		this.handler = implementation;
	}

}
