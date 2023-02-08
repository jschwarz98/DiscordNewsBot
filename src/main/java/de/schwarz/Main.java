package de.schwarz;

import de.schwarz.bot.DiscordBot;
import de.schwarz.spiegel.rss.RssDatabase;

import java.io.FileNotFoundException;
import java.util.Random;

public class Main {
	public static Random r = new Random();

	public static DotEnv env;

	public static void main(String[] args) {
		readDotEnv();
		String token = env.getValue(DotEnv.Key.TOKEN.value);
		String serverId = env.getValue(DotEnv.Key.NEWS_SERVER.value);
		RssDatabase.initialize("uuids.db");
		DiscordBot discordBot = new DiscordBot(token, serverId);
	}

	private static void readDotEnv() {
		try {
			env = new DotEnv("./.env");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Add a .env file!");
			System.exit(1);
		}
	}
}