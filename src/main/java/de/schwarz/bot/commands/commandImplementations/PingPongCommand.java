package de.schwarz.bot.commands.commandImplementations;

import de.schwarz.bot.commands.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingPongCommand implements CommandHandler {
	@Override
	public void accept(MessageReceivedEvent event) {
		event.getMessage().reply("pong!").queue();
	}
}
