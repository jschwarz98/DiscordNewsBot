package de.schwarz.bot;

import de.schwarz.rss.RssDatabase;
import de.schwarz.rss.RssFeedReader;
import de.schwarz.rss.sources.RssFeedSources;
import de.schwarz.rss.sources.information.RssInformationExtractor;
import de.schwarz.rss.xml.Channel;
import de.schwarz.rss.xml.Item;
import de.schwarz.rss.xml.RssFeed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.awt.Color.ORANGE;

public class NewsScraperJob {

	private final JDA jda;
	private final RssDatabase db;
	private final String targetChannelId;

	public NewsScraperJob(JDA jda, String targetChannelId) {
		this.jda = jda;
		this.targetChannelId = targetChannelId;

		this.db = RssDatabase.getInstance();
	}

	public void run() throws MalformedURLException {
		System.out.println("Running job at: " + LocalDateTime.now());

		for (RssFeedSources newsSource : RssFeedSources.values()) {
			List<Item> items = loadRssFeed(newsSource.url);
			sendNews(items, newsSource);
		}
	}

	private void sendNews(List<Item> items, RssFeedSources newsSource) {
		items.forEach(item -> {
			MessageEmbed embed = createEmbed(item, newsSource);
			List<ItemComponent> actionButtons = createButtons(item, newsSource);

			TextChannel channel = jda.getTextChannelById(this.targetChannelId);
			if (channel == null)
				throw new RuntimeException("Couldnt find TargetChannelId: " + this.targetChannelId);

			channel.sendMessageEmbeds(embed)
					.addActionRow(actionButtons)
					.queue(m -> db.addUUID(newsSource.extractor.getGuid(item)),
							Throwable::printStackTrace);

		});
	}

	private List<ItemComponent> createButtons(Item item, RssFeedSources newsSite) {
		List<ItemComponent> buttons = new ArrayList<>();

		Button articleButton = Button
				.link(newsSite.extractor.getLink(item), "Artikel")
				.withEmoji(Emoji.fromUnicode("U+1F4F0")); // newspaper emoji see @ https://unicode.org/emoji/charts/full-emoji-list.html
		buttons.add(articleButton);

		Button githubButton = Button
				.link("https://github.com/jschwarz98/DiscordNewsBot", "Github-Projekt")
				.withEmoji(Emoji.fromFormatted("<:github:849286315580719104>"));
		buttons.add(githubButton);

		return buttons;
	}

	@NotNull
	private List<Item> loadRssFeed(String url) throws MalformedURLException {
		Optional<RssFeed> feed = RssFeedReader.read(new URL(url));
		List<Item> items = new ArrayList<>();
		if (feed.isEmpty())
			return new ArrayList<>();

		RssFeed rssFeed = feed.get();
		for (Channel channel : rssFeed.getChannels()) {
			for (Item item : channel.getItems()) {
				if (db.existsUUID(item.getGuid()))
					continue;
				items.add(item);
			}
		}
		Collections.reverse(items);
		return items;
	}

	private MessageEmbed createEmbed(Item item, RssFeedSources newsSite) {
		RssInformationExtractor ex = newsSite.extractor;

		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle(ex.getTitle(item))
				.setDescription(ex.getDescription(item))
				.setAuthor(ex.getPublicationDateFormatted(item))
				.setFooter("provided by " + newsSite.name())
				.setColor(ORANGE);

		String imageUrl = ex.getImageUrl(item);
		if (imageUrl != null)
			builder.setImage(imageUrl);

		return builder.build();
	}
}
