package org.project.Util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class XmlWriterTest {

    @Test
    void testWriteStatCreatesValidXml() throws Exception {
        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("fantasy", 2);
        stats.put("drama", 1);
        String attribute = "genre";
        String expectedFileName = "statistics_by_" + attribute + ".xml";
        File file = new File(expectedFileName);
        XmlWriter writer = new XmlWriter();

        try {
            writer.writeStat(stats, attribute);
            assertTrue(file.exists(), "XML файл мав бути створений");
            String content = Files.readString(file.toPath());
            assertTrue(content.contains("<statistics attribute=\"genre\">"));
            assertTrue(content.contains("<value>fantasy</value>"));
            assertTrue(content.contains("<count>2</count>"));
            assertTrue(content.contains("<value>drama</value>"));
            assertTrue(content.contains("<count>1</count>"));
            assertTrue(content.contains("</statistics>"));

        } finally {
            Files.deleteIfExists(file.toPath());
        }
    }
}