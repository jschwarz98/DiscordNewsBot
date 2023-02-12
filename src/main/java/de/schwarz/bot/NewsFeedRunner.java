package de.schwarz.bot;

import de.schwarz.DotEnv;
import de.schwarz.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class NewsFeedRunner {

	private static NewsFeedRunner runner;
	private final Thread thread;
	private boolean running = false;

	private NewsFeedRunner() {
		String channelId = Main.env.getValue(DotEnv.Key.NEWS_CHANNEL_ID.value);
		String seconds = Main.env.getValue(DotEnv.Key.POLLING_RATE.value);
		long requestDelay = TimeUnit.SECONDS.toNanos(Long.parseLong(seconds));

		this.thread = new Thread(rssFeedPollingLoop(requestDelay, Main.jda, channelId));
	}

	public static NewsFeedRunner getInstance() {
		if (runner == null) {
			runner = new NewsFeedRunner();
		}
		return runner;
	}

	@NotNull
	private Runnable rssFeedPollingLoop(long requestDelay, JDA jda, String channelId) {
		return () -> {
			NewsScraperJob job = new NewsScraperJob(jda, channelId);
			while (running) {
				try {
					job.run();
					LockSupport.parkNanos(requestDelay);
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	public void startJob() {
		if (running) return;
		running = true;

		TextChannel channel = Main.jda.getTextChannelById(Main.env.getValue(DotEnv.Key.NEWS_CHANNEL_ID.value));
		if (channel != null)
			channel.sendMessage("Starting NewsRunner...").queue();

		this.thread.start();
	}

	public void stopJob() {
		if (!running) return;
		this.running = false;
		try {
			this.thread.join(100, 0);
			TextChannel channel = Main.jda.getTextChannelById(Main.env.getValue(DotEnv.Key.NEWS_CHANNEL_ID.value));
			if (channel != null)
				channel.sendMessage("Stopping NewsRunner...").queue();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}


