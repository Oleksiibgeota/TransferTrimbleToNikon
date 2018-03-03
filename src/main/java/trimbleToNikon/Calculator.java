package trimbleToNikon;

import java.util.List;

public class Calculator {
    private DatabaseOperations dbOps = new DatabaseOperations();

    public void calculateAllPoints() {
        System.out.println("Calculate all points");
        List<RawPoint> rawPoints = dbOps.getAllRawPoint();
        for (RawPoint rp : rawPoints) {
            Point point = calculatePoint(rp);
            dbOps.createPoint(point);
        }
    }

    public Point calculatePoint(RawPoint rawPoint) {
        System.out.println("Calculate point "+rawPoint.getName());
        double dist = rawPoint.getDistance();
        double degree = Math.floor(rawPoint.getHorizontalAngel());
        double minute = Math.floor((rawPoint.getHorizontalAngel() - degree) * 100);
        double second = Math.floor((((rawPoint.getHorizontalAngel() - degree) * 100) - minute) * 100);
        Station stationWorking = dbOps.getStationByName("first station");
        Station stationDirection = dbOps.getStationByName("second station");
        double rhumb = calculateRhumb(stationWorking,stationDirection);
        double directionAngelPoint = rhumb + (degree + minute / 60 + second / 3600);
        double XSW = stationWorking.getStationThis().getX();
        double YSW = stationWorking.getStationThis().getY();
        double pointX= XSW + (dist * Math.cos(Math.toRadians(directionAngelPoint)));
        double pointY = YSW + (dist * Math.sin(Math.toRadians(directionAngelPoint)));
        Point point = new Point();
        point.setName(String.valueOf(rawPoint.getName()));
        point.setX(pointX);
        point.setY(pointY);
        System.out.println("point calculated x="+point.getX() +", y=" +point.getX() );
        return point;
    }

    public double calculateRhumb(Station stationWorking, Station stationDirection) {
        double XSW = stationWorking.getStationThis().getX();
        double YSW = stationWorking.getStationThis().getY();
        double XSD = stationDirection.getStationThis().getX();
        double YSD = stationDirection.getStationThis().getY();

        double deltaX = XSD - XSW;
        double deltaY = YSD - YSW;
        double prepareRhumb = Math.toDegrees(Math.atan(Math.abs(deltaY / deltaX)));
        double rhumb = 0.0;
        if (deltaX > 0 & deltaY > 0) {
            rhumb = prepareRhumb;
        }
        if (deltaX < 0 & deltaY > 0) {
            rhumb = 180 - prepareRhumb;
        }
        if (deltaX < 0 & deltaY < 0) {
            rhumb = prepareRhumb - 180;
        }
        if (deltaX > 0 & deltaY < 0) {
            rhumb = 360 - prepareRhumb;
        }
        return rhumb;
    }

}
