package de.schwarz.bot.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@FunctionalInterface
public interface CommandHandler {
	void accept(MessageReceivedEvent event);
}
