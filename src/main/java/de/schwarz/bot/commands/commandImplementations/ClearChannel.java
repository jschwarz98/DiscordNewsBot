package de.schwarz.bot.commands.commandImplementations;

import de.schwarz.Main;
import de.schwarz.bot.commands.CommandHandler;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

import static de.schwarz.DotEnv.Key.NEWS_CHANNEL_ID;

public class ClearChannel implements CommandHandler {
	@Override
	public void accept(MessageReceivedEvent event) {
		if (!event.getChannel().getId().equals(Main.env.getValue(NEWS_CHANNEL_ID.value))) {
			event.getChannel().sendMessage("This Command is only usable in the news channel!").queue();
			return;
		}
		Member bot = event.getGuild().getSelfMember();
		if (!bot.hasPermission(event.getChannel().asGuildMessageChannel(), Permission.MESSAGE_HISTORY)) {
			System.out.println("Keine Befugnis Nachrichten-Historie einzusehen. 'MESSAGE_HISTORY' fehlt");
			return;
		}
		if (!bot.hasPermission(event.getChannel().asGuildMessageChannel(), Permission.MESSAGE_MANAGE)) {
			System.out.println("Keine Befugnis Nachrichten zu löschen. 'MESSAGE_MANAGE' fehlt");
			return;
		}
		TextChannel c = event.getChannel().asTextChannel();
		c.getLatestMessageId();
		c.getHistory()
				.retrievePast(100)
				.queue(messages -> messages.forEach(message -> message.delete().queue()));
		c.sendMessage("Deleting up to 100 Messages now...").complete().delete().queueAfter(10, TimeUnit.SECONDS);
	}

}
