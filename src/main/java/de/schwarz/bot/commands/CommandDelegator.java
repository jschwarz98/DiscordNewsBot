package de.schwarz.bot.commands;

import java.util.Arrays;
import java.util.List;

public class CommandDelegator {

	public CommandDelegator() {

	}

	public CommandHandler getHandler(String command, Environment environment) {
		List<Command> commands = Arrays.stream(Command.values())
				.filter(c -> c.command.equalsIgnoreCase(command))
				.filter(c -> c.environment.equals(Environment.EVERYWHERE) || c.environment.equals(environment))
				.toList();

		return switch (commands.size()) {
			case 0 -> (event) -> {
			}; // do nothing.
			case 1 -> commands.get(0).handler;
			default ->
					(event) -> event.getMessage().reply("Could not determine which command to use... please consult with the dev team!").queue();
		};
	}

}
