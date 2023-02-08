package de.schwarz.spiegel.rss;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Enclosure {
	@XmlAttribute(name = "type")
	private String type;

	@XmlAttribute(name = "url")
	private String url;
}
