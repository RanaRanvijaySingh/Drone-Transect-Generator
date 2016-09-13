package webonise.mapboxdemo.dronetransects;

public class Point {
    public double x;
    public double y;

    public Point() {

    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
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

    /**
     * Function to get the center point between two points
     *
     * @param startPoint Point:  Start point or first point
     * @param endPoint   Point : End point or second point
     * @return Point : Center point
     */
    public Point getCenterPoint(Point startPoint, Point endPoint) {
        Point centerPoint = new Point();
        centerPoint.setX((startPoint.getX() + endPoint.getX()) / 2);
        centerPoint.setY((startPoint.getY() + endPoint.getY()) / 2);
        return centerPoint;
    }
}
