package me.okayvlev.web.osgi.news.service.news;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import me.okayvlev.web.osgi.news.api.news.Article;
import me.okayvlev.web.osgi.news.api.news.News;

import java.util.ArrayList;
import java.util.List;

public class LentaNews implements News {
    @JsonProperty(value = "headlines")
    @JsonDeserialize(contentAs = LentaArticle.class)
    private List<Article> articles;

    @Override
    public List<Article> getArticles() {
        return new ArrayList<>(articles);
    }
}
