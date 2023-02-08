package de.schwarz.spiegel.rss;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class RssFeed {
	@XmlElement(name = "channel")
	private List<Channel> channels;
}
