package webonise.mapboxdemo.dronetransects;

/**
 * Class represents equation of a line in the form of "y = mx + c"
 */
public class LineEquation {
    /**
     * "m" represents slope of a line
     * From math formula : m = (y2-y1) / (x2-x1)
     */
    private double m;
    /**
     * "c" represents 'y' intercept of a line
     */
    private double c;
    /**
     * "a" represents factor of y in line equation ay = mx + c
     * By default "a" will always be one.
     */
    private double a = 1;
    private String equation;

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
        setEquation();
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
        setEquation();
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public String getEquation() {
        return a + "y = " + m + "x + " + c;
    }

    private void setEquation() {
        this.equation = a + "y = " + m + "x + " + c;
    }
}
