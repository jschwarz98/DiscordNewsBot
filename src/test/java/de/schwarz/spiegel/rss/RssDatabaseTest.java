package de.schwarz.spiegel.rss;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

class RssDatabaseTest {

	private static RssDatabase db;

	@BeforeAll
	public static void before() {
		File file = new File("./test.db");
		if (file.exists()) {
			Assertions.assertTrue(file.delete());
		}
		db = RssDatabase.initialize("test.db");
	}

	@Test
	public void dbTest() {
		db.addUUID("123");
		Assertions.assertTrue(db.existsUUID("123"), "123 exists");
		Assertions.assertFalse(db.existsUUID("1234"), "1234 does not exist");

	}

}