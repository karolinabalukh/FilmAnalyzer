package org.project;

import org.project.Service.FilmParser;
import org.project.Service.StatCalculate;
import org.project.Util.XmlWriter;

import java.io.IOException;
import java.util.Map;

public class FilmStatisticsApp {

    private static final int[] THREAD_COUNTS = {1, 2, 4, 8};

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("Usage: java -jar FilmAnalyzer.jar <folder_path> <attribute>");
            System.out.println("Example: java -jar FilmAnalyzer.jar films genre");
            return;
        }

        String folderPath = args[0];
        String attribute = args[1];

        Map<String, Integer> finalStats = null;

        for (int threads : THREAD_COUNTS) {
            FilmParser parser = new FilmParser();
            StatCalculate calc = new StatCalculate(attribute);
            long start = System.currentTimeMillis();
            parser.processFilms(folderPath, calc::addFilm, threads);
            long end = System.currentTimeMillis();
            System.out.println("Threads: " + threads + "  =  Time: " + (end - start) + " ms");

            if (threads == 4) {
                finalStats = calc.getStat();
            }
        }

        if (finalStats == null || finalStats.isEmpty()) {
            System.out.println("No statistics generated. Attribute may be not correct: " + attribute);
            return;
        }

        System.out.println("Statistics for attribute '" + attribute + "':");
        finalStats.forEach((key, value) -> System.out.println("  " + key + ": " + value));

        XmlWriter xmlWriter = new XmlWriter();
        xmlWriter.writeStat(finalStats, attribute);
    }
}
