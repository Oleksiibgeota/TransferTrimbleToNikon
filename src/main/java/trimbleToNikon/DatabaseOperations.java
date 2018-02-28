package trimbleToNikon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DatabaseOperations {
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "Root";

    private Connection con;
    private PreparedStatement stmt;

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
        String query = "SELECT * FROM station WHERE name = ?";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Station station = new Station();
                station.setId(rs.getInt("id"));
                station.setName(rs.getString("name"));
                station.setHeight(rs.getDouble("height"));
                return station;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return null;
    }

    public void updateStation(Station station) {
        String query = "UPDATE station SET name = ?, height= ? WHERE id = ?;";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, station.getName());
            stmt.setDouble(2, station.getHeight());
            stmt.setLong(3, station.getId());
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

    //    public void calcXYZ(String nameWorkingStation, String nameDirectionStation, Set<RawPoint> rawPoints) {
//        Station stationWorking = getStationByName(nameWorkingStation);
//        Station stationDirection = getStationByName(nameDirectionStation);
//    double XSW = stationWorking.getStationThis().getX();
//    double YSW = stationWorking.getStationThis().getY();
//    double ZSW = stationWorking.getStationThis().getZ();
//    double XSD = stationDirection.getStationThis().getX();
//    double YSD = stationDirection.getStationThis().getY();
//    double ZSD = stationDirection.getStationThis().getZ();

    public void calcXYZ(Station station1, Station station2, Set<RawPoint> rawPoints) {

        double XSW = station1.getStationThis().getX();
        double YSW = station1.getStationThis().getY();
        double ZSW = station1.getStationThis().getZ();
        double XSD = station2.getStationThis().getX();
        double YSD = station2.getStationThis().getY();
        double ZSD = station2.getStationThis().getZ();

        double deltaX = XSD - XSW;
        double deltaY = YSD - YSW;
        System.out.println(Math.toDegrees(Math.atan(Math.abs(deltaY / deltaX))));
        double prepareRhumb = Math.toDegrees(Math.atan(Math.abs(deltaY / deltaX)));
        double rhumb = 0.0;
        if (deltaX > 0 & deltaY > 0) {
//then quoter 1
            rhumb = prepareRhumb;
        }
        if (deltaX < 0 & deltaY > 0) {
//then quoter 2
            rhumb = 180 - prepareRhumb;
        }
        if (deltaX < 0 & deltaY < 0) {
//then quoter 3
            rhumb = prepareRhumb - 180;
        }
        if (deltaX > 0 & deltaY < 0) {
//then quoter 4
            rhumb = 360 - prepareRhumb;
        }


        String query = "INSERT INTO point (x, y) VALUES(?,?);";
        try (Connection con = DriverManager.getConnection(url, user, password); PreparedStatement stmt = con.prepareStatement(query)) {
            for (RawPoint rp : rawPoints) {
                double degree = Math.floor(rp.getHorizontalAngel());
                double minute = Math.floor((rp.getHorizontalAngel() - degree) * 100);
                double second = Math.floor((((rp.getHorizontalAngel() - degree) * 100) - minute) * 100);
                double directionAngelPoint = rhumb + (degree + 60 / (minute + 60 / second));
                if (directionAngelPoint > 360) {
                    directionAngelPoint = directionAngelPoint - 360;
                }
//double pointX =
                stmt.setDouble(1, XSW + (rp.getDistance()*Math.cos(Math.toRadians(directionAngelPoint))));
//double pointY =
                stmt.setDouble(2, YSW + (rp.getDistance()*Math.sin(Math.toRadians(directionAngelPoint))));
                stmt.execute();
//                rp.getVerticalAngel();

            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return;
    }

}

