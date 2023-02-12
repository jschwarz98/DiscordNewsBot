package de.schwarz.rss.sources.information;

import de.schwarz.rss.RssTimeConverter;
import de.schwarz.rss.xml.Enclosure;
import de.schwarz.rss.xml.Item;
import org.jetbrains.annotations.Nullable;

public interface RssInformationExtractor {

	default String getTitle(Item item) {
		return item.getTitle();
	}

	default String getDescription(Item item) {
		return item.getDescription();
	}

	default String getPublicationDate(Item item) {
		return item.getPubDate();
	}

	default String getPublicationDateFormatted(Item item) {
		return RssTimeConverter.toGermanTimeString(getPublicationDate(item));
	}

	@Nullable
	default String getImageUrl(Item item) {
		if (item.getEnclosure() == null)
			return null;
		Enclosure enclosure = item.getEnclosure();
		if (enclosure.getUrl() == null || enclosure.getUrl().isEmpty())
			return null;
		return enclosure.getUrl();
	}

	default String getGuid(Item item) {
		return item.getGuid();
	}

	default String getLink(Item item) {
		return item.getLink();
	}

}
