package de.schwarz.spiegel.rss;

import java.sql.*;

public class RssDatabase {

	private static final String url = "jdbc:sqlite:./";
	private static RssDatabase db;
	private Connection connection;

	private RssDatabase(String file) {
		try {
			Connection conn = DriverManager.getConnection(url + file);
			DatabaseMetaData meta = conn.getMetaData();
			System.out.println("The driver name is " + meta.getDriverName());
			System.out.println("A new database has been created.");

			Statement statement = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS ITEM (uuid  TEXT PRIMARY KEY);";
			statement.executeUpdate(sql);

			connection = conn;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static RssDatabase getInstance() {
		if (db == null) throw new RuntimeException("DB muss initialisiert sein!");
		return db;
	}

	public static RssDatabase initialize(String file) {
		if (db == null) {
			db = new RssDatabase(file);
		}
		return db;
	}

	public boolean existsUUID(String uuid) {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM ITEM WHERE uuid like '" + uuid + "'");
			return resultSet.getInt(1) != 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void addUUID(String uuid) {
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO ITEM (uuid) VALUES ('" + uuid + "')");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
