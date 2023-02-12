package de.schwarz.rss.sources;

import de.schwarz.rss.sources.information.DefaultRssFeedInformationExtractor;
import de.schwarz.rss.sources.information.GameStarInformationExtractor;
import de.schwarz.rss.sources.information.RssInformationExtractor;

public enum RssFeedSources {
	Spiegel("https://www.spiegel.de/schlagzeilen/index.rss"),
	GameStar("https://www.gamestar.de/news/rss/news.rss", new GameStarInformationExtractor()),
	;

	public final String url;
	public final RssInformationExtractor extractor;

	RssFeedSources(String url) {
		this.url = url;
		this.extractor = new DefaultRssFeedInformationExtractor();
	}

	RssFeedSources(String url, RssInformationExtractor extractor) {
		this.url = url;
		this.extractor = extractor;
	}
}
