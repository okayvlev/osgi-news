package me.okayvlev.web.osgi.news.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import me.okayvlev.web.osgi.news.api.NewsService;
import me.okayvlev.web.osgi.news.api.news.News;
import me.okayvlev.web.osgi.news.service.net.SendHttpRequest;
import me.okayvlev.web.osgi.news.service.news.AifRss;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.BundleContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Component
@Service
public class AifNewsService implements NewsService {
    private static final String API_LINK = "http://www.aif.ru/rss/news.php";
    private XmlMapper xmlMapper = new XmlMapper();

    @Override
    public String getName() {
        return "aif";
    }

    @Override
    public News getNews() throws IOException {
        String data = new SendHttpRequest(API_LINK).withTimeout(10, TimeUnit.SECONDS)
                                                   .execute();
        xmlMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
        AifRss rss = xmlMapper.readValue(data, AifRss.class);
        return rss.getNews();
    }

    @Activate
    public void start(BundleContext context) {
        System.out.println("Starting Aif service");
    }

    @Deactivate
    public void stop(BundleContext context) {
        System.out.println("Stopping Aif service");
    }
}
