package de.schwarz.bot.commandImplementations;

import de.schwarz.bot.commands.CommandHandlerImplementation;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandNotUnique implements CommandHandlerImplementation {
	@Override
	public void accept(MessageReceivedEvent event) {
		event.getMessage().reply("Befehl konnte nicht eindeutig bestimmt werden").queue();
	}
}
