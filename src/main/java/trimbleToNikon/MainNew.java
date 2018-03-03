package trimbleToNikon;

import java.util.Set;

public class MainNew {
    public static void main(String[] args) {
        ReadRawData rrd = new ReadRawData();
        Set<RawPoint> rawPoints = rrd.processRawData("180208.dat");
//        System.out.println(rawPoints);
        DatabaseOperations persist = new DatabaseOperations();
        Calculator calculator = new Calculator();
        calculator.calculateAllPoints();

    }
}
