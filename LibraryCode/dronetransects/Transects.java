package webonise.mapboxdemo.dronetransects;

import java.util.ArrayList;
import java.util.List;

import webonise.mapboxdemo.dronetransects.EquationHandler;
import webonise.mapboxdemo.dronetransects.LineEquation;
import webonise.mapboxdemo.dronetransects.Point;

public class Transects {
    private static final int MAX_TRANCEST_LIMIT = 120;
    private static double MIN_LAT_INIT = 90.0; // initialize min x at max value
    private static double MAX_LAT_INIT = -90.0; // initialize max x at min value
    private static double MIN_LON_INIT = 180.0; // initialize min lon at max value

    /**
     * Function to generate transects for given polygon at a given angle with given distance
     * between the transects.
     *
     * @param polygonPoints            List<Point> list of polygon points
     * @param angle                    int angle at which the transect should be inclined.
     *                                 Angle 0 means lines will be vertical. Go on increasing the
     *                                 angle for inclined lines from longitude.
     * @param distanceBetweenTransects double distance between the transects.
     * @return List<Point> waypoint list. Creating polyline on map for all the waypoint will give
     * you complete transect for the given polygon.
     */
    public static List<Point> generateTransects(List<Point> polygonPoints, int angle,
                                                double distanceBetweenTransects) {
        List<Point> waypointList = new ArrayList<>();
        EquationHandler equationHandler = new EquationHandler();
        try {

            /**
             * STEP 1: Get all line equation in a list.
             */
            List<LineEquation> edgeLines = getAllEdgeLineEquations(polygonPoints);
            /**
             * STEP 2: Find which corner should be selected as a reference points
             *  top left corner or bottom left corner of polygon.
             */
            Point referencePoint;
            boolean isReferencePointOnTop = isReferencePointTopLeftCorner(angle);
            if (isReferencePointOnTop) {
                referencePoint = getTopLeftCornerPoint(polygonPoints);
            } else {
                referencePoint = getBottomLeftCornerPoint(polygonPoints);
            }
            /**
             * STEP 3: Get reference line equation based on given angle and reference point.
             */
            LineEquation referenceLine = equationHandler.getLineEquation(angle, referencePoint);
            int transectCount = 0;
            boolean isPolygonEndReached = false;
            /**
             * STEP 4: From obtained reference line keep on drawing parallel transects and
             * point of intersections with the polygon.
             */
            do {
                /**
                 * Distance from reference point will be calculated based on
                 * Given distance X total number of transects generated.
                 */
                double distanceFromBasePoint = distanceBetweenTransects * transectCount;
                /**
                 * Find the next point from where next transect is suppose to pass.
                 */
                Point transectCrossingPoint = getTransectCrossingPoint(referenceLine, referencePoint,
                        distanceFromBasePoint, isReferencePointOnTop);
                /**
                 * Get the equation of transect line passing through new point.
                 */
                LineEquation transectLine = equationHandler.getParallelLineEquation
                        (referenceLine, transectCrossingPoint);
                /**
                 * Find all the point of intersections of transect line and all edge lines. Save
                 * only those points which are on the polygon.
                 */
                List<Point> intersectionPoints = getAllIntersectionPoints(transectLine,
                        edgeLines, polygonPoints);
                waypointList.addAll(intersectionPoints);
                transectCount++;
                /**
                 * Identify if the end of polygon is reached or not.
                 * If the waypoint are added and there is not intersection point from last transect.
                 */
                if ((waypointList.size() > 0 && intersectionPoints.size() == 0) || transectCount > MAX_TRANCEST_LIMIT) {
                    isPolygonEndReached = true;
                }
            } while (!isPolygonEndReached);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrangeWaypoints(waypointList);
    }

    /**
     * Function to get all the line equation of edges of the polygon.
     *
     * @param polygonPoints List<Points>
     * @return List<LineEquation>
     */
    private static List<LineEquation> getAllEdgeLineEquations(List<Point> polygonPoints) {
        EquationHandler equationHandler = new EquationHandler();
        List<LineEquation> edgeLines = new ArrayList<>();
        for (int i = 0; i < polygonPoints.size(); i++) {
            Point firstPoint = polygonPoints.get(i);
            Point secondPoint = polygonPoints.get((i + 1) % polygonPoints.size());
            /**
             * Get equation of line from two points
             */
            try {
                edgeLines.add(equationHandler.getLineEquation(firstPoint, secondPoint));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return edgeLines;
    }

    /**
     * Function to check if the top left corner should be selected or not.
     * If the angle falls between 0 to 90 or 180 to 270 then return true else false
     *
     * @param angle int
     * @return boolean
     */
    private static boolean isReferencePointTopLeftCorner(int angle) {
        return angle % 180 >= 0 && angle % 180 < 90;
    }

    /**
     * Function to get the top left corner point of the polygon.
     *
     * @param polygonPoints List<Point>
     * @return Point
     */
    public static Point getTopLeftCornerPoint(List<Point> polygonPoints) {
        /**
         * Instead of 0,0 as the reference point we changed it to max x and max y.
         */
        double maxLat = MAX_LAT_INIT;
        double minLon = MIN_LON_INIT;
        for (Point point : polygonPoints) {
            if (point.getX() > maxLat) {
                maxLat = point.getX();
            }
            if (point.getY() < minLon) {
                minLon = point.getY();
            }
        }
        return new Point(maxLat, minLon);
    }

    /**
     * Function to get the bottom left corner point of the polygon.
     *
     * @param polygonPoints List<Point>
     * @return Point
     */
    public static Point getBottomLeftCornerPoint(List<Point> polygonPoints) {
        double minLat = MIN_LAT_INIT;
        double minLon = MIN_LON_INIT;
        for (Point point : polygonPoints) {
            if (point.getX() < minLat) {
                minLat = point.getX();
            }
            if (point.getY() < minLon) {
                minLon = point.getY();
            }
        }
        return new Point(minLat, minLon);
    }

    /**
     * Function to get the transect crossing point at a given distance from given point.
     *
     * @param referenceLineEquation reference line from which you have to find the distance.
     * @param referencePoint        Point reference point from which you have to find next point.
     * @param distance              double distance at which you have to get the point.
     * @param isReferencePointOnTop flag to find out which of the points top one or bottom one we
     *                              have to select.
     * @return Point
     */
    public static Point getTransectCrossingPoint(LineEquation referenceLineEquation,
                                                 Point referencePoint, double distance,
                                                 boolean isReferencePointOnTop) {
        EquationHandler equationHandler = new EquationHandler();
        try {
            /**
             * Find the equation of a line perpendicular to given line.
             */
            LineEquation perpendicularLine = equationHandler.getPerpendicularLineEquation
                    (referenceLineEquation, referencePoint);
            /**
             * Find the points on the perpendicular line at a given distance.
             */
            Point[] pointsAtDistance = equationHandler.getPointOnLineAtDistance(perpendicularLine,
                    referencePoint, distance);
            return getTransectPoint(pointsAtDistance, isReferencePointOnTop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to find out which of the point we have to select from given two points.
     *
     * @param pointsOnSameLine      Point[]
     * @param isReferencePointOnTop boolean flag which tell us if the reference point at the top
     *                              or at the bottom.
     *                              if TRUE :  You will get point that is below the given line.
     *                              if FALSE : You will get point that is on right side of given
     *                              line.
     * @return Point
     */
    public static Point getTransectPoint(Point[] pointsOnSameLine, boolean
            isReferencePointOnTop) throws ArrayIndexOutOfBoundsException {
        /**
         * If the reference point is at the top then LONGITUDE should be increasing for the next
         * point.
         */
        if (isReferencePointOnTop) {
            /**
             * Check which of the points have greater latitude values.
             */
            return pointsOnSameLine[0].getY() > pointsOnSameLine[1].getY()
                    ? pointsOnSameLine[0] : pointsOnSameLine[1];
        }
        /**
         * If the reference point is at the bottom then LATITUDE should be increasing for the next
         * point.
         */
        else {
            /**
             * Check which of the points have greater latitude values.
             */
            return pointsOnSameLine[0].getX() > pointsOnSameLine[1].getX()
                    ? pointsOnSameLine[0] : pointsOnSameLine[1];
        }
    }

    /**
     * Function to get all the intersection points between the polygon and the transect lines.
     *
     * @param transectLine  LineEquation
     * @param edgeLines     List<LineEquation>
     * @param polygonPoints List<Point>
     * @return List<Point>
     */
    public static List<Point> getAllIntersectionPoints(LineEquation transectLine,
                                                       List<LineEquation> edgeLines,
                                                       List<Point> polygonPoints) {
        List<Point> intersectionPoints = null;
        if (edgeLines != null) {
            intersectionPoints = new ArrayList<>();
            EquationHandler equationHandler = new EquationHandler();
            for (int i = 0; i < edgeLines.size(); i++) {
                try {
                    Point interceptPoint = equationHandler
                            .getIntersectionPoint(transectLine, edgeLines.get(i));
                    if (equationHandler.isPointOnPolygon(polygonPoints, interceptPoint)) {
                        intersectionPoints.add(interceptPoint);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        /**
         * Here is two step process.
         * First we have to make sure the intercept point is always less than equal to 2.
         * Which is done by filterInterceptPoints function.
         *
         * Finally arrange the points in one conventional way.
         * The convention is:
         * If X of point is different then point with lesser X will be first index and other on
         * second index.
         * else if X is same then check for Y to be different and point with lesser Y will be
         * first index and other on second index.
         */
        return arrangeInterceptPoint(filterInterceptPoints(intersectionPoints));
    }

    /**
     * Function to filter intercept point so as to get just two point of intercept.
     *
     * @param intersectionPoints List<Point> Original list of intercept points.
     * @return List<Point> Final intercept points.
     */
    public static List<Point> filterInterceptPoints(List<Point> intersectionPoints) {
        /**
         * Check if the intercept point is less than 3 then return the same.
         */
        if (intersectionPoints != null && intersectionPoints.size() < 3) {
            return intersectionPoints;
        } else {
            /**
             * Find return the points with (X min,Y min) and (X max, Y max)
             */
            Point minPoint = intersectionPoints.get(0);
            Point maxPoint = intersectionPoints.get(intersectionPoints.size() - 1);
            /**
             * Select comparison param based on which of the coordinate is not same ie. X or Y.
             */
            boolean isComparisonParamX =
                    intersectionPoints.get(0).getX() != intersectionPoints.get(1).getX();
            for (int i = 0; i < intersectionPoints.size(); i++) {
                if (isComparisonParamX) {
                    /**
                     * Find out the minimum point of intersection.
                     */
                    if (intersectionPoints.get(i).getX() < minPoint.getX()) {
                        minPoint = intersectionPoints.get(i);
                    }
                    /**
                     * Find max point of intersection.
                     */
                    if (intersectionPoints.get(i).getX() > maxPoint.getX()) {
                        maxPoint = intersectionPoints.get(i);
                    }
                } else {
                    /**
                     * Find out the minimum point of intersection.
                     */
                    if (intersectionPoints.get(i).getY() < minPoint.getY()) {
                        minPoint = intersectionPoints.get(i);
                    }
                    /**
                     * Find max point of intersection.
                     */
                    if (intersectionPoints.get(i).getY() > maxPoint.getY()) {
                        maxPoint = intersectionPoints.get(i);
                    }
                }
            }
            List<Point> pointList = new ArrayList<>();
            pointList.add(minPoint);
            pointList.add(maxPoint);
            return pointList;
        }
    }

    /**
     * Function to arrange the intercept point in one conventional way so as to make the final
     * list of way points have similar pattern.
     *
     * @param points List<Point>
     * @return List<Point>
     */
    public static List<Point> arrangeInterceptPoint(List<Point> points) {
        if (points != null && points.size() >= 2) {
            /**
             * Check if x coordinate is same or not.
             */
            if (points.get(0).getX() != points.get(1).getX()) {
                /**
                 * If x coordinate is not same then check which of the points have higher X value and
                 * save the one having lesser in first index and other in second index.
                 */
                if (points.get(0).getX() > points.get(1).getX()) {
                    //Swap the values
                    Point temp = points.get(0);
                    points.set(0, points.get(1));
                    points.set(1, temp);
                }
            }
            /**
             * Check if Y coordinate is same or not.
             */
            else if (points.get(0).getY() != points.get(1).getY()) {
                /**
                 * If Y coordinate is not same then check which of the points have higher Y value
                 * and save the one having lesser in first index and other in second index.
                 */
                if (points.get(0).getY() > points.get(1).getY()) {
                    //Swap the values
                    Point temp = points.get(0);
                    points.set(0, points.get(1));
                    points.set(1, temp);
                }
            }
        }
        return points;
    }

    /**
     * Function to arrange all the waypoints in a format so as drone could fly on it with
     * covering minimum distance
     *
     * @param points List<Point>
     * @return List<Point>
     */
    public static List<Point> arrangeWaypoints(List<Point> points) {
        if (points != null && points.size() > 3) {
            int i = 2;
            do {
                int firstPosition = i;
                int secondPosition = i + 1;
                Point temp = points.get(firstPosition);
                points.set(firstPosition, points.get(secondPosition));
                points.set(secondPosition, temp);
                i += 4;
            } while (i + 1 <= points.size() - 1);
        }
        return points;
    }
}
