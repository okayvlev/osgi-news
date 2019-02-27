package me.okayvlev.web.osgi.news.service.news;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import me.okayvlev.web.osgi.news.api.news.Article;

public class AifArticle implements Article {
    @JacksonXmlProperty(localName = "title")
    private String title;

    @Override
    public String getTitle() {
        return title;
    }
}
