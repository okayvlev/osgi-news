package me.okayvlev.web.osgi.news.service.news;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "rss")
public class AifRss {
    @JacksonXmlProperty(localName = "channel")
    private AifNews news;

    public AifNews getNews() {
        return news;
    }
}
