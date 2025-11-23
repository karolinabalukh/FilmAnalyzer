package org.project.Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.project.Entity.Film;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class FilmParserTest {

    @TempDir
    File tempDir;

    @Test
    void testParseOneFile() throws Exception {

        File json = new File(tempDir, "film.json");

        try (FileWriter fw = new FileWriter(json)) {
            fw.write("""
                    {
                       "title": "Test Movie",
                       "year": 2022,
                       "duration": 120,
                       "genre": "fantasy",
                       "rating": 7.8,
                       "description": "desc",
                       "director": {
                         "name": "John",
                         "birthDate": "01-01-1980",
                         "country": "USA"
                       }
                    }
                    """);
        }

        FilmParser parser = new FilmParser();
        AtomicReference<Film> ref = new AtomicReference<>();

        parser.processFilms(tempDir.getAbsolutePath(), ref::set, 1);

        Film film = ref.get();
        assertNotNull(film);
        assertEquals("Test Movie", film.getTitle());
        assertEquals(2022, film.getYear());
        assertEquals("fantasy", film.getGenre());
        assertEquals("John", film.getDirector().getName());
    }

    @Test
    void testEmptyFolder(@TempDir File tempDir) {
        FilmParser parser = new FilmParser();
        AtomicReference<Film> ref = new AtomicReference<>();

        parser.processFilms(tempDir.getAbsolutePath(), ref::set, 1);
        assertNull(ref.get());
    }

    @Test
    void testBrokenJson(@TempDir File tempDir) throws Exception {
        File json = new File(tempDir, "broken.json");

        try (FileWriter fw = new FileWriter(json)) {
            fw.write("{ invalid json }");
        }

        FilmParser parser = new FilmParser();
        AtomicReference<Film> ref = new AtomicReference<>();
        parser.processFilms(tempDir.getAbsolutePath(), ref::set, 1);
        assertNull(ref.get());
    }

    @Test
    void testMultipleFiles(@TempDir File tempDir) throws Exception {
        for (int i = 1; i <= 3; i++) {
            File f = new File(tempDir, "film" + i + ".json");
            try (FileWriter fw = new FileWriter(f)) {
                fw.write(("""
                    {
                     "title": "Movie %d",
                     "year": 2000,
                     "duration": 100,
                     "genre": "drama",
                     "rating": 7.0,
                     "description": "d",
                     "director": { "name": "A", "birthDate": "1", "country": "US" }
                    }
                    """.formatted(i)).replace("\n", ""));
            }
        }

        FilmParser parser = new FilmParser();
        List<String> titles = new ArrayList<>();
        parser.processFilms(tempDir.getAbsolutePath(), film -> titles.add(film.getTitle()), 4);
        assertEquals(3, titles.size());
    }
}
