package trimbleToNikon;

import java.util.Set;

public class MainNew {
    public static void main(String[] args) {
        ReadRawData rrd = new ReadRawData();
        Set<RawPoint> rawPoints = rrd.processRawData("180208.dat");
        System.out.println(rawPoints);
        DatabaseOperations persist = new DatabaseOperations();
//        persist.createStation("first station", 1.65);
//        persist.createStation("second station", 1.475);


        Point point1 = new Point();
        point1.setX(3);
        point1.setY(1);
        point1.setZ(122.768);
        Station station = persist.getStationByName("first station");
        station.setStationThis(point1);
        System.out.println(station);
        Point point2 = new Point();

        point2.setX(100);
        point2.setY(100);
        Station station2 = persist.getStationByName("second station");
        station2.setStationThis(point2);

        System.out.println(station);
        System.out.println(station2);
        System.out.println(station);
        persist.calcXYZ(station,station2,rawPoints);
    }

}
