package de.schwarz.bot;

import de.schwarz.DotEnv;
import de.schwarz.Main;
import de.schwarz.bot.commands.MessageInCommandListener;
import de.schwarz.spiegel.rss.NewsScraperJob;
import de.schwarz.spiegel.rss.RssDatabase;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class DiscordBot {

	public boolean running = true;
	@Getter
	private Guild guild;

	public DiscordBot(String token, String serverId) {
		JDA jdaConnection = JDABuilder
				.createDefault(token)
				.enableIntents(
						GatewayIntent.GUILD_MEMBERS,
						GatewayIntent.GUILD_MESSAGES
				)
				.build();
		try {
			jdaConnection.awaitReady();
			jdaConnection.getPresence().setActivity(Activity.watching("Spiegel-Schlagzeilen"));
			jdaConnection.addEventListener(new MessageInCommandListener());
			guild = jdaConnection.getGuildById(serverId);

			System.out.println("Client is Ready!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		startNewsFeedTracking();
	}

	private void startNewsFeedTracking() {
		Thread newsFeed = new Thread(() -> {
			NewsScraperJob job = new NewsScraperJob(this, RssDatabase.getInstance(), Main.env.getValue(DotEnv.Key.RSS_FEED_SOURCE.value));
			while (running) {
				try {
					job.run();
					long seconds = 1000;
					LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(60 * seconds));
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
			}
		});
		newsFeed.start();
	}
}
