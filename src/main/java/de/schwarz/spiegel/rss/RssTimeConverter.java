package de.schwarz.spiegel.rss;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class RssTimeConverter {

	/**
	 * used to format an EN time string to DE time string using the pattern "EEE, dd MMM yyyy HH:mm:ss Z" -> "EEE, dd MMM yyyy HH:mm:ss"
	 *
	 * @param time The Time-String in this pattern: "EEE, dd MMM yyyy HH:mm:ss Z"
	 * @return a formatted Time-String: "EEE, dd MMM yyyy HH:mm:ss"
	 */
	public static String toGermanTimeString(String time) {
		return parseDate(parseDate(time, Locale.ENGLISH), Locale.GERMAN);
	}

	public static String parseDate(LocalDateTime date, Locale locale) {
		DateTimeFormatter dateTimeFormatterSingleDigitDays = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss", locale);
		DateTimeFormatter dateTimeFormatterTwoDigitDays = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss", locale);

		try {
			return dateTimeFormatterSingleDigitDays.format(date);
		} catch (DateTimeException e) {
			try {
				return dateTimeFormatterTwoDigitDays.format(date);
			} catch (DateTimeException e2) {
				e.printStackTrace();
				return "";
			}
		}
	}

	public static LocalDateTime parseDate(String content, Locale locale) {
		DateTimeFormatter dateTimeFormatterDoubleDigitDays = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", locale);
		DateTimeFormatter dateTimeFormatterSingleDigitDays = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss Z", locale);

		LocalDateTime result = null;

		try {
			result = LocalDateTime.from(dateTimeFormatterDoubleDigitDays.parse(content));
		} catch (DateTimeParseException e) {
			try {
				result = LocalDateTime.from(dateTimeFormatterSingleDigitDays.parse(content));
			} catch (DateTimeParseException error) {
				error.printStackTrace();
				System.out.println("Couldnt parse At: " + error.getErrorIndex() + "  | " + content.substring(error.getErrorIndex()) + "  | locale: " + locale);
			}

		}
		return result;

	}
}
