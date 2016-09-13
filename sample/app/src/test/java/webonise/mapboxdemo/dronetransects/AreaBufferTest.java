package webonise.mapboxdemo.dronetransects;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AreaBufferTest {
    @Test
    public void buggerTestForValidData() {
        List<Point> points = new ArrayList<>();
        Point point = new Point();
        point.setX(2);
        point.setY(3);
        points.add(point);
        Point point1 = new Point();
        point1.setX(5);
        point1.setY(5);
        points.add(point1);
        Point point2 = new Point();
        point2.setX(5);
        point2.setY(1);
        points.add(point2);
        Point point3 = new Point();
        point3.setX(0);
        point3.setY(0);
        points.add(point3);
        points.add(points.get(0));
        AreaBuffer areaBuffer = new AreaBuffer();
        try {
            List bufferPointList = areaBuffer.buffer(points, 0.0001);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
