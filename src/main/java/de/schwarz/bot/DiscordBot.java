package de.schwarz.bot;

import de.schwarz.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class DiscordBot {

	public DiscordBot(String token) {

		JDA jda = JDABuilder
				.createDefault(token)
				.setHttpClientBuilder(new OkHttpClient.Builder().readTimeout(20, TimeUnit.SECONDS))
				.enableIntents(
						GatewayIntent.GUILD_MESSAGES,
						GatewayIntent.MESSAGE_CONTENT
				)
				.build();
		try {
			jda.awaitReady();
			jda.getPresence().setActivity(Activity.watching("News"));
			jda.addEventListener(new MessageInCommandListener());

			Main.jda = jda;
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
