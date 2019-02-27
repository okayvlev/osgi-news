package me.okayvlev.web.osgi.news.client;

import me.okayvlev.web.osgi.news.client.model.DefaultStatsProvider;
import me.okayvlev.web.osgi.news.client.model.StatsProvider;
import me.okayvlev.web.osgi.news.client.model.NewsRepository;
import org.apache.felix.scr.annotations.*;
import org.osgi.framework.BundleContext;

@Component(immediate = true)
@Service
@Properties({
        @Property(name = "osgi.command.scope", value = "news"),
        @Property(name = "osgi.command.function", value = "stats")
})
public class NewsClientActivator implements StatsProvider {
    private StatsProvider statsProvider;

    @Activate
    public void start(BundleContext context) throws Exception {
        System.out.println("News Service started");
        statsProvider = new DefaultStatsProvider(new NewsRepository(context));
    }

    @Deactivate
    public void stop(BundleContext context) throws Exception {
        statsProvider = null;
        System.out.println("News Service stopped");
    }

    @Override
    public void stats() {
        if (isRunning()) {
            statsProvider.stats();
        }
    }

    @Override
    public void stats(String source) {
        try {
            if (isRunning()) {
                statsProvider.stats(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isRunning() {
        boolean result = statsProvider != null;

        if (!result) {
            System.out.println("Service is not running");
        }

        return result;
    }
}
