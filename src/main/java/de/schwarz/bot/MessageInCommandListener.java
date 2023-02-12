package de.schwarz.bot;

import de.schwarz.bot.commands.CommandDelegator;
import de.schwarz.bot.commands.CommandHandler;
import de.schwarz.bot.commands.Environment;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageInCommandListener extends ListenerAdapter {

	private final CommandDelegator commandDelegator;

	public MessageInCommandListener() {
		commandDelegator = new CommandDelegator();
	}


	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot()) {
			System.out.println("Message from Bot: " + event.getAuthor().isBot() + " " + event.getAuthor().getName());
			return;
		}

		System.out.println("Received message from " + event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay() + " | embeds: " + event.getMessage().getEmbeds().size());

		String command = event.getMessage().getContentDisplay().split(" ")[0];
		Environment env = event.isFromType(ChannelType.PRIVATE) ? Environment.PRIVATE : Environment.PUBLIC;

		CommandHandler handler = commandDelegator.getHandler(command, env);

		handler.accept(event);

	}
}