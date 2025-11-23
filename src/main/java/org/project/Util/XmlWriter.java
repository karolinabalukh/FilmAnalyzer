package org.project.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class XmlWriter {

    public void writeStat(Map<String, Integer> stats, String attribute) throws IOException, Exception {
        String fileName = "statistics_by_" + attribute + ".xml";

        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fw.write("<statistics attribute=\"" + attribute + "\">\n");

            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                fw.write("   <item>\n");
                fw.write("      <value>" + entry.getKey() + "</value>\n");
                fw.write("      <count>" + entry.getValue() + "</count>\n");
                fw.write("   </item>\n");
            }

            fw.write("</statistics>\n");
        }

        System.out.println("XML saved: " + fileName);
    }
}
