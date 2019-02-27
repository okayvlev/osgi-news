package me.okayvlev.web.osgi.news.service.news;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.okayvlev.web.osgi.news.api.news.Article;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LentaArticle implements Article {
    private String title;

    @JsonProperty(value = "info")
    private void unpackTitle(Map<String, Object> info) {
        title = (String) info.get("title");
    }

    @Override
    public String getTitle() {
        return title;
    }
}
