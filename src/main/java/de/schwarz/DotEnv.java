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
		readDotEnvFile(path);
	}

	private void readDotEnvFile(String path) throws FileNotFoundException {
		new BufferedReader(new FileReader(path))
				.lines()
				.forEach(this::addToEnvironmentVariables);
	}

	private void addToEnvironmentVariables(String s) {
		int equalsIndex = s.indexOf("=");
		String key = s.substring(0, equalsIndex);
		String value = s.substring(equalsIndex + 1);
		if (key.isEmpty() || value.isEmpty())
			return;
		environmentVariables.put(key, value);
	}

	public String getValue(String key) {
		return environmentVariables.getOrDefault(key, null);
	}

	public enum Key {
		TOKEN("TOKEN"),
		NEWS_SERVER("NEWS_SERVER"),
		RSS_FEED_SOURCE("RSS_FEED_SOURCE");


		public final String value;

		Key(String value) {
			this.value = value;
		}
	}

}
