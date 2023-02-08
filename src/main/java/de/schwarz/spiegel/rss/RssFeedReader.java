package de.schwarz.spiegel.rss;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.util.Optional;

public class RssFeedReader {
	private static final Unmarshaller unmarshaller;

	static {
		try {
			unmarshaller = JAXBContext.newInstance(RssFeed.class).createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	private RssFeedReader() {
	}

	public static Optional<RssFeed> read(URL url) {
		try {
			return Optional.of((RssFeed) unmarshaller.unmarshal(url));
		} catch (JAXBException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
}
