package de.schwarz.spiegel.rss;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Channel {
	@XmlElement(name = "title")
	private String title;
	@XmlElement(name = "link")
	private String link;
	@XmlElement(name = "description")
	private String description;
	@XmlElement(name = "language")
	private String language;
	@XmlElement(name = "pubDate")
	private String pubDate;
	@XmlElement(name = "lastBuildDate")
	private String lastBuildDate;
	@XmlElement(name = "image")
	private String image;
	@XmlElement(name = "item")
	private List<Item> items;
}
