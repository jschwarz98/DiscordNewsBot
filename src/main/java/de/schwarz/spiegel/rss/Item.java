package de.schwarz.spiegel.rss;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Item {
	@XmlElement(name = "title")
	private String title;
	@XmlElement(name = "link")
	private String link;
	@XmlElement(name = "description")
	private String description;
	@XmlElement(name = "category")
	private String category;
	@XmlElement(name = "enclosure")
	private Enclosure enclosure;
	@XmlElement(name = "guid")
	private String guid;
	@XmlElement(name = "pubDate")
	private String pubDate;
	@XmlElement(name = "content:encoded")
	private String contentEncoded;
}
