package me.okayvlev.web.osgi.news.api;

import me.okayvlev.web.osgi.news.api.news.News;

import java.io.IOException;
import java.net.MalformedURLException;

public interface NewsService {
    String getName();

    News getNews() throws IOException;
}
