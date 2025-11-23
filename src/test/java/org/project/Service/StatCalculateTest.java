package org.project.Service;
import org.junit.jupiter.api.Test;
import org.project.Entity.Director;
import org.project.Entity.Film;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class StatCalculateTest {

    @Test
    void testGenreStat() {
        StatCalculate calc = new StatCalculate("genre");
        Film film1 = new Film("Film A", 2020, 100, "fantasy, drama", 7.5, "desc",
                new Director("John", "01-01-1980", "USA"));

        Film film2 = new Film("Film B", 2021, 110, "fantasy", 8.1, "desc",
                new Director("John", "01-01-1980", "USA"));
        calc.addFilm(film1);
        calc.addFilm(film2);
        Map<String, Integer> stats = calc.getStat();

        assertEquals(2, stats.get("fantasy"));
        assertEquals(1, stats.get("drama"));
    }

    @Test
    void testYearStat() {
        StatCalculate calc = new StatCalculate("year");

        Film f1 = new Film("A", 2020, 90, "crime", 6.5, "desc", null);
        Film f2 = new Film("B", 2020, 120, "crime", 6.0, "desc", null);
        Film f3 = new Film("C", 2019, 140, "crime", 8.0, "desc", null);
        calc.addFilm(f1);
        calc.addFilm(f2);
        calc.addFilm(f3);
        Map<String, Integer> stats = calc.getStat();

        assertEquals(2, stats.get("2020"));
        assertEquals(1, stats.get("2019"));
    }

    @Test
    void testUnknownAttribute() {
        StatCalculate calc = new StatCalculate("unknown");

        Film film = new Film("X", 2020, 90, "crime", 6.0, "desc",
                new Director("A", "1", "US"));
        calc.addFilm(film);

        Map<String, Integer> stats = calc.getStat();
        assertTrue(stats.isEmpty());
    }

    @Test
    void testCountryStat() {
        StatCalculate calc = new StatCalculate("country");

        Film f1 = new Film("A", 2020, 100, "crime", 6.0, "d",
                new Director("Bob", "1", "USA"));
        Film f2 = new Film("B", 2021, 110, "fantasy", 7.0, "d",
                new Director("John", "1", "USA"));
        Film f3 = new Film("C", 2022, 120, "drama", 5.0, "d",
                new Director("Ken", "1", "Canada"));
        calc.addFilm(f1);
        calc.addFilm(f2);
        calc.addFilm(f3);

        Map<String,Integer> stats = calc.getStat();
        assertEquals(2, stats.get("USA"));
        assertEquals(1, stats.get("Canada"));
    }
}
