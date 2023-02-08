package de.schwarz.spiegel.rss;

import de.schwarz.bot.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static java.awt.Color.ORANGE;

public class NewsScraperJob {

	private final DiscordBot bot;
	private final RssDatabase db;
	private final String url;
	private final BiConsumer<Member, List<Item>> sendeArtikelEmbeds = (member, items) -> {
		if (member.getUser().isBot()) return;

		for (Item item : items) {
			member.getUser().openPrivateChannel()
					.queue(channel -> channel
							.sendMessageEmbeds(createEmbed(item))
							.addActionRow(Button.link(item.getLink().substring(0, item.getLink().indexOf("#ref=rss")), "Spiegel-Artikel"))
							.queue());
		}
	};

	public NewsScraperJob(DiscordBot bot, RssDatabase db, String url) {
		this.bot = bot;
		this.db = db;
		this.url = url;
		// <:github:849286315580719104>
	}

	public void run() throws MalformedURLException {
		System.out.println("Running job at: " + LocalDateTime.now());
		Optional<RssFeed> feed = RssFeedReader.read(new URL(url));
		List<Item> items = new ArrayList<>();
		if (feed.isEmpty()) return;
		RssFeed rssFeed = feed.get();
		rssFeed.getChannels().forEach(channel ->
				channel.getItems().forEach(item -> {
					if (db.existsUUID(item.getGuid())) return;
					db.addUUID(item.getGuid());
					items.add(item);
				})
		);
		Collections.reverse(items);
		bot.getGuild().loadMembers(m -> sendeArtikelEmbeds.accept(m, items));
	}

	private MessageEmbed createEmbed(Item item) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle(item.getTitle()).setDescription(item.getDescription()).setColor(ORANGE).setAuthor(RssTimeConverter.toGermanTimeString(item.getPubDate())).setFooter("provided by Spiegel").setImage(item.getEnclosure().getUrl());
		return builder.build();
	}
}
