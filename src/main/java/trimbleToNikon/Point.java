package trimbleToNikon;

public class Point {
    private long id;
    private double x;
    private double y;
    private double z;
    private String name;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String result = "Point{";
        result += x > 0 ? " x=" + x : "";
        result += y > 0 ? " y=" + y : "";
        result += z > 0 ? " z=" + z : "";
        result += "}";
        return result;
    }
}
