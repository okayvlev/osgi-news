package me.okayvlev.web.osgi.news.client.model;

public interface StatsProvider {
    void stats();

    void stats(String source);
}
