package trimbleToNikon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DatabaseOperations {
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "Root";

    public void saveRawPoints(Set<RawPoint> rawPoints) {
        String query = "INSERT INTO raw_point (name, distance, horizontal_angel, vertical_angel) VALUES(?,?,?,?);";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            for (RawPoint rp : rawPoints) {
                stmt.setInt(1, rp.getName());
                stmt.setDouble(2, rp.getDistance());
                stmt.setDouble(3, rp.getHorizontalAngel());
                stmt.setDouble(4, rp.getVerticalAngel());
                stmt.execute();
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public void createRawPoint(int name, double distance, double horizontal_angel, double vertical_angel, double height) {
        String query = "INSERT INTO raw_point(name, distance, horizontal_angel, vertical_angel,height) VALUES (?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, name);
            stmt.setDouble(2, distance);
            stmt.setDouble(3, horizontal_angel);
            stmt.setDouble(4, vertical_angel);
            stmt.setDouble(5, height);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setHeightInRawPoints(Long idStart, Long idEnd, Double height) {
        String query = "UPDATE raw_point SET height = ? WHERE id = ?";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            for (long i = idStart; i <= idEnd; i++) {
                stmt.setDouble(1, height);
                stmt.setLong(2, i);
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRawPoint(String nameCurrent,
                               String nameNew,
                               double newDistance,
                               double newHorizontal_angel,
                               double newVertical_angel,
                               double newHeight) {
        String query = "UPDATE raw_point SET name = ?, distance = ?, horizontal_angel = ?, vertical_angel = ?, height = ? WHERE name = ?;";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, nameNew);
            stmt.setDouble(2, newDistance);
            stmt.setDouble(3, newHorizontal_angel);
            stmt.setDouble(4, newVertical_angel);
            stmt.setDouble(5, newHeight);
            stmt.setString(6, nameCurrent);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public RawPoint getRawPointById(Long id) {
        String query = "SELECT * FROM raw_point WHERE id = ?;";
        RawPoint rawPoint = new RawPoint();
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                rawPoint.setId(rs.getLong("id"));
                rawPoint.setName(rs.getInt("name"));
                rawPoint.setDistance(rs.getDouble("distance"));
                rawPoint.setHorizontalAngel(rs.getDouble("horizontal_angel"));
                rawPoint.setVerticalAngel(rs.getDouble("vertical_angel"));
                rawPoint.setHeight(rs.getDouble("height"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rawPoint;
    }

    public List<RawPoint> getAllRawPoint() {
        List<RawPoint> rawPoints = new ArrayList<>();
        String query = "SELECT * FROM raw_point;";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RawPoint rawPoint = new RawPoint();
                rawPoint.setName(rs.getInt("name"));
                rawPoint.setDistance(rs.getDouble("distance"));
                rawPoint.setHorizontalAngel(rs.getDouble("horizontal_angel"));
                rawPoint.setVerticalAngel(rs.getDouble("vertical_angel"));
                rawPoint.setHeight(rs.getDouble("height"));
                rawPoints.add(rawPoint);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rawPoints;
    }

    public void deleteRawPointsById(Long idStart, Long idEnd) {
        String query = "DELETE FROM raw_point WHERE id = ?";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            for (long i = idStart; i <= idEnd; i++) {
                stmt.setLong(1, i);
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createPoint(Point point) {
       createPoint(point.getName(), point.getX(), point.getY(), point.getZ());
    }

    public void createPoint(String name, double x, double y, double z) {
        String query = "INSERT INTO point (name, x,y,z) VALUES(?,?,?,?);";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, x);
            stmt.setDouble(3, y);
            stmt.setDouble(4, z);
            stmt.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public void updatePointByName(String name, double xNew, double yNew, double zNew) {
        String query = "UPDATE point SET name = ?, x = ?, y= ?, z = ? WHERE name =?;";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, xNew);
            stmt.setDouble(3, yNew);
            stmt.setDouble(4, zNew);
            stmt.setString(5, name);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePointsByName(String name) {
        String query = "DELETE FROM raw_point WHERE id = ?";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Point getPointByName(String name) {
        String query = "SELECT * FROM point WHERE name = ?;";
        Point point = new Point();
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                point.setId(rs.getLong("id"));
                point.setName(rs.getString("name"));
                point.setX(rs.getDouble("x"));
                point.setY(rs.getDouble("y"));
                point.setZ(rs.getDouble("z"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return point;
    }

    public List<Point> getAllPoint() {
        List<Point> points = new ArrayList<>();
        String query = "SELECT * FROM point;";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Point point = new Point();
                point.setName(rs.getString("name"));
                point.setX(rs.getDouble("x"));
                point.setY(rs.getDouble("y"));
                point.setZ(rs.getDouble("z"));
                point.setId(rs.getLong("id"));
                points.add(point);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return points;
    }

    public void createStation(String name, double height) {
        String query = "INSERT INTO station (name, height) VALUES(?,?);";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, height);
            stmt.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public List<Station> getAllStations() {
        String query = "SELECT * FROM station;";
        List<Station> stations = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Station station = new Station();
                station.setId(rs.getInt("id"));
                station.setName(rs.getString("name"));
                station.setHeight(rs.getDouble("height"));
                stations.add(station);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return stations;
    }


    public Station getStationByName(String name) {
//        String query = "SELECT * FROM station WHERE name = ?";
        String query = "SELECT * FROM station LEFT JOIN point on station.name=point.name WHERE point.name = ?;";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Station station = new Station();
                Point point = new Point();
                station.setId(rs.getInt("id"));
                station.setName(rs.getString("name"));
                station.setHeight(rs.getDouble("height"));
                point.setX(rs.getDouble("x"));
                point.setY(rs.getDouble("y"));
                station.setStationThis(point);
                return station;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return null;
    }

    public void updateStationById(Long id, String newName, Double newHeight) {
        String query = "UPDATE station SET name = ?, height= ? WHERE id = ?;";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setDouble(2, newHeight);
            stmt.setLong(3, id);
            stmt.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public void deleteStation(long id) {
        String query = "DELETE FROM station WHERE  id = ?;";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.execute();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }




}

