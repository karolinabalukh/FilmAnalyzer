package org.project.Service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.Entity.Film;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class FilmParser {

    public void processFilms(String folderPath, Consumer<Film> filmConsumer, int threadCount) {

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Folder not found: " + folderPath);
            return;
        }

        List<File> jsonFiles = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                .filter(f -> f.getName().endsWith(".json"))
                .toList();

        if (jsonFiles.isEmpty()) {
            System.out.println("No JSON files found in folder: " + folderPath);
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        List<? extends Future<?>> futures = jsonFiles.stream()
                .map(file -> executor.submit(() -> {
                    try (JsonParser parser = mapper.createParser(file)) {

                        MappingIterator<Film> it = mapper.readerFor(Film.class).readValues(parser);

                        while (it.hasNext()) {
                            Film film = it.next();
                            filmConsumer.accept(film);
                        }

                    } catch (IOException e) {
                        System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
                    }
                }))
                .toList();

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
            }
        }
    }
}