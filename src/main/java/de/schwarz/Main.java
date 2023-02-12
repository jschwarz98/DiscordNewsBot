package de.schwarz;

import de.schwarz.bot.DiscordBot;
import de.schwarz.rss.RssDatabase;
import net.dv8tion.jda.api.JDA;

import java.io.FileNotFoundException;
import java.util.Random;

import static de.schwarz.DotEnv.Key.DATABASE_PATH;
import static de.schwarz.DotEnv.Key.TOKEN;

public class Main {
	public static Random r = new Random();

	public static DotEnv env;
	public static JDA jda;

	public static void main(String[] args) {
		readDotEnv();

		RssDatabase.initialize(env.getValue(DATABASE_PATH.value));

		String token = env.getValue(TOKEN.value);
		new DiscordBot(token);
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