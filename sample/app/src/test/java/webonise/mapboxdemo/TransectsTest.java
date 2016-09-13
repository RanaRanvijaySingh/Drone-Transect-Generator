package webonise.mapboxdemo;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import webonise.mapboxdemo.dronetransects.EquationHandler;
import webonise.mapboxdemo.dronetransects.LineEquation;
import webonise.mapboxdemo.dronetransects.Point;
import webonise.mapboxdemo.dronetransects.Transects;

public class TransectsTest {

    /**
     * Test cases for ________ getTopLeftCornerPoint function
     */
    @Test
    public void testGetTopLeftCornerPointForFourthQuad() {
        List polygonPoints = new ArrayList();
        polygonPoints.add(new Point(-29.07537517955836, -42.71484375));
        polygonPoints.add(new Point(-29.84064389983441, -29.355468750000004));
        polygonPoints.add(new Point(-39.77476948529546, -36.38671875));
        Point actualPoint = Transects.getTopLeftCornerPoint(polygonPoints);
        Assert.assertEquals(-29.07537517955836, actualPoint.getX(), 0);
    }

    @Test
    public void testGetTopLeftCornerPointForThirdQuad() {
        List polygonPoints = new ArrayList();
        polygonPoints.add(new Point(-40.58058466412763, 30.585937499999996));
        polygonPoints.add(new Point(-32.546813173515154, 67.8515625));
        polygonPoints.add(new Point(-46.55886030311718, 45.3515625));
        Point actualPoint = Transects.getTopLeftCornerPoint(polygonPoints);
        Assert.assertEquals(-32.546813173515154, actualPoint.getX(), 0);
        Assert.assertEquals(30.585937499999996, actualPoint.getY(), 0);
    }

    @Test
    public void testGetTopLeftCornerPointForSecondQuad() {
        List polygonPoints = new ArrayList();
        polygonPoints.add(new Point(29.6880527498568, -50.625));
        polygonPoints.add(new Point(29.53522956294847, -43.59375));
        polygonPoints.add(new Point(25.64152637306577, -45.17578125));
        Point actualPoint = Transects.getTopLeftCornerPoint(polygonPoints);
        Assert.assertEquals(29.6880527498568, actualPoint.getX(), 0);
        Assert.assertEquals(-50.625, actualPoint.getY(), 0);
    }

    /**
     * Test cases for ________ getBottomLeftCornerPoint function
     */
    @Test
    public void testGetBottomLeftCornerPointForThirdQuad() {
        List polygonPoints = new ArrayList();
        polygonPoints.add(new Point(-40.58058466412763, 30.585937499999996));
        polygonPoints.add(new Point(-32.546813173515154, 67.8515625));
        polygonPoints.add(new Point(-46.55886030311718, 45.3515625));
        Point actualPoint = Transects.getBottomLeftCornerPoint(polygonPoints);
        Assert.assertEquals(-46.55886030311718, actualPoint.getX(), 0);
        Assert.assertEquals(30.585937499999996, actualPoint.getY(), 0);
    }

    @Test
    public void testGetBottomLeftCornerPointForSecondQuad() {
        List polygonPoints = new ArrayList();
        polygonPoints.add(new Point(29.6880527498568, -50.625));
        polygonPoints.add(new Point(29.53522956294847, -43.59375));
        polygonPoints.add(new Point(25.64152637306577, -45.17578125));
        Point actualPoint = Transects.getBottomLeftCornerPoint(polygonPoints);
        Assert.assertEquals(25.64152637306577, actualPoint.getX(), 0);
        Assert.assertEquals(-50.625, actualPoint.getY(), 0);
    }

    /**
     * Test cases for _______________ getTransectPoint
     */
    @Test
    public void testGetTransectPointForTopCornerWithValidData() {
        Point[] points = new Point[2];
        points[0] = new Point(51.39920565355378, -73.916015625);
        points[1] = new Point(50.90303283111257, -73.740234375);
        Point actualPoint = Transects.getTransectPoint(points, true);
        Assert.assertEquals(50.90303283111257, actualPoint.getX(), 0);
    }

    @Test
    public void testGetTransectPointForTopCornerWithValidData2() {
        Point[] points = new Point[2];
        points[0] = new Point(22.51255695405145, 29.8828125);
        points[1] = new Point(17.978733095556183, 29.794921874999996);
        Point actualPoint = Transects.getTransectPoint(points, true);
        Assert.assertEquals(17.978733095556183, actualPoint.getX(), 0);
    }

    @Test
    public void testGetTransectPointForTopCornerWithValidData3() {
        Point[] points = new Point[2];
        points[0] = new Point(52.5897007687178, -69.9609375);
        points[1] = new Point(50.90303283111257, -70.048828125);
        Point actualPoint = Transects.getTransectPoint(points, true);
        Assert.assertEquals(50.90303283111257, actualPoint.getX(), 0);
    }

    @Test
    public void testGetTransectPointForBottomCornerWithValidData2() {
        Point[] points = new Point[2];
        points[0] = new Point(51.18967256411778, -72.5482177734375);
        points[1] = new Point(51.17934297928927, -72.39990234375);
        Point actualPoint = Transects.getTransectPoint(points, false);
        Assert.assertEquals(-72.39990234375, actualPoint.getY(), 0);
    }

    @Test
    public void testGetTransectPointForBottomCornerWithValidData3() {
        Point[] points = new Point[2];
        points[0] = new Point(52.5897007687178, -69.9609375);
        points[1] = new Point(50.90303283111257, -70.048828125);
        Point actualPoint = Transects.getTransectPoint(points, false);
        Assert.assertEquals(-69.9609375, actualPoint.getY(), 0);
    }

    /**
     * Test cases for _______________ getTransectCrossingPoint
     * ALMOST CORRECT
     */
    /*@Test
    public void testGetTransectCrossingPointForValidData() {
        LineEquation lineEquation = new LineEquation();
        lineEquation.setA(1);
        lineEquation.setM(1);
        lineEquation.setC(0);
        Point referencePoint = new Point(2, 2);
        double distance = 2;
        Point actualPoint = Transects.getTransectCrossingPoint(lineEquation, referencePoint,
                distance, true);
        Assert.assertEquals(4, actualPoint.getY(), 0);
        Assert.assertEquals(0, actualPoint.getX(), 0);
    }*/

    /**
     * ===================================================================================================
     */
    @Test
    public void testGetAllIntersectionPointsForValidData() {
        EquationHandler equationHandler = new EquationHandler();
        LineEquation lineEquation = new LineEquation();
        lineEquation.setA(1);
        lineEquation.setM(0);
        lineEquation.setC(2);
        List<Point> polygon = new ArrayList<>();
        polygon.add(new Point(2, 2));
        polygon.add(new Point(11, 11));
        polygon.add(new Point(15, 0));
        List<LineEquation> edgeLines = new ArrayList<>();
        for (int i = 0; i < polygon.size(); i++) {
            int nextPoint = (i + 1) % polygon.size();
            try {
                edgeLines.add(equationHandler.getLineEquation(polygon.get(i), polygon.get(nextPoint)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<Point> points = Transects.getAllIntersectionPoints(lineEquation, edgeLines, polygon);
        Assert.assertEquals(3, points.size());
    }

    @Test
    public void testGetAllIntersectionPointsForValidData2() {
        EquationHandler equationHandler = new EquationHandler();
        LineEquation lineEquation = new LineEquation();
        lineEquation.setA(1);
        lineEquation.setM(0);
        lineEquation.setC(2);
        List<Point> polygon = new ArrayList<>();
        polygon.add(new Point(0, 0)); // first point change
        polygon.add(new Point(11, 11));
        polygon.add(new Point(15, 0));
        List<LineEquation> edgeLines = new ArrayList<>();
        for (int i = 0; i < polygon.size(); i++) {
            int nextPoint = (i + 1) % polygon.size();
            try {
                edgeLines.add(equationHandler.getLineEquation(polygon.get(i), polygon.get(nextPoint)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        List<Point> points = Transects.getAllIntersectionPoints(lineEquation, edgeLines, polygon);
        Assert.assertEquals(2, points.size());
    }

    @Test
    public void testArrangeWaypointsForValidData() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 1));
        points.add(new Point(1, 1));
        points.add(new Point(2, 1));
        points.add(new Point(3, 1));
        points.add(new Point(4, 1));
        points.add(new Point(5, 1));
        points.add(new Point(6, 1));
        points.add(new Point(7, 1));
        points.add(new Point(8, 1));
        points.add(new Point(9, 1));
        points.add(new Point(10, 1));
        points.add(new Point(11, 1));
        List<Point> actualPointList = Transects.arrangeWaypoints(points);
        Assert.assertEquals(0, actualPointList.get(0).getX(), 0);
        Assert.assertEquals(1, actualPointList.get(1).getX(), 0);
        Assert.assertEquals(3, actualPointList.get(2).getX(), 0);
        Assert.assertEquals(2, actualPointList.get(3).getX(), 0);
        Assert.assertEquals(4, actualPointList.get(4).getX(), 0);
        Assert.assertEquals(5, actualPointList.get(5).getX(), 0);
        Assert.assertEquals(7, actualPointList.get(6).getX(), 0);
    }

    @Test
    public void testArrangeWaypointsForValidData2() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 1));
        points.add(new Point(1, 1));
        points.add(new Point(2, 1));
        points.add(new Point(3, 1));
        points.add(new Point(4, 1));
        points.add(new Point(5, 1));
        points.add(new Point(6, 1));
        points.add(new Point(7, 1));
        points.add(new Point(8, 1));
        points.add(new Point(9, 1));
        points.add(new Point(10, 1));
        List<Point> actualPointList = Transects.arrangeWaypoints(points);
        Assert.assertEquals(0, actualPointList.get(0).getX(), 0);
        Assert.assertEquals(1, actualPointList.get(1).getX(), 0);
        Assert.assertEquals(3, actualPointList.get(2).getX(), 0);
        Assert.assertEquals(2, actualPointList.get(3).getX(), 0);
        Assert.assertEquals(4, actualPointList.get(4).getX(), 0);
        Assert.assertEquals(5, actualPointList.get(5).getX(), 0);
        Assert.assertEquals(7, actualPointList.get(6).getX(), 0);
        Assert.assertEquals(6, actualPointList.get(7).getX(), 0);
        Assert.assertEquals(8, actualPointList.get(8).getX(), 0);
        Assert.assertEquals(9, actualPointList.get(9).getX(), 0);
        Assert.assertEquals(10, actualPointList.get(10).getX(), 0);
    }

    @Test
    public void testArrangeWaypointsForValidData3() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 1));
        points.add(new Point(1, 1));
        points.add(new Point(2, 1));
        points.add(new Point(3, 1));
        List<Point> actualPointList = Transects.arrangeWaypoints(points);
        Assert.assertEquals(0, actualPointList.get(0).getX(), 0);
        Assert.assertEquals(1, actualPointList.get(1).getX(), 0);
        Assert.assertEquals(3, actualPointList.get(2).getX(), 0);
        Assert.assertEquals(2, actualPointList.get(3).getX(), 0);
    }

    @Test
    public void testArrangeWaypointsForValidData4() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 1));
        points.add(new Point(1, 1));
        points.add(new Point(2, 1));
        List<Point> actualPointList = Transects.arrangeWaypoints(points);
        Assert.assertEquals(0, actualPointList.get(0).getX(), 0);
        Assert.assertEquals(1, actualPointList.get(1).getX(), 0);
        Assert.assertEquals(2, actualPointList.get(2).getX(), 0);
    }


    @Test
    public void testArrangeInterceptPointForValidData() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 1));
        points.add(new Point(1, 1));
        List<Point> actualPointList = Transects.arrangeInterceptPoint(points);
        Assert.assertEquals(0, actualPointList.get(0).getX(), 0);
        Assert.assertEquals(1, actualPointList.get(1).getX(), 0);
    }


    @Test
    public void testArrangeInterceptPointForValidData2() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, 1));
        List<Point> actualPointList = Transects.arrangeInterceptPoint(points);
        Assert.assertEquals(0, actualPointList.get(0).getY(), 0);
        Assert.assertEquals(1, actualPointList.get(1).getY(), 0);
    }


    @Test
    public void testArrangeInterceptPointForValidData3() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(0, 1));
        List<Point> actualPointList = Transects.arrangeInterceptPoint(points);
        Assert.assertEquals(0, actualPointList.get(0).getY(), 0);
        Assert.assertEquals(1, actualPointList.get(1).getY(), 0);
    }


    @Test
    public void testArrangeInterceptPointForValidData4() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(11, 12));
        points.add(new Point(14, 56));
        List<Point> actualPointList = Transects.arrangeInterceptPoint(points);
        Assert.assertEquals(11, actualPointList.get(0).getX(), 0);
        Assert.assertEquals(14, actualPointList.get(1).getX(), 0);
    }

    @Test
    public void testFilterInterceptPointsForValidData() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        List<Point> actualPoints = Transects.filterInterceptPoints(points);
        Assert.assertEquals(0, actualPoints.get(0).getX(), 0);
        Assert.assertEquals(2, actualPoints.get(1).getX(), 0);
    }

    @Test
    public void testFilterInterceptPointsForValidData1() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        points.add(new Point(3, 3));
        List<Point> actualPoints = Transects.filterInterceptPoints(points);
        Assert.assertEquals(0, actualPoints.get(0).getX(), 0);
        Assert.assertEquals(3, actualPoints.get(1).getX(), 0);
    }
}

























