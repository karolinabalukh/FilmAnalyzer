package org.project.Service;

import org.project.Entity.Film;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class StatCalculate {
    private final Map<String, Integer> map = new ConcurrentHashMap<>();
    private final String attribute;

    public StatCalculate(String attribute) {
        this.attribute = attribute;
    }

    public void addFilm(Film film) {
        List<String> values = extractValues(film, attribute);
        for (String value : values) {
            map.merge(value, 1, Integer::sum);
        }
    }

    private List<String> extractValues(Film film, String attribute) {
        List<String> list = new ArrayList<>();

        switch (attribute) {
            case "genre" -> {
                if (film.getGenre() != null) {
                    list.addAll(Arrays.asList(film.getGenre().split(",")));
                }
            }
            case "year" -> list.add(String.valueOf(film.getYear()));
            case "director" -> {
                if (film.getDirector() != null) {
                    list.add(film.getDirector().getName());
                }
            }
            case "country" -> {
                if (film.getDirector() != null) {
                    list.add(film.getDirector().getCountry());
                }
            }
            default -> {}
        }
        list.replaceAll(String::trim);
        return list;
    }

    private Map<String, Integer> sortDesc(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

        list.sort((a, b) -> b.getValue() - a.getValue());

        Map<String, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> e : list) {
            result.put(e.getKey(), e.getValue());
        }

        return result;
    }

    public Map<String, Integer> getStat() {
        return sortDesc(map);
    }
}