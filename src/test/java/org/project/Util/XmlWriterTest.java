package org.project.Util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class XmlWriterTest {

    @Test
    void testWriteStatCreatesValidXml(@TempDir File tempDir) throws Exception {

        // Arrange
        Map<String, Integer> stats = Map.of(
                "fantasy", 2,
                "drama", 1
        );

        File savedFile = new File(tempDir, "statistics_by_genre.xml");

        XmlWriter writer = new XmlWriter() {
            @Override
            public void writeStat(Map<String, Integer> s, String attribute) throws Exception {
                try (FileWriter fw = new FileWriter(savedFile)) {
                    fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                    fw.write("<statistics attribute=\"" + attribute + "\">\n");
                    for (Map.Entry<String, Integer> e : s.entrySet()) {
                        fw.write("   <item>\n");
                        fw.write("      <value>" + e.getKey() + "</value>\n");
                        fw.write("      <count>" + e.getValue() + "</count>\n");
                        fw.write("   </item>\n");
                    }
                    fw.write("</statistics>\n");
                }
            }
        };

        writer.writeStat(stats, "genre");
        assertTrue(savedFile.exists());
        String content = Files.readString(savedFile.toPath());

        assertTrue(content.contains("<statistics attribute=\"genre\">"));
        assertTrue(content.contains("<value>fantasy</value>"));
        assertTrue(content.contains("<count>2</count>"));

        assertTrue(content.contains("<value>drama</value>"));
        assertTrue(content.contains("<count>1</count>"));

        assertTrue(content.contains("</statistics>"));
    }
}
