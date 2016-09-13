package webonise.mapboxdemo.dronetransects;

import java.util.ArrayList;
import java.util.List;

public class AreaBuffer {
    /**
     * Function to get the buffer points from a given set of x y
     *
     * @param pointList List<Points> A list of INCLOSED points
     * @param distance  double Distance you want to be from original shape. Approx : 0.0001 is 3-5 meter distance on ground
     * @return List<Point> list of buffered points
     * @throws Exception
     */
    public static List<Point> buffer(List<Point> pointList, double distance) throws Exception {
        EquationHandler equationHandler = new EquationHandler();
        List<LineEquation> bufferedLineEquationList = new ArrayList<>();
        /**
         * Find the buffered line Equation.
         * If number of points in polygon are 5 ie. 0,1,2,3,4,0
         * then run loop for points (0,1),(1,2),(2,3),(3,4),(4,0)
         */
        for (int i = 0; i < pointList.size(); i++) {
            Point firstPoint = pointList.get(i);
            Point secondPoint = pointList.get((i + 1) % pointList.size());
            /**
             * Step 1: Get equation of line from two points
             */
            LineEquation actualLineEquation = equationHandler.getLineEquation(firstPoint, secondPoint);
            /**
             * Step 2: Get center point of two Points
             */
            Point centerPoint = new Point();
            centerPoint = centerPoint.getCenterPoint(firstPoint, secondPoint);
            /**
             * Step 3: Get perpendicular line though center point.
             */
            LineEquation perpendicularLine = equationHandler.getPerpendicularLineEquation
                    (actualLineEquation, centerPoint);
            /**
             * Step 4: Get point on the line a given distance
             */
            Point[] polygon = pointList.toArray(new Point[pointList.size() - 1]);
            Point bufferPoint = equationHandler.getPointOnLineAtDistance(perpendicularLine,
                    centerPoint, distance, polygon);
            /**
             * Step 5: Get equation of line parallel to original line passing though buffer point
             */
            LineEquation bufferLineEquation = equationHandler.getParallelLineEquation
                    (actualLineEquation, bufferPoint);
            bufferedLineEquationList.add(bufferLineEquation);
        }
        /**
         * Once you have line equation of all the buffered line.
         * Find the list of intersection points of all buffered lines.
         */
        List<Point> bufferedPoints = new ArrayList<>();
        for (int i = 0; i < bufferedLineEquationList.size(); i++) {
            int nextIndex = (i + 1) % bufferedLineEquationList.size();
            Point intersectionPoint = equationHandler.getIntersectionPoint
                    (bufferedLineEquationList.get(i), bufferedLineEquationList.get(nextIndex));
            bufferedPoints.add(intersectionPoint);
        }
        return bufferedPoints;
    }
}