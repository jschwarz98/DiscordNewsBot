package de.schwarz.rss.sources;

import de.schwarz.rss.sources.information.GameStarInformationExtractor;
import de.schwarz.rss.sources.information.RssInformationExtractor;
import de.schwarz.rss.xml.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class GameStarInformationExtractorTest {

	@Test
	public void testDescription() {
		RssInformationExtractor ex = new GameStarInformationExtractor();
		Item item = new Item();
		item.setDescription("<a href=\"https://www.gamestar.de/artikel/hogwarts-legacy-steam-rekord,3389864.html%22%3E<img align=\"left\" hspace=\"5\" src=\"https://images.cgames.de/images/gamestar/112/hogwarts-legacy-steam-gs_6217198.jpg%22/%3E</a>Hogwarts Legacy ist eins der erfolgreichsten Singleplayer-Spiele auf Steam. Was gleichzeitige Spielerzahlen angeht, kommt das Harry Potter-RPG nur an einen Konkurrenten nicht ran.");
		System.out.println(ex.getDescription(item));
		assertFalse(ex.getDescription(item).contains("</a>"));


	}

}