package me.okayvlev.web.osgi.news.client.model;

import me.okayvlev.web.osgi.news.client.model.repository.NewsRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultStatsProvider implements StatsProvider {
    private final String ALL_SOURCES_OPTION = "all";
    private final int TOP_LIST_SIZE = 10;

    private NewsRepository repository;

    public DefaultStatsProvider(NewsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void stats() {
        repository.updateServices();
        List<String> sources = getAvailableSourcesVerbose();

        if (sources.isEmpty()) {
            return;
        }

        sources.add(ALL_SOURCES_OPTION);

        System.out.println("Available options: ");
        sources.forEach(System.out::println);
    }

    @Override
    public void stats(String source) {
        if (source == null) {
            return;
        }

        repository.updateServices();

        if (source.equals(ALL_SOURCES_OPTION)) {
            List<String> sources = getAvailableSourcesVerbose();

            if (sources.isEmpty()) {
                return;
            }

            processStats(sources.parallelStream()
                                .map(repository::getTitles)
                                .flatMap(Collection::stream)
                                .collect(Collectors.toList()));
            return;
        }

        if (!repository.getAvailableSources().contains(source)) {
            System.out.println("No such source exists");
            stats();
            return;
        }

        processStats(repository.getTitles(source));
    }

    private List<String> getAvailableSourcesVerbose() {
        List<String> sources = repository.getAvailableSources();

        if (sources.isEmpty()) {
            System.out.println("No available sources");
            return Collections.emptyList();
        }

        System.out.println("Total " + sources.size() + " sources available");

        return sources;
    }

    private void processStats(List<String> titles) {
        if (titles.isEmpty()) {
            System.out.println("No news");
            return;
        }

        System.out.println("Top " + TOP_LIST_SIZE + " words in the news headlines right now: ");
        titles.stream()
              .map(title -> title.split("\\P{L}+"))
              .flatMap(Arrays::stream)
              .filter(str -> !str.isEmpty())
              .map(String::toLowerCase)
              .collect(Collectors.groupingBy(Function.identity(),
                                             Collectors.counting()))
              .entrySet().stream()
              .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
              .limit(TOP_LIST_SIZE)
              .forEach(this::outputEntry);
    }

    private void outputEntry(Map.Entry<String, Long> entry) {
        System.out.printf("%2d times: %s\n", entry.getValue(), entry.getKey());
    }
}
