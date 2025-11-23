package org.project.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.Entity.Film;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
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
                    try {
                        Film film = mapper.readValue(file, Film.class);
                        filmConsumer.accept(film);
                    } catch (IOException e) {
                        System.out.println("Cannot read file: " + file.getName());
                    }
                }))
                .toList();

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception ignored) {
            }
        }
        executor.shutdown();
    }

}