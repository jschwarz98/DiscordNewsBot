package de.schwarz.bot.commandImplementations;

import de.schwarz.bot.commands.CommandHandlerImplementation;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingPongCommand implements CommandHandlerImplementation {
	@Override
	public void accept(MessageReceivedEvent event) {
		event.getMessage().reply("pong!").queue();
	}
}
