package me.okayvlev.web.osgi.news.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.okayvlev.web.osgi.news.api.NewsService;
import me.okayvlev.web.osgi.news.api.news.News;
import me.okayvlev.web.osgi.news.service.net.SendHttpRequest;
import me.okayvlev.web.osgi.news.service.news.LentaNews;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.BundleContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Service
public class LentaNewsService implements NewsService {
    private static final String API_LINK = "https://api.lenta.ru/lists/latest";
    private ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public String getName() {
        return "lenta";
    }

    @Override
    public News getNews() throws IOException {
        String data = new SendHttpRequest(API_LINK).withTimeout(5, TimeUnit.SECONDS)
                                                   .execute();
        return jsonMapper.readValue(data, LentaNews.class);
    }

    @Activate
    public void start(BundleContext context) {
        System.out.println("Starting Lenta service");
    }

    @Deactivate
    public void stop(BundleContext context) {
        System.out.println("Stopping Lenta service");
    }
}
