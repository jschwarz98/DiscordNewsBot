package de.schwarz.rss;

import org.jetbrains.annotations.Nullable;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RssTimeConverter {

	private static final List<DateTimeFormatter> formatters = new ArrayList<>();

	static {
		DateTimeFormatter dateTimeFormatterDoubleDigitDays = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");
		formatters.add(dateTimeFormatterDoubleDigitDays);

		DateTimeFormatter dateTimeFormatterSingleDigitDays = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss Z");
		formatters.add(dateTimeFormatterSingleDigitDays);

		DateTimeFormatter dateTimeFormatterDoubleDigitDaysWrittenTimeZone = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z");
		formatters.add(dateTimeFormatterDoubleDigitDaysWrittenTimeZone);

		DateTimeFormatter dateTimeFormatterSingleDigitDaysWrittenTimeZone = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z");
		formatters.add(dateTimeFormatterSingleDigitDaysWrittenTimeZone);
	}

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
			return dateTimeFormatterSingleDigitDays.localizedBy(locale).format(date);
		} catch (DateTimeException e) {
			try {
				return dateTimeFormatterTwoDigitDays.format(date);
			} catch (DateTimeException e2) {
				e.printStackTrace();
				return "";
			}
		}
	}

	@Nullable
	public static LocalDateTime parseDate(String content, Locale locale) {
		LocalDateTime result = null;

		for (int i = 0; i < formatters.size(); i++) {
			DateTimeFormatter formatter = formatters.get(i);
			try {
				result = LocalDateTime.from(formatter.parse(content));
				break;
			} catch (DateTimeParseException e4) {
				if (i == formatters.size() - 1) {
					e4.printStackTrace();
					System.out.println("Couldnt parse At: " + e4.getErrorIndex() + "  | " + content.substring(e4.getErrorIndex()) + "  | locale: " + locale);
				}
			}
		}
		return result;
	}
}
