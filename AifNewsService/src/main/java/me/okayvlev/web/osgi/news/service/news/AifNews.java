package me.okayvlev.web.osgi.news.service.news;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import me.okayvlev.web.osgi.news.api.news.Article;
import me.okayvlev.web.osgi.news.api.news.News;

import java.util.List;

public class AifNews implements News {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "item")
    @JsonDeserialize(contentAs = AifArticle.class)
    private List<Article> articles;

    @Override
    public List<Article> getArticles() {
        return articles;
    }
}
