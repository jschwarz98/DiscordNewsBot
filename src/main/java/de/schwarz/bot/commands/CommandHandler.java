package de.schwarz.bot.commands;

import de.schwarz.bot.commandImplementations.CommandNotFound;
import de.schwarz.bot.commandImplementations.CommandNotUnique;

import java.util.Arrays;
import java.util.List;

public class CommandHandler {

	public CommandHandler() {

	}

	public CommandHandlerImplementation getHandler(String command, Environment environment) {
		List<Command> commands = Arrays.stream(Command.values())
				.filter(c -> c.command.equalsIgnoreCase(command))
				.filter(c -> c.environment.equals(Environment.EVERYWHERE) || c.environment.equals(environment))
				.toList();

		return switch (commands.size()) {
			case 1 -> commands.get(0).handler;
			case 0 -> new CommandNotFound();
			default -> new CommandNotUnique();
		};
	}

}
