package trimbleToNikon;

import java.util.Set;

public class Station {
    private long id;
    private String name;
    private Set <Station> direction;
    private double height;
    private Point stationThis;
    private Set<RawPoint> rawChildPoint;
    private Set<Point> childPoint;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Station> getDirection() {
        return direction;
    }

    public void setDirection(Set<Station> direction) {
        this.direction = direction;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Point getStationThis() {
        return stationThis;
    }

    public void setStationThis(Point stationThis) {
        this.stationThis = stationThis;
    }

    public Set<RawPoint> getRawChildPoint() {
        return rawChildPoint;
    }

    public void setRawChildPoint(Set<RawPoint> rawChildPoint) {
        this.rawChildPoint = rawChildPoint;
    }

    public Set<Point> getChildPoint() {
        return childPoint;
    }

    public void setChildPoint(Set<Point> childPoint) {
        this.childPoint = childPoint;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", direction=" + direction +
                ", height=" + height +
                ", stationThis=" + stationThis +
                ", rawChildPoint=" + rawChildPoint +
                ", childPoint=" + childPoint +
                '}';
    }
}
