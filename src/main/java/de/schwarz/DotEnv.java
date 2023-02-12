package de.schwarz;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class DotEnv {

	private final Map<String, String> environmentVariables;

	public DotEnv(String path) throws FileNotFoundException {
		environmentVariables = new HashMap<>();
		// Default Values, if any...
		environmentVariables.put(Key.POLLING_RATE.value, "60"); // polling every 60 seconds
		environmentVariables.put(Key.DATABASE_PATH.value, "./uuids.db");

		readDotEnvFile(path);
	}

	private void readDotEnvFile(String path) throws FileNotFoundException {
		new BufferedReader(new FileReader(path))
				.lines()
				.forEach(this::addToEnvironmentVariables);
	}

	private void addToEnvironmentVariables(String s) {
		String trimmed = s.trim();

		if (trimmed.startsWith("#")) return; // comment
		if (!trimmed.contains("=")) return; // no value assignment
		if (trimmed.indexOf("=") + 1 == trimmed.length()) return; // no value after '='

		int separator = trimmed.indexOf("=");
		String key = trimmed.substring(0, separator);
		String value = trimmed.substring(separator + 1);

		if (key.isEmpty() || value.isEmpty()) // overlooked cases
			return;

		environmentVariables.put(key, value);
	}

	public String getValue(String key) {
		return environmentVariables.getOrDefault(key, null);
	}

	public enum Key {

		DATABASE_PATH("DATABASE_PATH"),
		POLLING_RATE("POLLING_RATE_IN_SECONDS"),

		TOKEN("TOKEN"),

		NEWS_CHANNEL_ID("NEWS_CHANNEL_ID");


		public final String value;

		Key(String value) {
			this.value = value;
		}
	}

}
