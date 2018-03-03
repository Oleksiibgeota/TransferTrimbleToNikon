package trimbleToNikon;

public class RawPoint {
    private long id;
    private double distance;
    private double horizontalAngel;
    private double verticalAngel;
    private double height;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    private int name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getHorizontalAngel() {
        return horizontalAngel;
    }

    public void setHorizontalAngel(double horizontalAngel) {
        this.horizontalAngel = horizontalAngel;
    }

    public double getVerticalAngel() {
        return verticalAngel;
    }

    public void setVerticalAngel(double verticalAngel) {
        this.verticalAngel = verticalAngel;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }




    @Override
    public String toString() {
        return "RawPoint{" +
                "id=" + id +
                ", distance=" + distance +
                ", horizontalAngel=" + horizontalAngel +
                ", verticalAngel=" + verticalAngel +
                ", height=" + height +
                ", name=" + name +
                '}';
    }
}
