package webonise.mapboxdemo.dronetransects;

import java.util.List;

public class EquationHandler {
    private static final String TAG = "EquationHandler";

    /**
     * Function Get equation of a line from given point.
     *
     * @param firstPoint  Point : First point on a plane
     * @param secondPoint Point : Second point on the same plane
     * @return LineEquation : Equation of a line between given points.
     */
    public LineEquation getLineEquation(Point firstPoint, Point secondPoint) throws Exception {
        LineEquation lineEquation = new LineEquation();
        double m;
        double c;
        double a = 1;
        /**
         * If x1 == x2 then slope of that line will always be -1 and c = X and a = 0 in ay = mx + c.
         */
        if (firstPoint.getX() == secondPoint.getX()) {
            m = -1;
            c = firstPoint.getX();
            a = 0;
        }
        /**
         * If y1 == y2 then slope of that line will always be 0 and c = Y in ay = mx + c.
         */
        else if (firstPoint.getY() == secondPoint.getY()) {
            m = 0;
            c = firstPoint.getY();
        } else {
            /**
             * m represents the slope of a line
             * From math formula : m = (y2-y1) / (x2-x1)
             */
            m = (secondPoint.getY() - firstPoint.getY())
                    / (secondPoint.getX() - firstPoint.getX());
            m = Double.isInfinite(m) ? 0 : m;
            /**
             * c represents 'y' intercept of a line.
             * From math formula : c = y - mx
             */
            c = firstPoint.getY() - (m * firstPoint.getX());
        }
        lineEquation.setM(m);
        lineEquation.setA(a);
        lineEquation.setC(c);
        return lineEquation;
    }

    /**
     * Function Get equation of a line from given point and angle.
     *
     * @param angle int : angle of the line
     * @param point Point : Second point on the same plane
     * @return LineEquation : Equation of a line between given points.
     */
    public LineEquation getLineEquation(int angle, Point point) throws Exception {
        LineEquation lineEquation = new LineEquation();
        double m;
        double a;
        double c;
        /**
         * If angle == 0 ie. line is parallel to x-axis then
         * slope of the line will be 0
         * y intercept will be 'y' from given point
         */
        if (angle % 180 == 0) {
            m = 0;
            a = 1;
            c = point.getY();
        }
        /**
         * If angle == 90 ie. line is parallel to y-axis then
         * slope of the line will be -1
         * y intercept will be 'x' from given point
         */
        else if (angle % 180 == 90) {
            m = -1;
            a = 0;
            c = point.getX();
        } else {
            /**
             * Using formula m = tan(angle)
             */
            m = Math.tan(Math.toRadians(angle));
            a = 1;
            /**
             * Step 2: Get 'y' intercept using given crossing point in formula y = mx + c.
             * so c = y - mx
             */
            c = point.getY() - (m * point.getX());
        }
        lineEquation.setC(c);
        lineEquation.setM(m);
        lineEquation.setA(a);
        return lineEquation;
    }

    /**
     * Function to get the equation of a line perpendicular to given line and passing though a
     * point.
     *
     * @param givenLineEquation LineEquation : Given line equation
     * @param crossingPoint     Point : Point through with the line should pass.
     * @return LineEquation : Line perpendicular to given line and passing through given point.
     */
    public LineEquation getPerpendicularLineEquation(LineEquation givenLineEquation,
                                                     Point crossingPoint) throws Exception {
        LineEquation lineEquation = new LineEquation();
        double m;
        double c;
        double a = 1;
        /**
         * If m == 0 ie. line is parallel to x-axis then
         * slope of the line will be -1
         * y intercept will be 'x' from given point
         */
        if (givenLineEquation.getM() == 0) {
            m = -1;
            a = 0;
            c = crossingPoint.getX();
        }
        /**
         * If a == 0 in line ay = mx + c that means that the line is parallel to y-axis then
         * slope of the line will be 0
         * y intercept will be 'y' from given point
         */
        else if (givenLineEquation.getA() == 0) {
            m = 0;
            c = crossingPoint.getY();
        } else {
            /**
             * Step 1: Get slope of the perpendicular line by formula m1.m2 = -1
             * given m1 is slop of given line and m2 will be slope of line perpendicular to it.
             */
            m = -1 / givenLineEquation.getM();
            m = Double.isInfinite(m) ? 0 : m;
            /**
             * Step 2: Get 'y' intercept using given crossing point in formula y = mx + c.
             * so c = y - mx
             */
            c = crossingPoint.getY() - (m * crossingPoint.getX());
        }
        lineEquation.setA(a);
        lineEquation.setC(c);
        lineEquation.setM(m);
        return lineEquation;
    }

    /**
     * Function to get the equation of a line parallel to given line and passing though a
     * point.
     *
     * @param givenLineEquation LineEquation : Given line equation
     * @param crossingPoint     Point : Point through with the line should pass.
     * @return LineEquation : Line parallel to given line and passing through given point.
     */
    public LineEquation getParallelLineEquation(LineEquation givenLineEquation,
                                                Point crossingPoint) throws Exception {
        LineEquation lineEquation = new LineEquation();
        double m;
        double c;
        double a = 1;
        /**
         * If m == 0 ie. line is parallel to x-axis then
         * slope of the line will be same
         * y intercept will be 'y' from given point
         */
        if (givenLineEquation.getM() == 0) {
            m = givenLineEquation.getM();
            a = 1;
            c = crossingPoint.getY();
        }
        /**
         * If a == 0 in line ay = mx + c that means that the line is parallel to y-axis then
         * slope of the line will be same
         * x intercept will be 'x' from given point
         */
        else if (givenLineEquation.getA() == 0) {
            m = givenLineEquation.getM();
            a = 0;
            c = crossingPoint.getX();
        } else {
            /**
             * Step 1: Get slope of the parallel line by formula m1 = m2
             * given m1 is slop of given line and m2 will be slope of line parallel to it.
             */
            m = givenLineEquation.getM();
            /**
             * Step 2: Get 'y' intercept using given crossing point in formula y = mx + c.
             * so c = y - mx
             */
            c = crossingPoint.getY() - (m * crossingPoint.getX());
        }
        lineEquation.setC(c);
        lineEquation.setA(a);
        lineEquation.setM(Double.isInfinite(m) ? 0 : m);
        return lineEquation;
    }

    /**
     * Function to get the intersection point of two given lines.
     *
     * @param firstLine  LineEquation : Equation of first line
     * @param secondLine LineEquation : Equation of second line
     * @return Point Intersection point of two lines.
     */
    public Point getIntersectionPoint(LineEquation firstLine, LineEquation secondLine) throws Exception {
        /**
         * Point of intersection will be infinite if slopes are same
         */
        if (firstLine.getM() == secondLine.getM())
            return new Point(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
        /**
         * There will be point of intersection only if there slope is different
         */
        /**
         * Check the value of "a" in ay = mx + c
         * As if a == 0  then given line is parallel to y-axis and
         */
        double x;
        /**
         * X will always be 'C' from y = mx + c
         */
        if (firstLine.getA() == 0) {
            x = firstLine.getC();
        } else if (secondLine.getA() == 0) {
            x = secondLine.getC();
        } else {
            /**
             * Given    first line :  y = m1x + c1
             *          Second line : y = m2x + c2
             * Find x : x = (c2 - c1) / (m1 - m2)
             */
            x = (secondLine.getC() - firstLine.getC()) / (firstLine.getM() - secondLine.getM());
        }
        double y;
        /**
         * Check for slope of a line to be 0.
         * If found then you can directly set the y-axis value as y-intercept.
         */
        if (secondLine.getM() == 0) {
            y = secondLine.getC();
        } else if (firstLine.getM() == 0) {
            y = firstLine.getC();
        } else {
            /**
             * Now put 'x' in any one line equation ie. y = mx + c.
             * Check if the line you choose should not be parallel to x-axis.
             */
            if (firstLine.getA() != 0) {
                y = (firstLine.getM() * x) + firstLine.getC();
            } else {
                y = (secondLine.getM() * x) + secondLine.getC();
            }
        }
        return new Point(x, y);
    }

    /**
     * Function to get point on a line at a given distance from given point
     *
     * @param lineEquation LineEquation : Line on which you have to find the point
     * @param pointOnLine  Point : Point from where you want a point at a given distance
     * @param distance     double : Distance at which you want the point to be.
     * @param polygon
     * @return Point Point at the given distance on the given line
     */
    public Point getPointOnLineAtDistance(LineEquation lineEquation, Point pointOnLine,
                                          double distance, Point[] polygon) throws Exception {
        Point[] distancePoint = getPointOnLineAtDistance(lineEquation, pointOnLine, distance);
        Point firstPoint = distancePoint[0];
        Point secondPoint = distancePoint[0];
        Polygon polygonPoints = buildPolygon(polygon);
        if (polygonPoints.contains(new Point(firstPoint.getX(), firstPoint.getY())))
            return secondPoint;
        else
            return firstPoint;
    }

    /**
     * Function to get point on a line at a given distance from given point
     *
     * @param lineEquation LineEquation : Line on which you have to find the point
     * @param pointOnLine  Point : Point from where you want a point at a given distance
     * @param distance     double : Distance at which you want the point to be.
     * @return Point[] Two Points at the given distance on the given line. These two points will be
     * on the either side of the lines.
     */
    public Point[] getPointOnLineAtDistance(LineEquation lineEquation, Point pointOnLine,
                                            double distance) {
        /**
         * Using line equation 'y=mx+c' and point (x,y) and distance 'd'
         * You will get two points on either side of the line.
         */
        Point firstPoint = new Point();
        Point secondPoint = new Point();
        double xFirstPoint;
        double xSecondPoint;
        double yFirstPoint;
        double ySecondPoint;
        /**
         * If line is parallel to x-axis ie. y = 4 then
         * point on the same with 'd' distance will be (x1 + d , y) and (x1 - d, y)
         */
        if (lineEquation.getM() == 0) {
            xFirstPoint = pointOnLine.getX() + distance;
            xSecondPoint = pointOnLine.getX() - distance;
            yFirstPoint = pointOnLine.getY();
            ySecondPoint = pointOnLine.getY();
        }
        /**
         * If line is parallel to y-axis ie. x = 4 then
         * point on the same with 'd' distance will be (x , y + d ) and (x , y - d)
         */
        else if (lineEquation.getA() == 0) {
            xFirstPoint = pointOnLine.getX();
            xSecondPoint = pointOnLine.getX();
            yFirstPoint = pointOnLine.getY() + distance;
            ySecondPoint = pointOnLine.getY() - distance;
        } else {
            /**
             * Step 1 : Get x point using formula x = x1 + (d / sqrt (1 + m * m))
             */
            //'x' for First point
            xFirstPoint = pointOnLine.getX() + (distance /
                    (Math.sqrt((1 + (Math.pow(lineEquation.getM(), 2))))));
            //'x' for Second point
            xSecondPoint = pointOnLine.getX() - (distance /
                    (Math.sqrt((1 + (Math.pow(lineEquation.getM(), 2))))));
            /**
             * Step 2 : Put 'x' in 'y = mx + c' to get 'y'
             */
            //'y' for first point
            yFirstPoint = (lineEquation.getM() * xFirstPoint) + lineEquation.getC();
            //'y' for second point
            ySecondPoint = (lineEquation.getM() * xSecondPoint) + lineEquation.getC();
        }
        firstPoint.setX(xFirstPoint);
        secondPoint.setX(xSecondPoint);
        secondPoint.setY(ySecondPoint);
        firstPoint.setY(yFirstPoint);
        Point[] points = new Point[2];
        points[0] = firstPoint;
        points[1] = secondPoint;
        return points;
    }

    private Polygon buildPolygon(Point[] polygon) {
        Polygon.Builder builder = Polygon.Builder();
        for (int i = 0; i < polygon.length; i++) {
            Point point = polygon[i];
            builder.addVertex(
                    new Point(point.getX(), point.getY()));
        }
        return builder.build();
    }


    /**
     * Function to check if the given point lies on the polygon or not.
     *
     * @param polygonPoints List<Point>
     * @param point         Point
     * @return boolean
     */
    public boolean isPointOnPolygon(List<Point> polygonPoints, Point point) throws Exception {
        for (int i = 0; i < polygonPoints.size(); i++) {
            /**
             * Get first and next point on the polygon.
             */
            int nextPointPosition = (i + 1) % polygonPoints.size();
            Point firstPoint = polygonPoints.get(i);
            Point nextPoint = polygonPoints.get(nextPointPosition);
            /**
             * Get the line equation formed by first and next point.
             */
            LineEquation lineEquation = getLineEquation(firstPoint, nextPoint);
            /**
             * Check if the point lies on the line or not.
             */
            if (isPointOnLine(lineEquation, point)) {
                /**
                 * Check if the point is in the range of first and last point or not.
                 * (firstPoint.x <= point.x <= nextPoint.x) or (nextPoint.x <= point.x <= firstPoint.x)
                 * and
                 * (firstPoint.y <= point.y <= nextPoint.y) or (nextPoint.y <= point.y <= firstPoint.y)
                 */
                if (((firstPoint.getX() <= point.getX() && point.getX() <= nextPoint.getX())
                        || (nextPoint.getX() <= point.getX() && point.getX() <= firstPoint.getX()))
                        && ((firstPoint.getY() <= point.getY() && point.getY() <= nextPoint.getY())
                        || (nextPoint.getY() <= point.getY() && point.getY() <= firstPoint.getY()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Function to check if the point lies on the line or not.
     * To check this you need to place value of X and Y in the line equation Y = mx + c.
     *
     * @param lineEquation LineEquation
     * @param point        Point
     * @return boolean
     */
    public boolean isPointOnLine(LineEquation lineEquation, Point point) {
        final double VERY_SMALL_DIFFERENCE = 0.000000001;
        /**
         * Check for the function to satisfy equation ay = mx + c
         */
        double y = lineEquation.getA() * point.getY();
        double mx_c = (lineEquation.getM() * point.getX()) + lineEquation.getC();
        double difference = Math.abs((y - mx_c));
        return difference == 0.0 || difference < VERY_SMALL_DIFFERENCE;
    }
}
