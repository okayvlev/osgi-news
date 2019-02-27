package me.okayvlev.web.osgi.news.client.model.repository;

import me.okayvlev.web.osgi.news.api.NewsService;
import me.okayvlev.web.osgi.news.api.news.Article;
import me.okayvlev.web.osgi.news.api.news.News;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NewsRepository {
    private Map<String, NewsService> services = new ConcurrentHashMap<>();
    private ServiceTracker<NewsService, NewsService> tracker;

    public NewsRepository(BundleContext context) {
        tracker = new ServiceTracker<>(context, NewsService.class.getName(), null);
        tracker.open();
    }

    public List<String> getAvailableSources() {
        return new ArrayList<>(services.keySet());
    }

    public void updateServices() {
        services.clear();
        NewsService[] trackerServices = tracker.getServices(new NewsService[0]);
        Stream.of(trackerServices)
              .filter(Objects::nonNull)
              .forEach(service -> services.put(service.getName(), service));
    }

    public List<String> getTitles(String source) {
        return Optional.ofNullable(services.get(source))
                       .map(service -> {
                           try {
                               return service.getNews();
                           } catch (IOException e) {
                               System.out.println("Couldn't retrieve data from service " +
                                                          service.getName());
                               return null;
                           }
                       })
                       .map(News::getArticles)
                       .orElseGet(Collections::emptyList)
                       .stream()
                       .map(Article::getTitle)
                       .collect(Collectors.toList());
    }
}
