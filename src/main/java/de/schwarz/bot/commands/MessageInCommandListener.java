package de.schwarz.bot.commands;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageInCommandListener extends ListenerAdapter {

	private final CommandHandler commandHandler;

	public MessageInCommandListener() {
		commandHandler = new CommandHandler();
	}


	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		// Logging
		if (event.getAuthor().isBot() || event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
			System.out.println("Message from Bot: " + event.getAuthor().isBot() + " " + event.getAuthor().getName());
			return;
		}
		System.out.println("Received message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay() + " embeds: " + event.getMessage().getEmbeds().size());

		// Inhalt der Nachricht lesen
		String command = event.getMessage().getContentDisplay().split(" ")[0];
		Environment env = event.isFromType(ChannelType.PRIVATE) ? Environment.PRIVATE : Environment.PUBLIC;

		// Passenden Command raus suchen
		CommandHandlerImplementation handler = commandHandler.getHandler(command, env);

		// Command anwenden
		handler.accept(event);

	}
}