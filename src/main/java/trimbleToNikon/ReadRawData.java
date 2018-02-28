package trimbleToNikon;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadRawData {

    private static final String PATTERN_NUMBER = "(\\w{3}\\s)(\\d{5})";
    private static final String PATTERN_DISTANCE = "(\\w{2}\\s+)(\\d{1,4}\\.\\d{3})(.*)";
    private static final String PATTERN_HA = "(\\w{2}\\s+)(\\d{1,3}\\.\\d{4})(.*)";
    private static final String PATTERN_VA = "(\\w{1}\\d{1}\\s+)(\\d{1,3}\\.\\d{4})(.*)";


    public Set<RawPoint> processRawData(String fileName) {
        List<String> rawLines = getFileLine(fileName);
        Set<RawPoint> rawPoints = new LinkedHashSet<>();
        for (String line : rawLines) {
            RawPoint rp = populateRawPoint(line);
            rawPoints.add(rp);
        }
        return rawPoints;
    }

    private RawPoint populateRawPoint(String line) {
        RawPoint rp = new RawPoint();
        String[] rawLine = line.split("\\|");
        rp.setName(Integer.valueOf(parseRawData(rawLine[1], PATTERN_NUMBER)));
        rp.setDistance(Double.valueOf(parseRawData(rawLine[3], PATTERN_DISTANCE)));
        rp.setHorizontalAngel(Double.valueOf(parseRawData(rawLine[4], PATTERN_HA)));
        rp.setVerticalAngel(Double.valueOf(parseRawData(rawLine[5], PATTERN_VA)));
        return rp;
    }

    private String parseRawData(String rawData, String pattern) {
        Pattern pointNumberPattern = Pattern.compile(pattern);
        Matcher matcher = pointNumberPattern.matcher(rawData);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        return null;
    }

    private List<String> getFileLine(String filePath) {
        String line;
        List<String> lines = new ArrayList<>();
        try (InputStream is = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {
            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
