package de.schwarz.rss.sources.information;

import de.schwarz.rss.xml.Item;

public class GameStarInformationExtractor implements RssInformationExtractor {
	@Override
	public String getDescription(Item item) {
		String endingTag = "</a>";
		if (item.getDescription().contains(endingTag))
			return item.getDescription().substring(item.getDescription().indexOf(endingTag) + endingTag.length());
		return RssInformationExtractor.super.getDescription(item);
	}

}
