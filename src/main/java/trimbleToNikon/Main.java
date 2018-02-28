package trimbleToNikon;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {


    static String patternNumber = "(\\w{3}\\s)(\\d{5})";
    static String patternDistance = "(\\w{2}\\s+)(\\d{1,4}\\.\\d{3})(.*)";
    static String patternHorizontalAngel = "(\\w{2}\\s+)(\\d{1,3}\\.\\d{4})(.*)";
    static String patternVerticalAngel = "(\\w{1}\\d{1}\\s+)(\\d{1,3}\\.\\d{4})(.*)";

    static String head = "CO,Nikon RAW data format V2.00\n" +
            "CO,\n" +
            "CO,Description:\n" +
            "CO,Client:\n" +
            "CO,Comments:\n" +
            "CO,Downloaded 14-01-2018 18:16:29\n" +
            "CO,Software: Pre-install version: 1.20\n" +
            "CO,Instrument: Trimble series\n" +
            "CO,Dist Units: Metres\n" +
            "CO,Angle Units: DDDMMSS\n" +
            "CO,Zero azimuth: North\n" +
            "CO,Zero VA: Zenith\n" +
            "CO,Coord Order: NEZ\n" +
            "CO,HA Raw data: Azimuth\n" +
            "CO,Tilt Correction:  VA:ON HA:ON\n" +
            "CO, SEDITOR <JOB> Created 14-01-2018 18:16:29\n" +
            "CO,S/N:000000\n";

    public static void main(String[] args) throws IOException, InterruptedException {

//        String inputFilePath = args[0];
//        String outputFilePath = args[1];


        String inputFilePath = "D:\\Леша\\WP\\2018\\borodai\\180208.dat";
        String outputFilePath = "out2.txt";

        String timestamp = getTimestamp();

        List<String> lines = getFileLine(inputFilePath);
//        Pattern pointNumberPattern = Pattern.compile(".*END.*");

        int a = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.matches(".*END.*")) {
                a = ++i;
                break;
            }
        }
// add to fileOutPut head stamp;
        File file = new File(outputFilePath);
        writeConvertFile(head.toString(), outputFilePath);

        int lastPoint = 0;

        while (lastPoint != lines.size()) {

            StringJoiner destinationLineForStation = new StringJoiner(",", "", "\n");
            System.out.println("Input number working station ");
            Scanner scannerWorkingStation = new Scanner(System.in);
            String numberWorkStation = scannerWorkingStation.next();

            System.out.println("Input number station on direction");
            Scanner scannerStationDirecting = new Scanner(System.in);
            String stationOnDirection = scannerStationDirecting.next();

            System.out.println("Input height working station");
            Scanner scannerStationStayingHeight = new Scanner(System.in);
            String workStationHeight = scannerStationStayingHeight.next();

            System.out.println("Input height Landmark ");
            Scanner scannerHeightLandMarkOnThisStation = new Scanner(System.in);
            String heightLandMark = scannerHeightLandMarkOnThisStation.next();

            System.out.println("Input last point on this station ");
            Scanner scannerLastPointOnThisStation = new Scanner(System.in);
            int lastPointOnWorkStation = scannerLastPointOnThisStation.nextInt();
            lastPoint = lastPointOnWorkStation;

//        System.out.println(numberWorkStation);
//        System.out.println(stationOnDirection);
//        System.out.println(workStationHeight);
//        System.out.println(heightLandMark);
//        System.out.println(lastPointOnWorkStation);


            destinationLineForStation.add("ST")
                    .add(numberWorkStation)
                    .add("")
                    .add(stationOnDirection)
                    .add("")
                    .add(workStationHeight)
                    .add("0.0000")
                    .add("0.0000");
            writeConvertFile(destinationLineForStation.toString(), outputFilePath);

            int linesSize = +lastPointOnWorkStation;

            for (int i = a; i < linesSize; i++) {
                String line = lines.get(i);

                StringJoiner destinationLine = new StringJoiner(",", "", "\n");
                destinationLine
                        .add("SS")
                        .add(parseRawData(getRawData(line, 1), patternNumber))
                        .add(heightLandMark)
                        .add(parseRawData(getRawData(line, 3), patternDistance))
                        .add(parseRawData(getRawData(line, 4), patternHorizontalAngel))
                        .add(parseRawData(getRawData(line, 5), patternVerticalAngel))
                        .add(timestamp)
                        .add("");
                writeConvertFile(destinationLine.toString(), outputFilePath);

            }
            a = linesSize + 1;
        }


    }

    private static String getRawData(String line, int position) {
        String[] lineArr = line.split("\\|");
        if (lineArr.length > position) {
            return lineArr[position];
        }
        return null;
    }

    private static String parseRawData(String rawData, String pattern) {
        Pattern pointNumberPattern = Pattern.compile(pattern);
        Matcher matcher = pointNumberPattern.matcher(rawData);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        return null;
    }

    private static String getTimestamp() {
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        Date now = Calendar.getInstance().getTime();
        return sdfTime.format(now);
    }


    private static List<String> getFileLine(String filePath) {
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

    private static void writeConvertFile(String line, String outputFile) throws IOException {
        File file = new File(outputFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(line);
        }
    }


}