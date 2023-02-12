package de.schwarz.bot.commands.commandImplementations;

import de.schwarz.DotEnv;
import de.schwarz.Main;
import de.schwarz.bot.NewsFeedRunner;
import de.schwarz.bot.commands.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StartNewsFeed implements CommandHandler {
	@Override
	public void accept(MessageReceivedEvent event) {
		String channelId = event.getChannel().getId();
		if (!channelId.equals(Main.env.getValue(DotEnv.Key.NEWS_CHANNEL_ID.value))) return;

		NewsFeedRunner.getInstance().startJob();
	}
}
